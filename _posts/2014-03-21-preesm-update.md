---
title:  "Simultaneous release of PREESM, DFTools and Graphiti"
search: false
toc: false
last_modified_at: 2018-08-21T10:10:00-00:00
---

We just released version 1.1.0 of PREESM, version 1.1.0 of DFTools and version 1.3.12 of Graphiti. You can find these new versions on our [update site](http://preesm.insa-rennes.fr/repo/complete/).

### PREESM v1.1.0

New features (under the form of new PREESM workflow tasks):

*   Generation of C++ code from a PiMM model. The generated code build an PiSDF graph equivalent to the model when executed. Generation is done by the org.ietr.preesm.experiment.pimm.cppgenerator.scala.PiMMCppGenerationTask plug-in.
*   Connection of subgraphs of a PiMM model to obtain a fully connected model (i.e., where refinement files are replaced by connections between objects). Connection is done by the org.ietr.preesm.experiment.pimm.subgraph.connector.SubgraphConnectorTask plug-in.
*   Optimization of IBSDF graphs. The graphs are cleaned from useless vertices in order to reduce their size and speed up following tasks of the workflow. Optimization is done by the org.ietr.preesm.algorithm.optimization.AlgorithmOptimizationTask plug-in.
*   Export of IBSDF graphs to SDF For Free (SDF3) xml format. Export is done by the org.ietr.preesm.algorithm.exportSdf3Xml.Sdf3Exporter plug-in.

\+ Minor performance improvement in MEG addEdge operation.

### DFTools v1.1.0

New features:

*   Hierarchy flattening and single-rate transformation automatically add a new port for each fifo to special vertices (fork/join/broadcast/roundbuffer).
*   New port modifier: "unused".
*   Hierarchy flattening and single-rate transformation automatically add port modifiers to most special vertices
*   (fork/join/broadcast/roundbuffer).

Bugs fixed:

*   Insertion of edges with the wrong production rate, provoking non-schedulable graph errors (see [issue#3 of preesm repository](https://github.com/preesm/preesm/issues/3)).
*   Wrong BRV computation on unconnected actors.
*   Wrong BRV computationwith implicit interfaces.
*   Ports of Broadcast and RoundBuffer vertices were not cloned correctly.

### Graphiti v1.3.12

Code refactoring.
