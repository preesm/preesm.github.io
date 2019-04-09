---
title: "Workflow Tasks Reference"
permalink: /docs/workflowtasksref/
toc: true
---

_Last update : 2019.09.04; for version 3.6.0_





## Memory Optimization

### Memory Allocation

  * **Identifier**: `org.ietr.preesm.memory.allocation.MemoryAllocatorTask`
  * **Short description**: Perform the memory allocation for the given MEG.

#### Inputs
  * **MemEx**:MemoryExclusionGraph : _Input Memory Exclusion Graph_

#### Outputs
  * **MEGs**:Map : _??????????? karol ?_

#### Description
Workflow task responsible for allocating the memory objects of the given MEG.

#### Parameters

##### Verbose
Verbosity of the task.

| Value | Effect |
| --- | --- |
| _True_ | Detailed statistics of the allocation process are logged |
| _False_ | Logged information is kept to a minimum |

##### Allocator(s)
Specify which memory allocation algorithm(s) should be used. If the string value of the parameters contains several algorithm names, all will be executed one by one.

| Value | Effect |
| --- | --- |
| _Basic_ | Each memory object is allocated in a dedicated memory space. Memory allocated for a given object is not reused for other. |
| _BestFit_ | Memory objects are allocated one by one; allocating each object to the available space in memory whose size is the closest to the size of the allocated object. If MEG exclusions permit it, memory allocated for a memory object may be reused for others. |
| _FirstFit_ | Memory objects are allocated one by one; allocating each object to the first available space in memory whose size is the large enough to allocate the object. If MEG exclusions permit it, memory allocated for a memory object may be reused for others. |
| _DeGreef_ | Algorithm adapted from DeGreef (1997)}. If MEG exclusions permit it, memory allocated for a memory object may be reused for others. |

##### Distribution
Specify which memory architecture should be used to allocate the memory.

| Value | Effect |
| --- | --- |
| _SharedOnly_ | (Default) All memory objects are allocated in a single memory bank accessible to all PE. |
| _DistributedOnly_ | Each PE is associated to a private memory bank that no other PE can access. (Currently not supported by code generation.) |
| _Mixed_ | Both private memory banks and a shared memory can be used for allocating memory. |
| _MixedMerged_ | Same as mixed, but the memory allocation algorithm favors buffer merging over memory distribution. |

##### Best/First Fit order
When using FirstFit or BestFit memory allocators, this parameter specifies in which order the memory objects will be fed to the allocation algorithm. If the string value associated to the parameters contains several order names, all will be executed one by one.

| Value | Effect |
| --- | --- |
| _ApproxStableSet_ | Memory objects are sorted into disjoint stable sets. Stable sets are formed one after the other, each with the largest possible number of object. Memory objects are fed to the allocator set by set and in the largest first order within each stable set. |
| _ExactStableSet_ | Similar to 'ApproxStableSet'. Stable set are built using an exact algorithm instead of a heuristic. |
| _LargestFirst_ | Memory objects are allocated in decreasing order of their size. |
| _Shuffle_ | Memory objects are allocated in a random order. Using the 'Nb of Shuffling Tested' parameter, it is possible to test several random orders and only keep the best memory allocation. |
| _Scheduling_ | Memory objects are allocated in scheduling order of their 'birth'. The 'birth' of a memory object is the instant when its memory would be allocated by a dynamic allocator. This option can be used to mimic the behavior of a dynamic allocator. (Only available for MEG updated with scheduling information). |

##### Data alignment
Option used to force the allocation of buffers (i.e. Memory objects) with aligned addresses. The data alignment property should always have the same value as the one set in the properties of the Memory Scripts task.

| Value | Effect |
| --- | --- |
| _None_ | No special care is taken to align the buffers in memory. |
| _Data_ | All buffers are aligned on addresses that are multiples of their size. For example, a 4 bytes integer is aligned on 4 bytes address. |
| _Fixed:=n_ | Where $$n\in \mathbb{N}^*$$. This forces the allocation algorithm to align all buffers on addresses that are multiples of n bytes. |

##### Nb of Shuffling Tested
Number of random order tested when using the Shuffle value for the Best/First Fit order parameter.

| Value | Effect |
| --- | --- |
| _$$n\in \mathbb{N}^*$$_ | Number of random order. |

##### Merge broadcasts
(Deprecated) Merge memory objects corresponding to outputs of Broadcast actors. This feature was replaced by the more generic Memory Scripts.

#### Documented Errors

| Message | Explanation |
| --- | --- |
| **The obtained allocation was not valid because mutually exclusive memory objects have overlapping address ranges. The allocator is not working.** | When checking the result of a memory allocation, two memory objects linked with an exclusion in the MEG were allocated in overlapping memory spaces. The error is caused by an invalid memory allocation algorithm and should be corrected in the source code. |
| **The obtained allocation was not valid because there were unaligned memory objects. The allocator is not working.** | When checking the result of a memory allocation, some memory objects were found not to respect the Dala alignment parameter. The error is caused by an invalid memory allocation algorithm and should be corrected in the source code. |

#### See Also

  * Memory Allocation Algorithms : K. Desnos, M. Pelcat, J.-F. Nezan, and S. Aridhi. Pre-and post-scheduling memory allocation strategies on MPSoCs. In Electronic System Level Synthesis Conference (ESLsyn), 2013.
  * Distributed Memory Allocation : Karol Desnos, Maxime Pelcat, Jean-François Nezan, and Slaheddine Aridhi. Distributed memory allocation technique for synchronous dataflow graphs. In Signal Processing System (SiPS), Workshop on, pages 1–6. IEEE, 2016.
  * Broadcast Merging : K. Desnos, M. Pelcat, J.-F. Nezan, and S. Aridhi. Memory analysis and optimized allocation of dataflow applications on shared-memory MPSoCs. Journal of Signal Processing Systems, Springer, 2014.

