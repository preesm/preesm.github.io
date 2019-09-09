---
title: "Workflow Tasks Reference"
permalink: /docs/workflowtasksref/
toc: true
---

_This page has been generated. Last update : 2019.09.09; for Preesm version 3.17.0_

This page references the available workflow tasks.





## Graph Transformation

### Data-parallel Transformation

  * **Identifier**: `fi.abo.preesm.dataparallel.DataParallel`
  * **Implementing Class**: `fi.abo.preesm.dataparallel.DataParallel`
  * **Short description**: Detect whether an SDF graph is data-parallel and provide its data-parallel equivalent Single-Rate SDF and its re-timing information.

#### Inputs
  * **SDF** (of _SDFGraph_)

#### Outputs
  * **CySDF** (of _SDFGraph_)
  * **Info** (of _RetimingInfo_)

#### Description
An SDF graph is data-parallel when for each actor of the SDF graph, all of its instances can be executed at the same time. For instance, all strictly acyclic SDF graphs are data-parallel. This task increases the scope of this analysis to generic SDF graphs.

The task analyses an input SDF graph and reports if it is data-parallel by analysing each strongly connected component of the graph. If a strongly connected component requires a re-timing transformation for it to be data-parallel, then the transformation is carried out such that the corresponding strongly connected component in the output single-rate SDF is data-parallel. The re-timing transformation modifies the delays in the original SDF graph such that the final single-rate SDF output is data-parallel.

However, if a strongly connected component of the SDF is not data-parallel, then the plugin reports the actors of this strongly connected component. In this case, the strongly connected component at the output single-rate SDF graph is same as that of the single-rate transformation on the original SDF.

The data-structure INFO describes mapping of delays in original FIFO to delays in the transformed SDF. This non-trivial initialization of delays in FIFOs is represented using SDF-like graphs where FIFO initialization function is represented as an actor. The data-structure INFO is provided for sake of completion and is not being used by other plugins. The data-structure INFO can change in future based on the design of the initialization of the FIFOs.

#### Parameters
None.

#### Documented Errors

| Message | Explanation |
| --- | --- |
| **DAGComputationBug** | There is a bug in implementation due to incorrect assumption. Report the bug by opening an issue and attaching the graph that caused it. |

#### See Also

  * **Implementation details**: Sudeep Kanur, Johan Lilius, and Johan Ersfolk. Detecting data-parallel synchronous dataflow graphs. Technical Report 1184, 2017.


### Static PiMM to IBSDF - _Deprecated_

  * **Identifier**: `org.ietr.preesm.experiment.pimm2sdf.StaticPiMM2SDFTask`
  * **Implementing Class**: `org.preesm.algorithm.pisdf.pimm2sdf.StaticPiMM2SDFTask`
  * **Short description**: Transforms a static PiSDF Graph into an equivalent IBSDF graph.

#### Inputs
  * **PiMM** (of _PiGraph_)
  * **scenario** (of _Scenario_)

#### Outputs
  * **SDF** (of _SDFGraph_)

#### Description
In Preesm, since version 2.0.0, the Parameterized and Interfaced SDF (PiSDF) model of computa tion is used as the frontend model in the graphical editor of dataflow graphs. This model makes it possible to design dynamically reconfigurable dataflow graphs where the value of parameters, and production/consumption rates depending on them, might change during the execution of the application. In former versions, the Interface Based SDF (IBSDF) model of computation was used as the front end model for application design. Contrary to the PiSDF, the IBSDF is a static model of computation where production and consumption rates of actors is fixed at compile-time.

The purpose of this workflow task is to transform a static PiSDF graph into an equivalent IBSDF graph. A static PiSDF graph is a PiSDF graph where dynamic reconfiguration features of the PiSDF model of computation are not used.

#### Parameters
None.

#### See Also

  * **IBSDF**: J. Piat, S.S. Bhattacharyya, and M. Raulet. Interface-based hierarchy for synchronous data-flow graphs. In SiPS Proceedings, 2009.
  * **PiSDF**: K. Desnos, M. Pelcat, J.-F. Nezan, S.S. Bhattacharyya, and S. Aridhi. PiMM: Parameterized and interfaced dataflow meta-model for MPSoCs runtime reconfiguration. In Embedded Computer Systems: Architectures, Modeling, and Simulation (SAMOS XIII), 2013 International Conference on, pages 41–48. IEEE, 2013.


### Single-Rate Transformation - _Deprecated_

  * **Identifier**: `org.ietr.preesm.experiment.pimm2srdag.StaticPiMM2SrDAGTask`
  * **Implementing Class**: `org.preesm.algorithm.pisdf.pimm2srdag.StaticPiMM2SrDAGTask`
  * **Short description**: Transforms an SDF graph into an equivalent single-rate SDF graph.

#### Inputs
  * **PiMM** (of _PiGraph_)
  * **scenario** (of _Scenario_)
  * **architecture** (of _Design_)

#### Outputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **PiMM** (of _PiGraph_)

#### Description
In Preesm, since version 2.0.0, the Parameterized and Interfaced SDF (PiSDF) model of computa tion is used as the frontend model in the graphical editor of dataflow graphs. This model makes it possible to design dynamically reconfigurable dataflow graphs where the value of parameters, and production/consumption rates depending on them, might change during the execution of the application. In former versions, the Interface Based SDF (IBSDF) model of computation was used as the front end model for application design. Contrary to the PiSDF, the IBSDF is a static model of computation where production and consumption rates of actors is fixed at compile-time.

The purpose of this workflow task is to transform a static PiSDF graph into an equivalent IBSDF graph. A static PiSDF graph is a PiSDF graph where dynamic reconfiguration features of the PiSDF model of computation are not used.

#### Parameters

##### ExplodeImploreSuppr
_(Deprecated: use at your own risks)_

This parameter makes it possible to remove most of the explode and implode actors that are inserted in the graph during the single-rate transformation. The resulting SDF graph is an ill-constructed graph where a single data input/output port of an actor may be connected to several First-In, First-Out queues (Fifos).

| Value | Effect |
| --- | --- |
| _false_ | (default) The suppression of explode/implode special actors is not activated. |
| _true_ | The suppression of explode/implode special actors is activated. |

#### Documented Errors

| Message | Explanation |
| --- | --- |
| **Graph not valid, not schedulable** | Single-rate transformation of the SDF graph was aborted because the top level was not consistent, or it was consistent but did not contained enough delays — i.e. initial data tokens — to make it schedulable. |

#### See Also

  * **Single-rate transformation**: J.L. Pino, S.S. Bhattacharyya, and E.A. Lee. A hierarchical multiprocessor scheduling framework for synchronous dataflow graphs. Electronics Research Laboratory, College of Engineering, University of California, 1995.
  * **Special actors**: Karol Desnos, Maxime Pelcat, Jean-François Nezan, and Slaheddine Aridhi. On memory reuse between inputs and outputs of dataflow actors. ACM Transactions on Embedded Computing Systems, 15(30):25, January 2016.
  * **Graph consistency**: E.A. Lee and D.G. Messerschmitt. Synchronous data flow. Proceedings of the IEEE, 75(9):1235 – 1245, sept. 1987.


### Hierarchy Flattening - _Deprecated_

  * **Identifier**: `org.ietr.preesm.plugin.transforms.flathierarchy`
  * **Implementing Class**: `org.preesm.algorithm.transforms.HierarchyFlattening`
  * **Short description**: Transforms a hierarchical IBSDF graph into an equivalent SDF graph.

#### Inputs
  * **SDF** (of _SDFGraph_)

#### Outputs
  * **SDF** (of _SDFGraph_)

#### Description
The purpose of this workflow task is to flatten several levels of the hierarchy of an IBSDF graph and produce an equivalent SDF graph. A hierarchical IBSDF graph is a graph where the internal behavior of some actors is described using another IBSDF subgraph instead of a C header file. When applying this transformation, hierarchical IBSDF actors of the first n levels of hierarchy are replaced with the actors of the IBSDF subgraph with which these hierarchical actors are associated.

#### Parameters
None.

#### Documented Errors

| Message | Explanation |
| --- | --- |
| **Inconsistent Hierarchy, graph can’t be flattened** | Flattening of the IBSDF graph was aborted because one of the graph composing the application, at the top level or deeper in the hierarchy, was not consistent. |

#### See Also

  * **IBSDF**: J. Piat, S.S. Bhattacharyya, and M. Raulet. Interface-based hierarchy for synchronous data-flow graphs. In SiPS Proceedings, 2009.
  * **Graph consistency**: E.A. Lee and D.G. Messerschmitt. Synchronous data flow. Proceedings of the IEEE, 75(9):1235 – 1245, sept. 1987.


### PiSDF Flattener

  * **Identifier**: `org.ietr.preesm.pimm.algorithm.pimm2flat.StaticPiMM2FlatPiMMTask`
  * **Implementing Class**: `org.preesm.model.pisdf.statictools.PiSDFFlattenerTask`
  * **Short description**: Undocumented

#### Inputs
  * **PiMM** (of _PiGraph_)

#### Outputs
  * **PiMM** (of _PiGraph_)

#### Description
Undocumented

#### Parameters

##### Perform optimizations
Undocumented

| Value | Effect |
| --- | --- |
| _true / false_ | If true, tries to remove redundant special actors and self loops on delays. |


### PiSDF Single-Rate Transformation

  * **Identifier**: `pisdf-srdag`
  * **Implementing Class**: `org.preesm.model.pisdf.statictools.PiSDFToSingleRateTask`
  * **Short description**: Undocumented

#### Inputs
  * **PiMM** (of _PiGraph_)

#### Outputs
  * **PiMM** (of _PiGraph_)

#### Description
Undocumented

#### Parameters

##### Consistency_Method
Undocumented

| Value | Effect |
| --- | --- |
| _LCM_ |  |
| _Topology_ |  |


### PiSDF Transform Perfect Fit Delay To End Init

  * **Identifier**: `org.ietr.preesm.pisdf.transformperfectfitdelay`
  * **Implementing Class**: `org.preesm.model.pisdf.statictools.PiSDFTransformPerfectFitDelayToEndInitTask`
  * **Short description**: Undocumented

#### Inputs
  * **PiMM** (of _PiGraph_)

#### Outputs
  * **PiMM** (of _PiGraph_)

#### Description
Undocumented

#### Parameters
None.

## Graph Exporters

### SDF Exporter - _Deprecated_

  * **Identifier**: `org.ietr.preesm.plugin.exportXml.sdf4jgml`
  * **Implementing Class**: `org.preesm.algorithm.io.xml.SDFExporter`
  * **Short description**: Create a new __*.graphml__ file containing the exported SDF graph.

#### Inputs
  * **SDF** (of _SDFGraph_)

#### Outputs
None.

#### Description
The purpose of this task is to create a new __*.graphml__ file containing where the exported IBSDF graph will be written. The exported graph can then be visualized and exported using the former Preesm graph editor for IBSDF graph, which was replaced with the PiSDF graph editor since version 2.0.0. This task is generally used to export intermediary graphs generated at different step of a workflow execution. For example, to visualize the SDF graph resulting from the flattening of an IBSDF graph, or to understand the parallelism that was exposed by the single-rate transformation.

#### Parameters

##### path
Path of the directory within which the exported *.graphml file will be created. If the specified directory does not exist, it will be created.

| Value | Effect |
| --- | --- |
| _path/in/proj_ | Path within the Preesm project containing the workflow where the ”SDF Exporter” task is instantiated. Even if the workflow of a Preesm project A is executed with a scenario from a different project B, the __*.graphml__ file will be generated within the specified directory of project A.

Exported SDF graphs will be named automatically, usually using the same name as the original SDF graph processed by the workflow. If a graph with this name already exists in the given path, it will be overwritten.

Example: **Algo/generated/singlerate** |
| _path/in/proj/name.graphml_ | Path within the Preesm project containing the workflow where the ”SDF Exporter” task is instantiated. Even if the workflow of a Preesm project A is executed with a scenario from a different project B, the __*.graphml__ file will be generated within the specified directory of project A.

Exported SDF graph will be named using the string with the graphml extension at the end of the given path. If a graph with this name already exists in the given path, it will be overwritten.

Example: **Algo/generated/singlerate/myexport.graphml** |

#### Documented Errors

| Message | Explanation |
| --- | --- |
| **Path <given path> is not a valid path for export. <reason>** | The value set for parameter path is not a valid path in the project. |


### DAG Exporter - _Deprecated_

  * **Identifier**: `org.ietr.preesm.mapper.exporter.DAGExportTransform`
  * **Implementing Class**: `org.preesm.algorithm.mapper.exporter.DAGExportTransform`
  * **Short description**: Create a new __*.graphml__ file containing the exported Directed Acyclic Graph (DAG).

#### Inputs
  * **DAG** (of _DirectedAcyclicGraph_)

#### Outputs
None.

#### Description
The purpose of this task is to create a new __*.graphml__ file containing where the exported DAG will be written. The exported graph can then be visualized and exported using the former Preesm graph editor for IBSDF graph, which was replaced with the PiSDF graph editor since version 2.0.0. This task is generally used to export intermediary graphs generated by the mapping and scheduling tasks of the workflow.

#### Parameters

##### path
Path of the directory within which the exported *.graphml file will be created. If the specified directory does not exist, it will be created.

| Value | Effect |
| --- | --- |
| _path/in/proj_ | Path within the Preesm project containing the workflow where the ”SDF Exporter” task is instantiated. Even if the workflow of a Preesm project A is executed with a scenario from a different project B, the __*.graphml__ file will be generated within the specified directory of project A. Exported SDF graphs will be named automatically, usually using the same name as the original SDF graph processed by the workflow. If a graph with this name already exists in the given path, it will be overwritten. Example: **Algo/generated/singlerate** |
| _path/in/proj/name.graphml_ | Path within the Preesm project containing the workflow where the ”SDF Exporter” task is instantiated. Even if the workflow of a Preesm project A is executed with a scenario from a different project B, the __*.graphml__ file will be generated within the specified directory of project A. Exported SDF graph will be named using the string with the graphml extension at the end of the given path. If a graph with this name already exists in the given path, it will be overwritten. Example: **Algo/generated/singlerate/myexport.graphml** |

#### Documented Errors

| Message | Explanation |
| --- | --- |
| **Path \<given path\> is not a valid path for export. \<reason\>** | The value set for parameter path is not a valid path in the project. |


### PiSDF Exporter

  * **Identifier**: `pisdf-export`
  * **Implementing Class**: `org.preesm.model.pisdf.serialize.PiSDFExporterTask`
  * **Short description**: Undocumented

#### Inputs
  * **PiMM** (of _PiGraph_)

#### Outputs
None.

#### Description
Undocumented

#### Parameters

##### path
Undocumented

| Value | Effect |
| --- | --- |
| _/Algo/generated/pisdf/_ | default path |

##### hierarchical
Undocumented

| Value | Effect |
| --- | --- |
| _true/false_ | Export the whole hierarchy (default: true). When set to true, will export all the hierarchy in the folder given by 'path', replacing refinement paths. Note: exporting hierarchical graph with this option set to false can cause the  the consistency check fail if the children graphs do not exist. |

## Schedulers

### External Scheduling from DAG - _Deprecated_

  * **Identifier**: `org.ietr.preesm.plugin.mapper.external`
  * **Implementing Class**: `org.preesm.algorithm.mapper.ExternalMappingFromDAG`
  * **Short description**: Undocumented

#### Inputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **architecture** (of _Design_)
  * **scenario** (of _Scenario_)

#### Outputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **ABC** (of _LatencyAbc_)

#### Description
Undocumented

#### Parameters

##### SCHEDULE_FILE
Undocumented

| Value | Effect |
| --- | --- |
| _/schedule.json_ | default value |


### External Scheduling from PiSDF

  * **Identifier**: `pisdf-mapper.external`
  * **Implementing Class**: `org.preesm.algorithm.mapper.ExternalMappingFromPiMM`
  * **Short description**: Undocumented

#### Inputs
  * **PiMM** (of _PiGraph_)
  * **architecture** (of _Design_)
  * **scenario** (of _Scenario_)

#### Outputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **ABC** (of _LatencyAbc_)

#### Description
Undocumented

#### Parameters

##### SCHEDULE_FILE
Undocumented

| Value | Effect |
| --- | --- |
| _/schedule.json_ | default value |


### List Scheduling from SDF - _Deprecated_

  * **Identifier**: `org.ietr.preesm.plugin.mapper.fast`
  * **Implementing Class**: `org.preesm.algorithm.mapper.FASTMapping`
  * **Short description**: Undocumented

#### Inputs
  * **SDF** (of _SDFGraph_)
  * **architecture** (of _Design_)
  * **scenario** (of _Scenario_)

#### Outputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **ABC** (of _LatencyAbc_)

#### Description
Undocumented

#### Parameters

##### edgeSchedType
Undocumented

| Value | Effect |
| --- | --- |
| _Simple_ | Undocumented |

##### simulatorType
Undocumented

| Value | Effect |
| --- | --- |
| _LooselyTimed_ | Undocumented |

##### Check
Undocumented

| Value | Effect |
| --- | --- |
| _True_ | Undocumented |

##### Optimize synchronization
Undocumented

| Value | Effect |
| --- | --- |
| _False_ | Undocumented |

##### balanceLoads
Undocumented

| Value | Effect |
| --- | --- |
| _false_ | Undocumented |

##### displaySolutions
Undocumented

| Value | Effect |
| --- | --- |
| _false_ | Undocumented |

##### fastTime
Undocumented

| Value | Effect |
| --- | --- |
| _100_ | Undocumented |

##### fastLocalSearchTime
Undocumented

| Value | Effect |
| --- | --- |
| _10_ | Undocumented |


### Fast Scheduling from DAG - _Deprecated_

  * **Identifier**: `org.ietr.preesm.plugin.mapper.fastdag`
  * **Implementing Class**: `org.preesm.algorithm.mapper.FASTMappingFromDAG`
  * **Short description**: Undocumented

#### Inputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **architecture** (of _Design_)
  * **scenario** (of _Scenario_)

#### Outputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **ABC** (of _LatencyAbc_)

#### Description
Undocumented

#### Parameters

##### edgeSchedType
Undocumented

| Value | Effect |
| --- | --- |
| _Simple_ | Undocumented |

##### simulatorType
Undocumented

| Value | Effect |
| --- | --- |
| _LooselyTimed_ | Undocumented |

##### Check
Undocumented

| Value | Effect |
| --- | --- |
| _True_ | Undocumented |

##### Optimize synchronization
Undocumented

| Value | Effect |
| --- | --- |
| _False_ | Undocumented |

##### balanceLoads
Undocumented

| Value | Effect |
| --- | --- |
| _false_ | Undocumented |

##### displaySolutions
Undocumented

| Value | Effect |
| --- | --- |
| _false_ | Undocumented |

##### fastTime
Undocumented

| Value | Effect |
| --- | --- |
| _100_ | Undocumented |

##### fastLocalSearchTime
Undocumented

| Value | Effect |
| --- | --- |
| _10_ | Undocumented |


### Fast Scheduling from PiSDF

  * **Identifier**: `pisdf-mapper.fast`
  * **Implementing Class**: `org.preesm.algorithm.mapper.FASTMappingFromPiMM`
  * **Short description**: Undocumented

#### Inputs
  * **PiMM** (of _PiGraph_)
  * **architecture** (of _Design_)
  * **scenario** (of _Scenario_)

#### Outputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **ABC** (of _LatencyAbc_)

#### Description
Undocumented

#### Parameters

##### edgeSchedType
Undocumented

| Value | Effect |
| --- | --- |
| _Simple_ | Undocumented |

##### simulatorType
Undocumented

| Value | Effect |
| --- | --- |
| _LooselyTimed_ | Undocumented |

##### Check
Undocumented

| Value | Effect |
| --- | --- |
| _True_ | Undocumented |

##### Optimize synchronization
Undocumented

| Value | Effect |
| --- | --- |
| _False_ | Undocumented |

##### balanceLoads
Undocumented

| Value | Effect |
| --- | --- |
| _false_ | Undocumented |

##### displaySolutions
Undocumented

| Value | Effect |
| --- | --- |
| _false_ | Undocumented |

##### fastTime
Undocumented

| Value | Effect |
| --- | --- |
| _100_ | Undocumented |

##### fastLocalSearchTime
Undocumented

| Value | Effect |
| --- | --- |
| _10_ | Undocumented |


### List Scheduling from SDF - _Deprecated_

  * **Identifier**: `org.ietr.preesm.plugin.mapper.listscheduling`
  * **Implementing Class**: `org.preesm.algorithm.mapper.ListSchedulingMapping`
  * **Short description**: Undocumented

#### Inputs
  * **SDF** (of _SDFGraph_)
  * **architecture** (of _Design_)
  * **scenario** (of _Scenario_)

#### Outputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **ABC** (of _LatencyAbc_)

#### Description
Undocumented

#### Parameters

##### edgeSchedType
Undocumented

| Value | Effect |
| --- | --- |
| _Simple_ | Undocumented |

##### simulatorType
Undocumented

| Value | Effect |
| --- | --- |
| _LooselyTimed_ | Undocumented |

##### Check
Undocumented

| Value | Effect |
| --- | --- |
| _True_ | Undocumented |

##### Optimize synchronization
Undocumented

| Value | Effect |
| --- | --- |
| _False_ | Undocumented |

##### balanceLoads
Undocumented

| Value | Effect |
| --- | --- |
| _false_ | Undocumented |


### List Scheduling from DAG - _Deprecated_

  * **Identifier**: `org.ietr.preesm.plugin.mapper.listschedulingfromdag`
  * **Implementing Class**: `org.preesm.algorithm.mapper.ListSchedulingMappingFromDAG`
  * **Short description**: Undocumented

#### Inputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **architecture** (of _Design_)
  * **scenario** (of _Scenario_)

#### Outputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **ABC** (of _LatencyAbc_)

#### Description
Undocumented

#### Parameters

##### edgeSchedType
Undocumented

| Value | Effect |
| --- | --- |
| _Simple / Switcher_ | Undocumented |

##### simulatorType
Undocumented

| Value | Effect |
| --- | --- |
| _LooselyTimed_ | Undocumented |

##### Check
Undocumented

| Value | Effect |
| --- | --- |
| _True_ | Undocumented |

##### Optimize synchronization
Undocumented

| Value | Effect |
| --- | --- |
| _False_ | Undocumented |

##### EnergyAwareness
Undocumented

| Value | Effect |
| --- | --- |
| _False_ | Undocumented |

##### balanceLoads
Undocumented

| Value | Effect |
| --- | --- |
| _false_ | Undocumented |


### List Scheduling from PiSDF

  * **Identifier**: `pisdf-mapper.list`
  * **Implementing Class**: `org.preesm.algorithm.mapper.ListSchedulingMappingFromPiMM`
  * **Short description**: Undocumented

#### Inputs
  * **PiMM** (of _PiGraph_)
  * **architecture** (of _Design_)
  * **scenario** (of _Scenario_)

#### Outputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **ABC** (of _LatencyAbc_)

#### Description
Undocumented

#### Parameters

##### edgeSchedType
Undocumented

| Value | Effect |
| --- | --- |
| _Simple_ | Undocumented |

##### simulatorType
Undocumented

| Value | Effect |
| --- | --- |
| _LooselyTimed_ | Undocumented |

##### Check
Undocumented

| Value | Effect |
| --- | --- |
| _True_ | Undocumented |

##### Optimize synchronization
Undocumented

| Value | Effect |
| --- | --- |
| _False_ | Undocumented |

##### balanceLoads
Undocumented

| Value | Effect |
| --- | --- |
| _false_ | Undocumented |

##### EnergyAwareness
Undocumented

| Value | Effect |
| --- | --- |
| _True_ | Turns on energy aware mapping/scheduling |

##### EnergyAwarenessFirstConfig
Undocumented

| Value | Effect |
| --- | --- |
| _First_ | Takes as starting point the first valid combination of PEs |
| _Middle_ | Takes as starting point half of the available PEs |
| _Max_ | Takes as starting point all the available PEs |
| _Random_ | Takes as starting point a random number of PEs |

##### EnergyAwarenessSearchType
Undocumented

| Value | Effect |
| --- | --- |
| _Thorough_ | Analyzes PE combinations one by one until the performance objective is reached |
| _Halves_ | Divides in halves the remaining available PEs and goes up/down depending if the FPS reached are below/above the objective |


### Simple Scheduling from DAG - _Deprecated_

  * **Identifier**: `org.ietr.preesm.plugin.mapper.simple`
  * **Implementing Class**: `org.preesm.algorithm.mapper.MainCoreMappingFromDAG`
  * **Short description**: Undocumented

#### Inputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **architecture** (of _Design_)
  * **scenario** (of _Scenario_)

#### Outputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **ABC** (of _LatencyAbc_)

#### Description
Undocumented

#### Parameters
None.


### Simple Scheduling from PiSDF

  * **Identifier**: `pisdf-mapper.simple`
  * **Implementing Class**: `org.preesm.algorithm.mapper.MainCoreMappingFromPiMM`
  * **Short description**: Undocumented

#### Inputs
  * **PiMM** (of _PiGraph_)
  * **architecture** (of _Design_)
  * **scenario** (of _Scenario_)

#### Outputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **ABC** (of _LatencyAbc_)

#### Description
Undocumented

#### Parameters
None.


### PFast Scheduling from SDF - _Deprecated_

  * **Identifier**: `org.ietr.preesm.plugin.mapper.pfast`
  * **Implementing Class**: `org.preesm.algorithm.mapper.PFASTMapping`
  * **Short description**: Undocumented

#### Inputs
  * **SDF** (of _SDFGraph_)
  * **architecture** (of _Design_)
  * **scenario** (of _Scenario_)

#### Outputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **ABC** (of _LatencyAbc_)

#### Description
Undocumented

#### Parameters

##### edgeSchedType
Undocumented

| Value | Effect |
| --- | --- |
| _Simple_ | Undocumented |

##### simulatorType
Undocumented

| Value | Effect |
| --- | --- |
| _LooselyTimed_ | Undocumented |

##### Check
Undocumented

| Value | Effect |
| --- | --- |
| _True_ | Undocumented |

##### Optimize synchronization
Undocumented

| Value | Effect |
| --- | --- |
| _False_ | Undocumented |

##### balanceLoads
Undocumented

| Value | Effect |
| --- | --- |
| _false_ | Undocumented |

##### displaySolutions
Undocumented

| Value | Effect |
| --- | --- |
| _false_ | Undocumented |

##### fastTime
Undocumented

| Value | Effect |
| --- | --- |
| _100_ | Undocumented |

##### fastLocalSearchTime
Undocumented

| Value | Effect |
| --- | --- |
| _10_ | Undocumented |

##### nodesMin
Undocumented

| Value | Effect |
| --- | --- |
| _5_ | Undocumented |

##### procNumber
Undocumented

| Value | Effect |
| --- | --- |
| _1_ | Undocumented |

##### fastNumber
Undocumented

| Value | Effect |
| --- | --- |
| _100_ | Undocumented |


### PFast Scheduling from DAG - _Deprecated_

  * **Identifier**: `org.ietr.preesm.plugin.mapper.pfastdag`
  * **Implementing Class**: `org.preesm.algorithm.mapper.PFASTMappingFromDAG`
  * **Short description**: Undocumented

#### Inputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **architecture** (of _Design_)
  * **scenario** (of _Scenario_)

#### Outputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **ABC** (of _LatencyAbc_)

#### Description
Undocumented

#### Parameters

##### edgeSchedType
Undocumented

| Value | Effect |
| --- | --- |
| _Simple_ | Undocumented |

##### simulatorType
Undocumented

| Value | Effect |
| --- | --- |
| _LooselyTimed_ | Undocumented |

##### Check
Undocumented

| Value | Effect |
| --- | --- |
| _True_ | Undocumented |

##### Optimize synchronization
Undocumented

| Value | Effect |
| --- | --- |
| _False_ | Undocumented |

##### balanceLoads
Undocumented

| Value | Effect |
| --- | --- |
| _false_ | Undocumented |

##### displaySolutions
Undocumented

| Value | Effect |
| --- | --- |
| _false_ | Undocumented |

##### fastTime
Undocumented

| Value | Effect |
| --- | --- |
| _100_ | Undocumented |

##### fastLocalSearchTime
Undocumented

| Value | Effect |
| --- | --- |
| _10_ | Undocumented |

##### nodesMin
Undocumented

| Value | Effect |
| --- | --- |
| _5_ | Undocumented |

##### procNumber
Undocumented

| Value | Effect |
| --- | --- |
| _1_ | Undocumented |

##### fastNumber
Undocumented

| Value | Effect |
| --- | --- |
| _100_ | Undocumented |


### PFast Scheduling from PiSDF

  * **Identifier**: `pisdf-mapper.pfast`
  * **Implementing Class**: `org.preesm.algorithm.mapper.PFASTMappingFromPiMM`
  * **Short description**: Undocumented

#### Inputs
  * **PiMM** (of _PiGraph_)
  * **architecture** (of _Design_)
  * **scenario** (of _Scenario_)

#### Outputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **ABC** (of _LatencyAbc_)

#### Description
Undocumented

#### Parameters

##### edgeSchedType
Undocumented

| Value | Effect |
| --- | --- |
| _Simple_ | Undocumented |

##### simulatorType
Undocumented

| Value | Effect |
| --- | --- |
| _LooselyTimed_ | Undocumented |

##### Check
Undocumented

| Value | Effect |
| --- | --- |
| _True_ | Undocumented |

##### Optimize synchronization
Undocumented

| Value | Effect |
| --- | --- |
| _False_ | Undocumented |

##### balanceLoads
Undocumented

| Value | Effect |
| --- | --- |
| _false_ | Undocumented |

##### displaySolutions
Undocumented

| Value | Effect |
| --- | --- |
| _false_ | Undocumented |

##### fastTime
Undocumented

| Value | Effect |
| --- | --- |
| _100_ | Undocumented |

##### fastLocalSearchTime
Undocumented

| Value | Effect |
| --- | --- |
| _10_ | Undocumented |

##### nodesMin
Undocumented

| Value | Effect |
| --- | --- |
| _5_ | Undocumented |

##### procNumber
Undocumented

| Value | Effect |
| --- | --- |
| _1_ | Undocumented |

##### fastNumber
Undocumented

| Value | Effect |
| --- | --- |
| _100_ | Undocumented |

## Analysis

### Gantt Exporter

  * **Identifier**: `org.ietr.preesm.stats.exporter.StatsExporterTask`
  * **Implementing Class**: `org.preesm.algorithm.mapper.stats.exporter.StatsExporterTask`
  * **Short description**: This task exports scheduling results as a *.pgantt file that can be viewed using the ganttDisplay viewer [1].

#### Inputs
  * **ABC** (of _LatencyAbc_)
  * **scenario** (of _Scenario_)

#### Outputs
None.

#### Description
This task exports scheduling results as a *.pgantt file that can be viewed using the ganttDisplay viewer [1]. The exported *.pgantt file uses the XML syntax.

#### Parameters

##### path
Path of the exported *.pgantt file. If the specified directory does not exist, it will not be created.

| Value | Effect |
| --- | --- |
| _/path/in/proj_ | Path within the Preesm project containing the workflow where the ”Gantt Exporter” task is instantiated. Exported Gantt will be named as follows: **/path/in/proj/<scenario name> stats.pgantt**. If a graph with this name already exists in the given path, it will be overwritten. |

#### See Also

  * **[1]**: https://github.com/preesm/gantt-display


### Gantt Display

  * **Identifier**: `org.ietr.preesm.plugin.mapper.plot`
  * **Implementing Class**: `org.preesm.algorithm.mapper.ui.stats.StatEditorTransform`
  * **Short description**: Displays the result of a mapping/scheduling algorithm as a Gantt diagram.

#### Inputs
  * **ABC** (of _LatencyAbc_)
  * **scenario** (of _Scenario_)

#### Outputs
None.

#### Description
This task generates SDF3 code modeling the given SDF graph. SDF modeling in SDF3 follow the specification introduced by Stuijk et al. in [1].

Known Limitations: Here is a list of known limitations of the SDF3 importation process: Only SDF graphs can be imported, Actors of the SDF cannot be implemented on more than one processor type, Timings cannot depend on parameters since SDF3 does not support parameterized SDF.

#### Parameters
None.

#### See Also

  * **Speedup assessment chart**: Maxime Pelcat. Prototypage Rapide et Génération de Code pour DSP Multi-Coeurs Appliqués à la Couche Physique des Stations de Base 3GPP LTE. PhD thesis, INSA de Rennes, 2010.

## Memory Optimization

### Memory Allocation

  * **Identifier**: `org.ietr.preesm.memory.allocation.MemoryAllocatorTask`
  * **Implementing Class**: `org.preesm.algorithm.memory.allocation.MemoryAllocatorTask`
  * **Short description**: Perform the memory allocation for the given MEG.

#### Inputs
  * **MemEx** (of _MemoryExclusionGraph_) : Input Memory Exclusion Graph

#### Outputs
  * **MEGs** (of _Map_) : Map associating, for each memory element in the architecture, according to the chosen _Distribution_ parameter value, a Memory Exclusion Graph annotated with allocation information (i.e. buffer addresses, etc.).

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
| _DistributedOnly_ | Each PE is associated to a private memory bank that no other PE can access. (Currently supported only in the MPPA code generation.) |
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

#### Documented Errors

| Message | Explanation |
| --- | --- |
| **The obtained allocation was not valid because mutually exclusive memory objects have overlapping address ranges. The allocator is not working.** | When checking the result of a memory allocation, two memory objects linked with an exclusion in the MEG were allocated in overlapping memory spaces. The error is caused by an invalid memory allocation algorithm and should be corrected in the source code. |
| **The obtained allocation was not valid because there were unaligned memory objects. The allocator is not working.** | When checking the result of a memory allocation, some memory objects were found not to respect the Dala alignment parameter. The error is caused by an invalid memory allocation algorithm and should be corrected in the source code. |

#### See Also

  * **Memory Allocation Algorithms**: K. Desnos, M. Pelcat, J.-F. Nezan, and S. Aridhi. Pre-and post-scheduling memory allocation strategies on MPSoCs. In Electronic System Level Synthesis Conference (ESLsyn), 2013.
  * **Distributed Memory Allocation**: Karol Desnos, Maxime Pelcat, Jean-François Nezan, and Slaheddine Aridhi. Distributed memory allocation technique for synchronous dataflow graphs. In Signal Processing System (SiPS), Workshop on, pages 1–6. IEEE, 2016.
  * **Broadcast Merging**: K. Desnos, M. Pelcat, J.-F. Nezan, and S. Aridhi. Memory analysis and optimized allocation of dataflow applications on shared-memory MPSoCs. Journal of Signal Processing Systems, Springer, 2014.


### Memory Bounds Estimator

  * **Identifier**: `org.ietr.preesm.memory.bounds.MemoryBoundsEstimator`
  * **Implementing Class**: `org.preesm.algorithm.memory.bounds.MemoryBoundsEstimator`
  * **Short description**: Compute bounds of the amount of memory needed to allocate the MEG

#### Inputs
  * **MemEx** (of _MemoryExclusionGraph_)

#### Outputs
  * **MemEx** (of _MemoryExclusionGraph_)
  * **BoundMin** (of _Long_)
  * **BoundMax** (of _Long_)

#### Description
The analysis technique presented in [1] can be used in Preesm to derive bounds for the amount of memory that can be allocated for an application. The upper bound corresponds to the worst memory allocation possible for an application. The lower bound is a theoretical value that limits the minimum amount of memory that can be allocated. By definition, the lower bound is not always reachable, which means that it might be impossible to find an allocation with this optimal amount of memory. The minimum bound is found by solving the Maximum Weight Clique problem on the MEG. This task provides a convenient way to evaluate the quality of a memory allocation.

#### Parameters

##### Verbose
How verbose will this task be during its execution. In verbose mode, the task will log the name of the used solver, the start and completion time of the bound estimation algorithm. Computed bounds are always logged, even if the verbose parameter is set to false.

| Value | Effect |
| --- | --- |
| _false_ | (Default) The task will not log information. |
| _true_ | The task will log build and MEG information. |

##### Solver
Specify which algorithm is used to compute the lower bound.

| Value | Effect |
| --- | --- |
| _Heuristic_ | (Default) Heuristic algorithm described in [1] is used. This technique find an approximate solution. |
| _Ostergard_ | Östergård’s algorithm [2] is used. This technique finds an optimal solution, but has a potentially exponential complexity. |
| _Yamaguchi_ | Yamaguchi et al.’s algorithm [3] is used. This technique finds an optimal solution, but has a potentially exponential complexity. |

#### See Also

  * **[1]**: K. Desnos, M. Pelcat, J.-F. Nezan, and S. Aridhi. Memory bounds for the distributed execution of a hierarchical synchronous data-flow graph. In Embedded Computer Systems: Architectures, Modeling, and Simulation (SAMOS XII), 2012 International Conference on, 2012.
  * **[2]**: Patric R. J. Östergård. A new algorithm for the maximum-weight clique problem. Nordic J. of Computing, 8(4):424–436, December 2001.
  * **[3]**: K. Yamaguchi and S. Masuda. A new exact algorithm for the maximum weight clique problem. In 23rd International Conference on Circuit/Systems, Computers and Communications (ITC-CSCC’08), 2008.
  * **Memory Bounds**: K. Desnos, M. Pelcat, J.-F. Nezan, and S. Aridhi. Pre-and post-scheduling memory allocation strategies on MPSoCs. In Electronic System Level Synthesis Conference (ESLsyn), 2013.


### Serial Memory Bounds

  * **Identifier**: `org.ietr.preesm.memory.bounds.SerialMemoryBoundsEstimator`
  * **Implementing Class**: `org.preesm.algorithm.memory.bounds.SerialMemoryBoundsEstimator`
  * **Short description**: Compute bounds of the amount of memory needed to allocate the MEGs.

#### Inputs
  * **MEGs** (of _Map_)

#### Outputs
None.

#### Description
This task computes the memory bounds (see Memory Bound Estimator Task) for several MEGs, like the one produced by the Memory Allocation task.

#### Parameters

##### Verbose
How verbose will this task be during its execution. In verbose mode, the task will log the name of the used solver, the start and completion time of the bound estimation algorithm. Computed bounds are always logged, even if the verbose parameter is set to false.

| Value | Effect |
| --- | --- |
| _false_ | (Default) The task will not log information. |
| _true_ | The task will log build and MEG information. |

##### Solver
Specify which algorithm is used to compute the lower bound.

| Value | Effect |
| --- | --- |
| _Heuristic_ | (Default) Heuristic algorithm described in [1] is used. This technique find an approximate solution. |
| _Ostergard_ | Östergård’s algorithm [2] is used. This technique finds an optimal solution, but has a potentially exponential complexity. |
| _Yamaguchi_ | Yamaguchi et al.’s algorithm [3] is used. This technique finds an optimal solution, but has a potentially exponential complexity. |

#### See Also

  * **[1]**: K. Desnos, M. Pelcat, J.-F. Nezan, and S. Aridhi. Memory bounds for the distributed execution of a hierarchical synchronous data-flow graph. In Embedded Computer Systems: Architectures, Modeling, and Simulation (SAMOS XII), 2012 International Conference on, 2012.
  * **[2]**: Patric R. J. Östergård. A new algorithm for the maximum-weight clique problem. Nordic J. of Computing, 8(4):424–436, December 2001.
  * **[3]**: K. Yamaguchi and S. Masuda. A new exact algorithm for the maximum weight clique problem. In 23rd International Conference on Circuit/Systems, Computers and Communications (ITC-CSCC’08), 2008.
  * **Memory Bounds**: K. Desnos, M. Pelcat, J.-F. Nezan, and S. Aridhi. Pre-and post-scheduling memory allocation strategies on MPSoCs. In Electronic System Level Synthesis Conference (ESLsyn), 2013.


### MEG Updater

  * **Identifier**: `org.ietr.preesm.memory.exclusiongraph.MemExUpdater`
  * **Implementing Class**: `org.preesm.algorithm.memory.exclusiongraph.MemExUpdater`
  * **Short description**: Relax memory allocation constraints of the MEG using scheduling information.

#### Inputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **MemEx** (of _MemoryExclusionGraph_)

#### Outputs
  * **MemEx** (of _MemoryExclusionGraph_)

#### Description
The MEG used in Preesm can be updated with scheduling information to remove exclusions between memory objects and make better allocations possible.

#### Parameters

##### Verbose
How verbose will this task be during its execution. In verbose mode, the task will log the start and completion time of the update, as well as characteristics (number of memory objects, density of exclusions) of the MEGs both before and after the update.

| Value | Effect |
| --- | --- |
| _false_ | (Default) The task will not log information. |
| _true_ | The task will log build and MEG information. |

#### See Also

  * **MEG update**: K. Desnos, M. Pelcat, J.-F. Nezan, and S. Aridhi. Pre-and post-scheduling memory allocation strategies on MPSoCs. In Electronic System Level Synthesis Conference (ESLsyn), 2013.


### MEG Builder

  * **Identifier**: `org.ietr.preesm.memory.exclusiongraph.MemoryExclusionGraphBuilder`
  * **Implementing Class**: `org.preesm.algorithm.memory.exclusiongraph.MemoryExclusionGraphBuilder`
  * **Short description**: Builds the Memory Exclusion Graph (MEG) modeling the memory allocation constraints.

#### Inputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **scenario** (of _Scenario_)

#### Outputs
  * **MemEx** (of _MemoryExclusionGraph_)

#### Description
The memory allocation technique used in Preesm is based on a Memory Exclusion Graph (MEG). A MEG is a graph whose vertices model the memory objects that must be allocated in memory in order to run the generated code. In the current version of Preesm, each of these memory objects corresponds either to an edge of the Directed Acyclic Graph (DAG) or to a buffer corresponding to ”delays” of the graph that store data between executions of a schedule. In the MEG, two memory objects are linked by an edge (called an exclusion) if they can not be allocated in overlapping memory spaces.

#### Parameters

##### Verbose
How verbose will this task be during its execution. In verbose mode, the task will log the start and completion time of the build, as well as characteristics (number of memory objects, density of exclusions) of the produced MEG.

| Value | Effect |
| --- | --- |
| _false_ | (Default) The task will not log information. |
| _true_ | The task will log build and MEG information. |

#### See Also

  * **MEG**: K. Desnos, M. Pelcat, J.-F. Nezan, and S. Aridhi. Memory bounds for the distributed execution of a hierarchical synchronous data-flow graph. In Embedded Computer Systems: Architectures, Modeling, and Simulation (SAMOS XII), 2012 International Conference on, 2012.


### Memory Scripts

  * **Identifier**: `org.ietr.preesm.memory.script.MemoryScriptTask`
  * **Implementing Class**: `org.preesm.algorithm.memory.script.MemoryScriptTask`
  * **Short description**: Executes the memory scripts associated to actors and merge buffers.

#### Inputs
  * **DAG** (of _DirectedAcyclicGraph_)
  * **MemEx** (of _MemoryExclusionGraph_)
  * **scenario** (of _Scenario_)

#### Outputs
  * **MemEx** (of _MemoryExclusionGraph_)

#### Description
Executes the memory scripts associated to actors and merge buffers. The purpose of the memory scripts is to allow Preesm to allocate input and output buffers of certain actors in overlapping memory range.

#### Parameters

##### Check
Verification policy used when checking the applicability of the memory scripts written by the developer and associated to the actor.

| Value | Effect |
| --- | --- |
| _Thorough_ | Will generate error messages with a detailed description of the source of the error. This policy should be used when writting memory scripts for the first time. |
| _Fast_ | All errors in memory script are still detected, but error messages are less verbose. This verification policy is faster than the Thorough policy. |
| _None_ | No verification is performed. Use this policy to speed up workflow execution once all memory scripts have been validated.. |

##### Data alignment
Option used to force the allocation of buffers with aligned addresses. The data alignment property should always have the same value as the one set in the properties of the Memory Allocation task.

| Value | Effect |
| --- | --- |
| _None_ | No special care is taken to align the buffers in memory. |
| _Data_ | All buffers are aligned on addresses that are multiples of their size. For example,a 4 bytes integer is aligned on 4 bytes address. |
| _Fixed:=$$n$$_ | Where $$n\in \mathbb{N}^*$$. This forces the allocation algorithm to align all buffers on addresses that are multiples of n bytes. |

##### Log Path
Specify whether, and where, a log of the buffer matching optimization should be generated. Generated log are in the markdown format, and provide information on all matches created by scripts as well as which match could be applied by the optimization process.

| Value | Effect |
| --- | --- |
| _path/file.txt_ | The path given in this property is relative to the ”Code generation directory” defined in the executed scenario. |
| _empty_ | No log will be generated. |

##### Verbose
Verbosity of the workflow task.

| Value | Effect |
| --- | --- |
| _True_ | The workflow task will be verbose in the console. |
| _False_ | The workflow task will be more quiet in the console. |

#### See Also

  * **Buffer merging**: Karol Desnos, Maxime Pelcat, Jean-François Nezan, and Slaheddine Aridhi. On memory reuse between inputs and outputs of dataflow actors. ACM Transactions on Embedded Computing Systems, 15(30):25, January 2016.

## Synhtesis

### Simple Synhtesis

  * **Identifier**: `pisdf-synthesis.simple`
  * **Implementing Class**: `org.preesm.algorithm.synthesis.PreesmSynthesisTask`
  * **Short description**: Undocumented

#### Inputs
  * **PiMM** (of _PiGraph_)
  * **architecture** (of _Design_)
  * **scenario** (of _Scenario_)

#### Outputs
  * **Schedule** (of _Schedule_)
  * **Mapping** (of _Mapping_)
  * **Allocation** (of _Allocation_)

#### Description
Undocumented

#### Parameters
None.

## Code Generation

### Spider Codegen

  * **Identifier**: `org.ietr.preesm.pimm.algorithm.spider.codegen.SpiderCodegenTask`
  * **Implementing Class**: `org.preesm.codegen.xtend.spider.SpiderCodegenTask`
  * **Short description**: Generate Spider code for dynamic PiSDF.

#### Inputs
  * **PiMM** (of _PiGraph_)
  * **scenario** (of _Scenario_)
  * **architecture** (of _Design_)

#### Outputs
None.

#### Description
Generate Spider code for dynamic PiSDF.

#### Parameters

##### scheduler
Runtime scheduler to use.

| Value | Effect |
| --- | --- |
| _list_on_the_go_ | Undocumented |
| _round_robin_ | Undocumented |
| _round_robin_scattered_ | Undocumented |
| _list_ | (Default) |

##### memory-alloc
Runtime memory allocation to use.

| Value | Effect |
| --- | --- |
| _special-actors_ | Undocumented |
| _dummy_ | (Default) |

##### shared-memory-size
Size of the shared memory allocated by Spider.

| Value | Effect |
| --- | --- |
| _$$n$$_ | $$n > 0$$ bytes. (Default = 67108864) |

##### papify
Use of PAPIFY. Select type of feedback given too

| Value | Effect |
| --- | --- |
| _off_ | PAPIFY is off |
| _dump_ | PAPIFY is on. Print csv files |
| _feedback_ | PAPIFY is on. Give feedback to the GRT |
| _both_ | PAPIFY is on. Print csv files and give feedback to the GRT |

##### apollo
Whether to use Apollo.

| Value | Effect |
| --- | --- |
| _true / false_ |  |

##### verbose
Whether to log.

| Value | Effect |
| --- | --- |
| _true / false_ |  |

##### trace
Whether to trace what is happening at runtime.

| Value | Effect |
| --- | --- |
| _true / false_ |  |

##### stack-type
Type of stack to use

| Value | Effect |
| --- | --- |
| _static_ | Use static stack |
| _dynamic_ | Use dynamic stack |

##### graph-optims
Whether to optimize the graph at runtime or not

| Value | Effect |
| --- | --- |
| _true / false_ |  |

#### See Also

  * **Spider**: Heulot, Julien; Pelcat, Maxime; Desnos, Karol; Nezan, Jean-François; Aridhi, Slaheddine (2014) “SPIDER: A Synchronous Parameterized and Interfaced Dataflow-Based RTOS for Multicore DSPs”. EDERC 2014, Milan, Italy.


### Code Generation

  * **Identifier**: `org.ietr.preesm.codegen.xtend.task.CodegenTask`
  * **Implementing Class**: `org.preesm.codegen.xtend.task.CodegenTask`
  * **Short description**: Generate code for the application deployment resulting from the workflow execution.

#### Inputs
  * **MEGs** (of _Map_)
  * **DAG** (of _DirectedAcyclicGraph_)
  * **scenario** (of _Scenario_)
  * **architecture** (of _Design_)

#### Outputs
None.

#### Description
This workflow task is responsible for generating code for the application deployment resulting from the workflow execution.

The generated code makes use of 2 macros that can be overridden in the **preesm.h** user header file:
*  **PREESM_VERBOSE** : if defined, the code will print extra info about actor firing;
*  **PREESM_LOOP_SIZE** : when set to an integer value $$n > 0$$, the application will terminate after $$n$$ executions of the graph.
*  **PREESM_NO_AFFINITY** : if defined, the part of the code that sets the affinity to specific cores will be skipped;

When the loop size macro is omitted, the execution can be stopped by setting the global variable **preesmStopThreads** to 1. This variable is defined in the **main.c** generated file, and should be accessed using extern keyword.

#### Parameters

##### Printer
Specify which printer should be used to generate code. Printers are defined in Preesm source code using an extension mechanism that make it possible to define a single printer name for several targeted architecture. Hence, depending on the type of PEs declared in the architecture model, Preesm will automatically select the associated printer class, if it exists.

| Value | Effect |
| --- | --- |
| _C_ | Print C code and shared-memory based communications. Currently compatible with x86, c6678, and arm architectures. |
| _InstrumentedC_ | Print C code instrumented with profiling code, and shared-memory based communications. Currently compatible with x86, c6678 architectures.. |
| _XML_ | Print XML code with all informations used by other printers to print code. Compatible with x86, c6678. |

##### Papify
Enable the PAPI-based code instrumentation provided by PAPIFY

| Value | Effect |
| --- | --- |
| _true/false_ | Print C code instrumented with PAPIFY function calls based on the user-defined configuration of PAPIFY tab in the scenario. Currently compatibe with x86 and MPPA-256 |

##### Apollo
Enable the use of Apollo for intra-actor optimization

| Value | Effect |
| --- | --- |
| _true/false_ | Print C code with Apollo function calls. Currently compatibe with x86 |


### Code Generation with cluster

  * **Identifier**: `org.ietr.preesm.codegen.xtend.task.CodegenClusterTask`
  * **Implementing Class**: `org.preesm.codegen.xtend.task.CodegenWithClusterTask`
  * **Short description**: Generate code for the application deployment resulting from the workflow execution.

#### Inputs
  * **MEGs** (of _Map_)
  * **DAG** (of _DirectedAcyclicGraph_)
  * **scenario** (of _Scenario_)
  * **architecture** (of _Design_)
  * **schedules** (of _Map_)

#### Outputs
None.

#### Description
This workflow task is responsible for generating code for the application deployment resulting from the workflow execution.

The generated code makes use of 2 macros that can be overridden in the **preesm.h** user header file:
*  **PREESM_VERBOSE** : if defined, the code will print extra info about actor firing;
*  **PREESM_LOOP_SIZE** : when set to an integer value $$n > 0$$, the application will terminate after $$n$$ executions of the graph.
*  **PREESM_NO_AFFINITY** : if defined, the part of the code that sets the affinity to specific cores will be skipped;

When the loop size macro is omitted, the execution can be stopped by setting the global variable **preesmStopThreads** to 1. This variable is defined in the **main.c** generated file, and should be accessed using extern keyword.

#### Parameters

##### Printer
Specify which printer should be used to generate code. Printers are defined in Preesm source code using an extension mechanism that make it possible to define a single printer name for several targeted architecture. Hence, depending on the type of PEs declared in the architecture model, Preesm will automatically select the associated printer class, if it exists.

| Value | Effect |
| --- | --- |
| _C_ | Print C code and shared-memory based communications. Currently compatible with x86, c6678, and arm architectures. |
| _InstrumentedC_ | Print C code instrumented with profiling code, and shared-memory based communications. Currently compatible with x86, c6678 architectures.. |
| _XML_ | Print XML code with all informations used by other printers to print code. Compatible with x86, c6678. |

##### Papify
Enable the PAPI-based code instrumentation provided by PAPIFY

| Value | Effect |
| --- | --- |
| _true/false_ | Print C code instrumented with PAPIFY function calls based on the user-defined configuration of PAPIFY tab in the scenario. Currently compatibe with x86 and MPPA-256 |

## Code Generation 2

### Code Generation 2

  * **Identifier**: `codegen2`
  * **Implementing Class**: `org.preesm.codegen.xtend.task.CodegenTask2`
  * **Short description**: Undocumented

#### Inputs
  * **PiMM** (of _PiGraph_)
  * **scenario** (of _Scenario_)
  * **architecture** (of _Design_)
  * **Schedule** (of _Schedule_)
  * **Mapping** (of _Mapping_)
  * **Allocation** (of _Allocation_)

#### Outputs
None.

#### Description
Undocumented

#### Parameters

##### Printer
Specify which printer should be used to generate code. Printers are defined in Preesm source code using an extension mechanism that make it possible to define a single printer name for several targeted architecture. Hence, depending on the type of PEs declared in the architecture model, Preesm will automatically select the associated printer class, if it exists.

| Value | Effect |
| --- | --- |
| _C_ | Print C code and shared-memory based communications. Currently compatible with x86, c6678, and arm architectures. |
| _InstrumentedC_ | Print C code instrumented with profiling code, and shared-memory based communications. Currently compatible with x86, c6678 architectures.. |
| _XML_ | Print XML code with all informations used by other printers to print code. Compatible with x86, c6678. |

##### Papify
Enable the PAPI-based code instrumentation provided by PAPIFY

| Value | Effect |
| --- | --- |
| _true/false_ | Print C code instrumented with PAPIFY function calls based on the user-defined configuration of PAPIFY tab in the scenario. Currently compatibe with x86 and MPPA-256 |

## Other

### Clustering

  * **Identifier**: `org.ietr.preesm.pisdfclustering`
  * **Implementing Class**: `org.preesm.algorithm.clustering.Clustering`
  * **Short description**: Undocumented

#### Inputs
  * **PiMM** (of _PiGraph_)
  * **scenario** (of _Scenario_)

#### Outputs
  * **PiMM** (of _PiGraph_)
  * **schedules** (of _Map_)

#### Description
Workflow task responsible for clustering hierarchical actors.

#### Parameters

##### Algorithm
Undocumented

| Value | Effect |
| --- | --- |
| _APGAN_ |  |
| _Dummy_ |  |
| _Random_ |  |
| _Parallel_ |  |

##### Seed
Undocumented

| Value | Effect |
| --- | --- |
| _$$n\in \mathbb{N}^*$$_ | Seed for random generator |


### Clustering

  * **Identifier**: `org.ietr.preesm.Clustering`
  * **Implementing Class**: `org.preesm.algorithm.clustering.OldClustering`
  * **Short description**: Undocumented

#### Inputs
  * **SDF** (of _SDFGraph_)
  * **scenario** (of _Scenario_)
  * **architecture** (of _Design_)

#### Outputs
  * **SDF** (of _SDFGraph_)

#### Description
Workflow task responsible for clustering hierarchical actors.

#### Parameters
None.


### SDF4J Exporter - _Deprecated_

  * **Identifier**: `sdf4j-export`
  * **Implementing Class**: `org.preesm.algorithm.io.xml.SDF4JGMLExporter`
  * **Short description**: Undocumented

#### Inputs
  * **SDF** (of _SDFGraph_)

#### Outputs
None.

#### Description
Undocumented

#### Parameters

##### path
Undocumented

| Value | Effect |
| --- | --- |
| __ | Undocumented |


### Latency Evaluation

  * **Identifier**: `org.ietr.preesm.latency.LatencyEvaluationPlugin`
  * **Implementing Class**: `org.preesm.algorithm.latency.LatencyEvaluationTask`
  * **Short description**: Undocumented

#### Inputs
  * **SDF** (of _SDFGraph_)
  * **scenario** (of _Scenario_)

#### Outputs
  * **SDF** (of _SDFGraph_)
  * **scenario** (of _Scenario_)
  * **latency** (of _Double_)

#### Description
Undocumented

#### Parameters

##### multicore
Undocumented

| Value | Effect |
| --- | --- |
| _true/false_ |  |

##### method
Undocumented

| Value | Effect |
| --- | --- |
| _FAST_ | (default) Hierarchical method |
| _FLAT_LP_ | Based on Flattening the hierarchy |
| _FLAT_SE_ | Based on Flattening the hierarchy |


### GetPiMM

  * **Identifier**: `org.ietr.preesm.mapper.getpimm`
  * **Implementing Class**: `org.preesm.algorithm.mapper.GetPiMMFromDAGTask`
  * **Short description**: Undocumented

#### Inputs
  * **DAG** (of _DirectedAcyclicGraph_)

#### Outputs
  * **PiMM** (of _PiGraph_)

#### Description
Undocumented

#### Parameters
None.


### Hierarchical Scheduler - _Deprecated_

  * **Identifier**: `hsceduler`
  * **Implementing Class**: `org.preesm.algorithm.mapper.algo.HScheduleTask`
  * **Short description**: Undocumented

#### Inputs
None.

#### Outputs
None.

#### Description
Undocumented

#### Parameters
None.


### Implementation Exporter

  * **Identifier**: `org.ietr.preesm.plugin.mapper.exporter.ImplExportTransform`
  * **Implementing Class**: `org.preesm.algorithm.mapper.exporter.ImplExportTransform`
  * **Short description**: Undocumented

#### Inputs
  * **DAG** (of _DirectedAcyclicGraph_)

#### Outputs
  * **xml** (of _String_)

#### Description
Undocumented

#### Parameters

##### path
Undocumented

| Value | Effect |
| --- | --- |
| __ | Undocumented |


### Single rate SDF to DAG Transformation

  * **Identifier**: `org.ietr.preesm.mapper.SDF2DAGTransformation`
  * **Implementing Class**: `org.preesm.algorithm.mapper.graphtransfo.SDF2DAGTransformation`
  * **Short description**: Undocumented

#### Inputs
  * **SDF** (of _SDFGraph_)
  * **scenario** (of _Scenario_)
  * **architecture** (of _Design_)

#### Outputs
  * **DAG** (of _DirectedAcyclicGraph_)

#### Description
Undocumented

#### Parameters
None.


### Memory Exclusion Graph Mapper

  * **Identifier**: `org.ietr.preesm.memory.distributed.MapperTask`
  * **Implementing Class**: `org.preesm.algorithm.memory.distributed.MapperTask`
  * **Short description**: Undocumented

#### Inputs
  * **MemEx** (of _MemoryExclusionGraph_)

#### Outputs
  * **MemExes** (of _Map_)

#### Description
Undocumented

#### Parameters

##### Verbose
Undocumented

| Value | Effect |
| --- | --- |
| _? C {True, False}_ |  |

##### Distribution
Specify which memory architecture should be used to allocate the memory.

| Value | Effect |
| --- | --- |
| _SharedOnly_ | (Default) All memory objects are allocated in a single memory bank accessible to all PE. |
| _DistributedOnly_ | Each PE is associated to a private memory bank that no other PE can access. (Currently not supported by code generation.) |
| _Mixed_ | Both private memory banks and a shared memory can be used for allocating memory. |
| _MixedMerged_ | Same as mixed, but the memory allocation algorithm favors buffer merging over memory distribution. |


### Activity Exporter of Tokens and Quanta

  * **Identifier**: `org.ietr.preesm.algorithm.moa.activity.ActivityExporter`
  * **Implementing Class**: `org.preesm.algorithm.moa.activity.ActivityExporter`
  * **Short description**: Undocumented

#### Inputs
  * **ABC** (of _LatencyAbc_)

#### Outputs
None.

#### Description
Undocumented

#### Parameters

##### path
Undocumented

| Value | Effect |
| --- | --- |
| _stats/mat/activity_ | Undocumented |

##### human_readable
Undocumented

| Value | Effect |
| --- | --- |
| _Yes_ | Undocumented |


### Activity Exporter of Custom Quanta

  * **Identifier**: `org.ietr.preesm.algorithm.moa.activity.CustomQuantaExporter`
  * **Implementing Class**: `org.preesm.algorithm.moa.activity.CustomQuantaExporter`
  * **Short description**: Undocumented

#### Inputs
  * **ABC** (of _LatencyAbc_)

#### Outputs
None.

#### Description
Undocumented

#### Parameters

##### xls_file
Undocumented

| Value | Effect |
| --- | --- |
| _stats/mat/custom_quanta_in/quanta_in_$SCENARIO$.xls_ |  |

##### path
Undocumented

| Value | Effect |
| --- | --- |
| _stats/mat/activity_ |  |

##### human_readable
Undocumented

| Value | Effect |
| --- | --- |
| _Yes_ |  |


### Activity Exporter of Tokens and Quanta for a single ABC

  * **Identifier**: `org.ietr.preesm.algorithm.moa.activity.MonoActivityExporter`
  * **Implementing Class**: `org.preesm.algorithm.moa.activity.MonoActivityExporter`
  * **Short description**: Undocumented

#### Inputs
  * **ABC** (of _LatencyAbc_)

#### Outputs
None.

#### Description
Undocumented

#### Parameters

##### path
Undocumented

| Value | Effect |
| --- | --- |
| _stats/mat/activity_ |  |

##### human_readable
Undocumented

| Value | Effect |
| --- | --- |
| _Yes_ |  |


### Periods Prescheduling Checker

  * **Identifier**: `org.ietr.preesm.pimm.algorithm.checker.periods.PeriodsPreschedulingChecker`
  * **Implementing Class**: `org.preesm.algorithm.pisdf.checker.PeriodsPreschedulingChecker`
  * **Short description**: Undocumented

#### Inputs
  * **PiMM** (of _PiGraph_)
  * **scenario** (of _Scenario_)
  * **architecture** (of _Design_)

#### Outputs
  * **PiMM** (of _PiGraph_)

#### Description
Undocumented

#### Parameters

##### Selection rate (%)
Undocumented

| Value | Effect |
| --- | --- |
| _100_ |  |


### Throughput Evaluation

  * **Identifier**: `org.ietr.preesm.throughput.ThroughputPlugin`
  * **Implementing Class**: `org.preesm.algorithm.throughput.ThroughputEvaluationTask`
  * **Short description**: Undocumented

#### Inputs
  * **SDF** (of _SDFGraph_)
  * **scenario** (of _Scenario_)

#### Outputs
  * **throughput** (of _Double_)
  * **SDF** (of _SDFGraph_)
  * **scenario** (of _Scenario_)

#### Description
Undocumented

#### Parameters

##### method
Undocumented

| Value | Effect |
| --- | --- |
| _SR_ | Schedule-Replace technique |
| _ESR_ | Evaluate-Schedule-Replace method |
| _HPeriodic_ | Hierarchical Periodic Schedule method |
| _Classical_ | Based on Flattening the hierarchy |


### SDF2HSDF

  * **Identifier**: `org.ietr.preesm.plugin.transforms.sdf2hsdf`
  * **Implementing Class**: `org.preesm.algorithm.transforms.HSDFTransformation`
  * **Short description**: Undocumented

#### Inputs
  * **SDF** (of _SDFGraph_)

#### Outputs
  * **SDF** (of _SDFGraph_)

#### Description
Undocumented

#### Parameters

##### ExplodeImplodeSuppr
Undocumented

| Value | Effect |
| --- | --- |
| _false_ |  |


### Algorithm Iterator

  * **Identifier**: `org.ietr.preesm.algorithm.transforms.IterateAlgorithm`
  * **Implementing Class**: `org.preesm.algorithm.transforms.IterateAlgorithm`
  * **Short description**: Undocumented

#### Inputs
  * **SDF** (of _SDFGraph_)

#### Outputs
  * **SDF** (of _SDFGraph_)

#### Description
Undocumented

#### Parameters

##### nbIt
Undocumented

| Value | Effect |
| --- | --- |
| _1_ |  |

##### setStates
Undocumented

| Value | Effect |
| --- | --- |
| _true_ |  |


### Papify Engine - _Deprecated_

  * **Identifier**: `org.ietr.preesm.codegen.xtend.task.CodegenPapifyEngineTask`
  * **Implementing Class**: `org.preesm.codegen.xtend.task.CodegenPapifyEngineTask`
  * **Short description**: Deprecated - does nothing (as of v3.9.1). See parameter 'Papify' in CodegenTask'.

Old doc: Generate the required instrumentation code for the application based on the PAPIFY tab information.

#### Inputs
  * **scenario** (of _Scenario_)
  * **DAG** (of _DirectedAcyclicGraph_)

#### Outputs
  * **DAG** (of _DirectedAcyclicGraph_)

#### Description
Deprecated - does nothing (as of v3.9.1). See parameter 'Papify' in CodegenTask'.

Old doc: This workflow task is responsible for generating the instrumentation of the code for the application based on the PAPIFY tab information.

The generated code makes use of 1 macro that enables/disables the monitoring in the **preesm.h** user header file:
*  **_PREESM_PAPIFY_MONITOR** : if defined, the code monitoring will take place;


#### Parameters
None.


### PiSDF BRV Exporter

  * **Identifier**: `pisdf-brv-export`
  * **Implementing Class**: `org.preesm.model.pisdf.brv.BRVExporter`
  * **Short description**: Undocumented

#### Inputs
  * **PiMM** (of _PiGraph_)

#### Outputs
  * **PiMM** (of _PiGraph_)

#### Description
Undocumented

#### Parameters

##### path
Undocumented

| Value | Effect |
| --- | --- |
| _/stats/xml/_ | default value |


### PiSDF Checker

  * **Identifier**: `pisdf-checker`
  * **Implementing Class**: `org.preesm.model.pisdf.check.PiMMAlgorithmCheckerTask`
  * **Short description**: Undocumented

#### Inputs
  * **PiMM** (of _PiGraph_)

#### Outputs
  * **PiMM** (of _PiGraph_)

#### Description
Undocumented

#### Parameters
None.


### scenario

  * **Identifier**: `org.ietr.preesm.scenario.task`
  * **Implementing Class**: `org.preesm.model.scenario.workflow.AlgorithmAndArchitectureScenarioNode`
  * **Short description**: Undocumented

#### Inputs
None.

#### Outputs
  * **scenario** (of _Scenario_)
  * **architecture** (of _Design_)
  * **PiMM** (of _PiGraph_)

#### Description
Undocumented

#### Parameters
None.


### Slam Flattener

  * **Identifier**: `org.ietr.preesm.archi.slam.flattening`
  * **Implementing Class**: `org.preesm.model.slam.utils.SlamHierarchyFlattening`
  * **Short description**: Undocumented

#### Inputs
  * **architecture** (of _Design_)

#### Outputs
  * **architecture** (of _Design_)

#### Description
Undocumented

#### Parameters

##### depth
Undocumented

| Value | Effect |
| --- | --- |
| _n > 0_ | default = 1 |

