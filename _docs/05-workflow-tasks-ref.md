---
title: "Documentation | Workflow Tasks Reference"
permalink: /docs/workflowtasksref/
toc: true
---

## Graph Transformations

### Static PiMM to IBSDF

* Transforms a static PiSDF Graph into an equivalent IBSDF graph.
* Plugin Identifier: `org.ietr.preesm.experiment.pimm2sdf.StaticPiMM2SDFTask`

TODO

---

### Hierarchy Flattening

* Transforms a hierarchical IBSDF graph into an SDF graph.
* Plugin Identifier: `org.ietr.preesm.plugin.transforms.flathierarchy`

#### Inputs

* SDF: the IBSDF graph to flatten

#### Outputs

* SDF: the flattened SDF graph

#### Parameters

* depth: This parameter is used to select the number of hierarchy levels that will be flattened by the workflow task.}

| Value | Effect |
|-------|--------|
| 0 | The input IBSDF graph is copied to the output port of the workflow task with no modification. |
| n > 0 | The first n levels of the hierarchical IBSDF graph are flattened. |
| n < 0 | All levels are flattened (up to 2^32-1) |

* another parameter: for test

| Value | Effect |
|-------|--------|
| 0 | The input IBSDF graph is copied to the output port of the workflow task with no modification. |
| n > 0 | The first n levels of the hierarchical IBSDF graph are flattened. |
| n < 0 | All levels are flattened (up to ) |

#### Description

The purpose of this workflow task is to flatten several levels of the hierarchy of an IBSDF graph and produce an equivalent SDF graph.

A hierarchical IBSDF graph is a graph where the internal behavior of some actors is described using another IBSDF subgraph instead of a C header file. 

When applying this transformation, hierarchical IBSDF actors of the first $n$ levels of hierarchy are replaced with the actors of the IBSDF subgraph with which these hierarchical actors are associated.

See also: IBSDF \cite{Piat_2009_Interface}

#### Documented Errors

* **Inconsistent Hierarchy, graph can't be flattened** : Flattening of the IBSDF graph was aborted because one of the graph composing the application, at the top level or deeper in the hierarchy, was not consistent. See also: Graph consistency \cite{Lee_Synchronous_1987}.

---

another task...

