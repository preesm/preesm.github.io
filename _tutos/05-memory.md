---
title: "Memory Footprint Reduction"
permalink: /tutos/memory/
toc: true
---

In this tutorial, you will learn how to optimise the memory use of your application. You will update the workflow of your Preesm project. Each time you run an updated Workflow, you can recompile the generated code and execute the application. The executables of your application will be optimized in terms of memory use.

The following topics are covered in this tutorial:

*   Overview of the memory allocation mechanism of Preesm
*   Selection of an allocation algorithm
*   Derive bounds for the memory allocation
*   Post-scheduling optimization of the memory allocation

Prerequisite: 
* [Tutorial Introduction](/tutos/intro)
* [Parallelize an Application on a Multicore CPU](/tutos/parasobel)

###### Tutorial created the 07.31.2013 by [K. Desnos](mailto:kdesnos@insa-rennes.fr)

## Project setup

In addition to the default requirements (see [Requirements for Running Tutorial Generated Code](/tutos/intro/#requirements-for-running-tutorial-generated-code)), download the following files:

*   Complete [Sobel Preesm Project](/assets/tutos/parasobel/tutorial1_result.zip)
*   [YUV Sequence (7zip)](/assets/downloads/akiyo_cif.7z) (9 MB)
*   [DejaVu TTF Font](/assets/downloads/DejaVuSans.ttf) (757KB)

## Memory Footprint Reduction

The memory allocation technique used in Preesm is based on a Memory Exclusion Graph (MEG). A MEG is a graph whose vertices model the memory objects that must be allocated in memory in order to run the generated code.  In the current version of Preesm, each of these memory objects corresponds either to an edge of the Directed Acyclic Graph (DAG) ("/Algo/generated/srdag/*.pi") or to a buffer corresponding to "delays" of the graph that store data between executions of a schedule (cf. [Software Pipelining tutorial](/tutos/softwarepipeline)). In the MEG, two memory objects are linked by an edge (called an exclusion) if they can not be allocated in overlapping memory spaces. More information on the MEG can be found in [\[1\]](#references).

### Basic Memory Allocation

Each time a Preesm workflow is run on a scenario, it prints log information that can be consulted in the "Console" view. Hereafter is an excerpt of the log generated when running the "/Workflows/Codegen.workflow" on the "/Scenarios/4core.scenario": 
```
13:37:00 Memory exclusion graph built with 21 vertices and density = 0.7523809523809524 
13:37:00 BasicAllocator allocates 665.5 kBytes in 0 ms. 
```
The three main informations contained in this excerpt are highlighted in blue.

*   **21 vertices**: This number corresponds to the number of memory objects that must be allocated in order to run the application.
*   **BasicAllocator**: Name of the memory allocator used in the workflow. We will see in the next section how to select a more efficient allocator.
*   **665.5 kBytes**: Number of bytes allocated in shared memory for the application. In the current version of Preesm, only shared memory allocation is possible.

The Basic allocation algorithm used in the Sobel project is a naive allocation algorithm that allocates each memory object in a dedicated memory space. The amount of memory allocated by this allocator is thus equal to the sum of the sizes of all allocated memory objects. The Basic allocation algorithm clearly is a suboptimal allocation algorithm as it does not reuse memory space to store several memory objects.

Reusing memory spaces to store several memory objects is an efficient method to reduce the amount of memory needed to run an application. For example, in the non-pipelined Sobel application, the buffer used to transmit data between actors Read_YUV and Split and the one used between Merge and display will never contain data simultaneously. It is thus possible to allocate these two buffers in overlapping adress ranges of the memory. More information on the modelisation of the memory allocation problem for Synchronous Dataflow (SDF) can be found in [\[1\]](#references).

###  Change The Memory Allocation Algorithm

Follow the following steps to change the allocation algorithm used in the workflow:

1.  Double-click on "/Workflows/Codegen.workflow" to open the workflow editor.
2.  In the workflow editor, select the "Memory Allocation" task.
3.  In the "Properties" view, open the "Task Variables" tab.
4.  Set the value of the "Allocator(s)" property to "FirstFit". Other possible allocators are "BestFit", "Basic" and "DeGreef". More information on these allocators can be found in [\[2\]](#references).
5.  Optionally, if you selected the BestFit or the FirstFit allocator, you can set the "Best/First Fit order" property using one of the following values:

| Value | Comment |
|-------|---------|
| ApproxStableSet | Vertices of the memory exclusion graph are sorted into disjoint stable sets [\[3\]](#references). Stable sets are formed one after the other, each with the largest (heuristic) number of vertex possible. Vertices are fed to the allocator set by set and in the largest first order within each stable set. |
| ExactStableSet | Similar to "ApproxStableSet". Stable set are formed using an exact algorithm instead of a heuristic. |
| LargestFirst | Memory objects are allocated in decreasing order of their size. |
| Shuffle | Memory objects are allocated in a random order. Using the "Nb of Shuffling Tested" property, it is possible to test several random orders and only keep the best memory allocation.|
| Scheduling | Memory objects are allocated in scheduling order of their "birth". The "birth" of a memory object is the instant when its memory would be allocated by a dynamic allocator. This option can be used to mimic the behavior of a dynamic allocator. (Only available for memory exclusion graphs updated with scheduling information). |

When using the "FirstFit" algorithm with the "LargestFirst" order, we get a memory allocation of 370.5625 kBytes, i.e. 44% less memory than the Basic allocator.

```
13:37:00 FirstFitAllocator(LARGEST_FIRST) allocates 370.5625 kBytes in 1 ms.
```

### Allocator performance evaluation

The analysis technique presented in [\[1\]](#references) can be used in Preesm to derive bounds for the amount of memory that can be allocated for an application. The upper bound corresponds to the worst memory allocation possible for an application. Its value corresponds to the memory allocated by the Basic allocator. The lower bound is a theoritical value that limits the minimim amount of memory that can be allocated. By definition, the lower bound is not always reachable, which means that it might be impossible to find an allocation with this optimal amount of memory.

![](/assets/tutos/memory/membounds.png)

To evaluate the quality of a memory allocation, a task can be added to the workflow to derive the memory bounds and print them in the "Console". Follow this procedure to add a "Mem Bounds" task to the workflow:

1.  Double-click on "/Workflows/Codegen.workflow" to open the workflow editor.
2.  Click on "Task" in the palette (on the right side of the workflow editor), then click in the workflow to add a new Task.
3.  In the "New Vertex" wizard, name the new task "Memory Bounds Estimator"
4.  Click on 'Data transfer" in the palette and click successively on the "MEG Builder" and the newly created task.
5.  Name all the new ports "MemEx".
6.  Select the new task in the editor and open the "Properties" view.
7.  In the "Basic" Tab, set the "plugin identifier" property to "org.ietr.preesm.memory.bounds.MemoryBoundsEstimator".
8.  Save the workflow before opening the "Task Variables" tab of the "Properties" of the new task.
9.  Set the "Solver" property to "Heuristic" and the "Verbose" property to "False".
10.  Save and run the workflow.

As a result of the workflow execution, the following line should appear in the "Console": 
```
13:37:00 Bound_Max = 681472 Bound_Min = 264704
```

As expected, the value of the upper bound for the Sobel application is 681 472 bytes. The value of the lower bound is 264 704 bytes. This lower bound can be reached only by using a FirstFit or a BestFit allocator fed in "Shuffle" order (with a large number of shuffling).

Using this analysis technique, it is thus possible to evaluate the quality of the memory allocation. As presented [\[2\]](#references), the FirstFit allocator allocates on average only 4% more memory than lower memory bound.

### Post-Scheduling Memory Allocation

As presented in [\[2\]](#references), the Memory Exclusion Graph (MEG) used in Preesm can be updated with scheduling information to remove exclusions between memory objects and make better allocations possible. To do so:

1.  Double-click on "/Workflows/Codegen.workflow" to open the workflow editor.
2.  Click on "Task" in the palette (on the right side of the workflow editor), then click in the workflow to add a new Task.
3.  In the "New Vertex" wizard, name the new task "MEG Updater"
4.  Select the new task in the editor and open the "Properties" view.
5.  In the "Basic" Tab, set the "plugin identifier" property to "org.ietr.preesm.memory.exclusiongraph.MemExUpdater".
6.  Save the workflow before opening the "Task Variables" tab of the "Properties" of the new task.
7.  Set the "Verbose" property to "True"
8.  Set the values of all other properties to "False" to update the MEG with precedence information from the scheduled DAG. The "Update with MemObject lifetime" property can be used to update the MEG with precedence and timing information from the schedule. This option will produce a valid allocation only if the runtime of the actors is constant and identical to the one used by the scheduler. Small variations of the actors runtime may corrupt the memory allocation (see [\[2\]](#references)).
9.  Copy the connection of the following figure in your workflow.
10.  Save and run the workflow on the 4core.scenario.

[![](/assets/tutos/memory/memupdateworkflow.png)](/assets/tutos/memory/memupdateworkflow.png)

As a result of the workflow execution, the following lines should appear in the "Console":
```
16:06:04 Memory exclusion graph built with 21 vertices and density = 0.7523809
16:06:04 Memory exclusion graph updated with 21 vertices and density = 0.7333333 
16:06:04 Exclusions removed: 4 (3%)
```

The first line gives the density of the MEG before being updated. The density of the MEG is the ratio of number of edges (or exclusions) present in the MEG to the maximum possible number of edges in the graph. The second line gives the density of the MEG after it was updated with scheduling information. The last line gives the number of exclusions that were removed by the "MemEx Updater" task. As we can see, the number of exclusions of the graph is decreased by the updating process.

In the Sobel application, using the "FirstFit" allocator in "LargestFirst" order after a MEG update results in the allocation of 318.3125 kBytes, or 14% less memory than before the update. Although it is not the case with the Sobel example, a MEG with a lower edge density often leads to a smaller lower bound, which, in turn, leads to more efficient memory allocations. On average, the amount of allocated memory is decreased by 32% when the MEG is updated with scheduling information [\[2\]](#references).

### Performance Impact

Reducing the amount of memory allocated for an application can have a strong impact on the performance of the application. For example, allocating the Sobel application with 1 pipeline stage (cf. [Software Pipelining tutorial](/tutos/softwarepipeline)) with the Basic allocator requires 985 600 bytes. The allocation of the same application after a MEG update with a FirstFit-LargestFirst allocator requires only 475 200 bytes. On an 8-cores Intel i7 CPU clocked at 2.40GHz, this memory footprint reduction leads to a speedup of 2% (from ~1077fps to ~1093fps) on a 352x288 YUV Sequence.

## References

**\[1\]** Desnos, Karol; Pelcat, Maxime; Nezan, Jean-François; Aridhi, Slaheddine (2012) "Memory Bounds for the Distributed Execution of a Hierarchical Synchronous Data-Flow Graph". _SAMOS XII_, Samos, Greece. [\[link\]](http://hal.inria.fr/hal-00721335)

**\[2\]** Desnos, Karol; Pelcat, Maxime; Nezan, Jean-François; Aridhi, Slaheddine (2013) "Pre- and Post-Scheduling Memory Allocation Strategies on MPSoCs". _ESLSyn13_, Austin TX, USA. [\[link\]](/assets/tutos/memory/desnos_pre_and_post.pdf)

**\[3\]** "Independent Set (graph Theory)". _Wikipedia_, July 25 2013. [\[link\]](http://en.wikipedia.org/w/index.php?title=Independent_set_(graph_theory)&oldid=562766476.)
