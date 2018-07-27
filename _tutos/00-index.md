---
title: "Tutorials"
permalink: /tutos/
toc: true
---

Migration of the tutorials to the PiSDF model
---------------------------------------------

We recently migrated all of our tutorials from the IBSDF (Interface-Based Synchronous Data-Flow) model to the PiSDF (Parameterized and interfaced Synchronous Data-Flow) model.

If ever you find an error, a broken link or any other problem with the tutorials, please do not hesitate to [contact us](mailto:cguy@insa-rennes.fr, kdesnos@insa-rennes.fr, mpelcat@insa-rennes.fr) about it.

1. [Tutorial Introduction](http://preesm.insa-rennes.fr/website/index.php?id=tutorial-introduction)
---------------------------------------------------------------------------------------------------

The following topics are covered in this tutorial:

*   Installation of PREESM, Graphiti graph editor and DFTools plugins for Eclipse
*   Presentation of PREESM layout (Workflow, Scenario, Architecture graph, Algorithm graph...)
*   Simulation and scheduling of a multicore code
*   Generation of a multicore code

Prerequisites: None

2. [Parallelize an Application on a Multicore CPU](http://preesm.insa-rennes.fr/website/index.php?id=parallelize-an-application-on-a-multicore-cpu)
---------------------------------------------------------------------------------------------------------------------------------------------------

The following topics are covered in this tutorial:

*   Implementation of a [Sobel filter](http://en.wikipedia.org/wiki/Sobel_operator) with Preesm
*   C Code generation
*   Parallelization for multi-threaded environment

Prerequisites: [Tutorial Introduction](http://preesm.insa-rennes.fr/website/index.php?id=tutorial-introduction)

3. [Program the 8 cores of your Texas Instruments C6678 EVM](http://preesm.insa-rennes.fr/website/index.php?id=code-generation-for-multicore-dsp)
-------------------------------------------------------------------------------------------------------------------------------------------------

The following topics are covered in this tutorial:

*   C Code and Instrumented C code generation
*   Generation of cache coherent code
*   Execution on a Multicore C6x DSP (EVM6678)
*   Performance optimization of the application

Prerequisite: [Tutorial Introduction](http://preesm.insa-rennes.fr/website/index.php?id=tutorial-introduction) [Parallelize an Application on a Multicore CPU](http://preesm.insa-rennes.fr/website/index.php?id=parallelize-an-application-on-a-multicore-cpu)

4. [Software Pipelining for Throughput Optimization](http://preesm.insa-rennes.fr/website/index.php?id=software-pipelining-for-throughput-optimization)
-------------------------------------------------------------------------------------------------------------------------------------------------------

The following topics are covered in this tutorial:

*   Software Pipelining of an Application for Throughput Optimization

Prerequisite: [Tutorial Introduction](http://preesm.insa-rennes.fr/website/index.php?id=tutorial-introduction) [Parallelize an Application on a Multicore CPU](http://preesm.insa-rennes.fr/website/index.php?id=parallelize-an-application-on-a-multicore-cpu)

5. [Memory Footprint Reduction](http://preesm.insa-rennes.fr/website/index.php?id=memory-footprint-reduction)
-------------------------------------------------------------------------------------------------------------

The following topics are covered in this tutorial:

*   Overview of the memory allocation mechanism of Preesm
*   Selection of an allocation algorithm
*   Derive bounds for the memory allocation
*   Post-scheduling optimization of the memory allocation

Prerequisite: [Tutorial Introduction](http://preesm.insa-rennes.fr/website/index.php?id=tutorial-introduction) [Parallelize an Application on a Multicore CPU](http://preesm.insa-rennes.fr/website/index.php?id=parallelize-an-application-on-a-multicore-cpu)

6\. [Advanced Memory Footprint Reduction](http://preesm.insa-rennes.fr/website/index.php?id=advanced-memory-footprint-reduction)
--------------------------------------------------------------------------------------------------------------------------------

The following topics are covered in this tutorial:

*   Memory scripts to customize the memory allocation of actor ports
*   Dataflow ports annotation
*   Performance optimization by removing useless memory operations

Prerequisite: [Memory Footprint Reduction](http://preesm.insa-rennes.fr/website/index.php?id=memory-footprint-reduction)

7. [Automated Measurement of Actor Execution Time](http://preesm.insa-rennes.fr/website/index.php?id=automated-actor-execution-time-measurement)
------------------------------------------------------------------------------------------------------------------------------------------------

The following topics are covered in this tutorial:

*   Instrumented C code generation
*   Analysis of measured execution time
*   Scenario timings update for increased performance

Prerequisite: [Tutorial Introduction](http://preesm.insa-rennes.fr/website/index.php?id=tutorial-introduction) [Parallelize an Application on a Multicore CPU](http://preesm.insa-rennes.fr/website/index.php?id=parallelize-an-application-on-a-multicore-cpu)

8. [Import or Export a SDF3 Graph](http://preesm.insa-rennes.fr/website/index.php?id=importexport-an-sdf3-graph)
----------------------------------------------------------------------------------------------------------------

The following topics are covered in this tutorial:

*   Creation of a new Workflow
*   Importation of [SDF3](http://www.es.ele.tue.nl/sdf3/) SDF Graphs

Prerequisites: [Tutorial Introduction](http://preesm.insa-rennes.fr/website/index.php?id=tutorial-introduction)

