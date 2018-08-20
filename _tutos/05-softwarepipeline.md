---
title: "Software Pipelining for Throughput Optimization"
permalink: /tutos/softwarepipeline/
toc: true
---

In this tutorial, you will learn how to increase the throughput of an application using the Software pipelining. You will introduce some delays in the dataflow description of your application.

The following topics are covered in this tutorial:

*   Software Pipelining of an Application for Throughput Optimization

Prerequisite: [Tutorial Introduction](index.php?id=tutorial-introduction), [Parallelize an Application on a Multicore CPU](index.php?id=parallelize-an-application-on-a-multicore-cpu)


###### Tutorial created the 10.14.2013 by [K. Desnos](mailto:kdesnos@insa-rennes.fr)

1\. Project setup
-----------------

The starting point of this tutorial is the Preesm project obtained as a result of the [Parallelize an Application on a Multicore CPU](index.php?id=parallelize-an-application-on-a-multicore-cpu) tutorial. The project resulting from this tutorial is available [\[here\]](data/uploads/tutorial_zips/tutorial1_result.zip). The external libraries, the YUV sequence and the generated C code are not included in this archive. Explanation on how to setup these external elements, compile and run the project are available [\[here\]](index.php?id=parallelize-an-application-on-a-multicore-cpu).

2\. Software Pipelining of an Application
-----------------------------------------

#### 2.1. What is the software pipelining?

Software pipelining (also called blocking or block scheduling) is a popular technique used to increase the throughput of an application. The throughput of an application is usually limited by the latency of its critical path. In Synchronous Dataflow (SDF) graphs, the critical path is the chain of actors whose sum of actor execution times is the largest. In the Sobel application, the critical path is composed of the following actors: (read\_YUV)-(Split)-(Sobel\_x)-(Merge)-(display). The basic idea behind the software pipelining technique is to break the critical path into shortest parts by introducing "delays" in the path. The introduction of delays relaxes the dependency constraints between actors and makes possible the concurrent execution of the different stages of the pipelined critical path. More information on software pipelining can be found in [\[1\]](#ref).

[![](/assets/tutos/softwarepipeline/sobel_pipelining.png)](/assets/tutos/softwarepipeline/sobel_pipelining.png)

#### 2.2. Pipelining the Sobel application

To add a first pipeline stage to the Sobel application in Preesm, follow the following steps:

1.  In Preesm, double-click on "/top_display.diagram" to open the graph editor.
2.  Right-click on the edge between actors Merge and display > Add Delay.
3.  Add dependencies between parameter width and the delay and between parameter height and the delay.
4.  Click on the delay.
5.  In the "Properties" view, set the "Delay" expression to "height*width".
6.  Similarly, add a delay of "height/2*width/2" on the two edges linking the Read_YUV actor to the display actor.
7.  Save the modified graph.
8.  Run the "/Workflows/Codegen.workflow" on the "/Scenarios/4core.scenario".

In the displayed Gantt diagram, you can see that the display actor is now scheduled before the Merge actor. Hence, the frame displayed at the nth execution of the schedule is the frame that was processed and merged at the (n-1)th execution of the schedule.

![](/assets/tutos/softwarepipeline/4coregantt_1pipeline.png)

#### 2.3. Performance test and further optimization.

When running the generated code on a multicore architecture, you should experience a moderate speedup in terms of frames per second (fps). For example, on an 8-cores Intel i7 CPU clocked at 2.40GHz, we experienced a speedup of 11% (from ~970fps to ~1077fps) on a 352x288 YUV sequence.

Adding a second pipeline stage between the Read\_YUV and the Split actors can be done to further improve the performances of the application (do not forget to double the delays on the edges between Read\_YUV and display).

The downside of the pipelining technique is an increase of the memory that must be allocated to run the application. Indeed, the transmission of a processed frame from one execution of the schedule to the next requires a place in memory. For example, with a 352x288 image, adding a first pipeline stage requires the allocation of 304 128 bytes of memory in addition to the 681 472 allocated for the non-pipelined application. Adding a second pipeline stage requires an extra 253 440 bytes. Smart memory allocation techniques can be used to reduce the total amount of memory allocated for the application (cf. ["Memory Footprint Reduction" tutorial](index.php?id=memory-footprint-reduction)).

In the current version of the Sobel project, the effect of pipelining might not be positive because of the inaccurate execution time associated to the actors. Indeed, in the Sobel project, the scheduling and mapping algorithm considers that all actors of the graph have a default execution time of 100 time units. This leads to bad decisions from the scheduler which may in turn leads to suboptimal schedules. To correct the time of the actors, follow [the Automated Measurement of Actor Execution Time tutorial](index.php?id=automated-actor-execution-time-measurement).

References
----------

**\[1\]** "Software Pipelining". _Wikipedia_, may 3rd 2013. [\[link\]](http://en.wikipedia.org/w/index.php?title=Software_pipelining&oldid=551016521)
