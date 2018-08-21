---
title: "Build from Graphiti or DFTools Sources"
permalink: /docs/buildfromgraphiti-dftools/
toc: true
---

_We discourage development within DFTools and Graphiti. These projects will eventually be removed._

Since version 2.2.6 of Preesm, Graphiti and DFTools are dependencies resolved using the update site mechanism of Eclipse. Therefore the source code does not need to be present in the workspace. Nonetheless, it is still possible to setup an Eclipse to work with Graphiti and/or DFTools source code.

The new Eclipse setup procedure is based on an Eclise feature that references other features. Indeed, the feature "PREESM  Developper Requirements (Meta Feature)" references Graphiti, DFTools, Xtend, GEF, etc. The Graphiti and DFTools projects have received the same update in their build and deployment process. Therefore, configuring Eclipse for DFTools or Graphiti is similar to Preesm.

## Note about importing Releng projects

When importing the source code of Graphiti, DFTools and Preems including the projects under 'releng', you may encounter warnings in the workspace:

![](/assets/docs/x02-buildfromgraphiti-dftools-assets/feature_not_resolved.png)

This denotes the absence of some feature in the current Eclipse installation. It comes from the updated build process that automatically generates these '.source' features during the deployment phase. These warning should not be fixed. If you do not intend to change the release process, it is best to close or remove from the workspace all projects under 'releng' (that includes "*.complete.site", "*.feature", "*.product", "*.releng").

## Preesm source only

Follow the [Building Preesm](/docs/buildpreesm) procedure

## DFTools + Preesm source

Follow the [same procedure](/docs/buildpreesm), but instead of installing the "PREESM  Developper Requirements (Meta Feature)", select the DFTools one. Note that having several of these meta features installed at the same time can cause conflicts.

In addition to the Preesm git repository, clone the [DFTools git repository](https://github.com/preesm/dftools) from
* [https://github.com/preesm/dftools.git](https://github.com/preesm/dftools.git) (https)
* **or** ```git@github.com:preesm/dftools.git``` (SSH)

Then, import DFTools **and** Preesm source code using Import as Existing Maven Projects, as detailed in the [Preesm procedure](/docs/buildpreesm).

## Graphiti + DFTools + Preesm source

Follow the [same procedure](/docs/buildpreesm), but instead of installing the "PREESM  Developper Requirements (Meta Feature)", select the Graphiti one. Note that having several of these meta features installed at the same time can cause conflicts.

In addition to the Preesm git repository, clone the [DFTools git repository](https://github.com/preesm/dftools) (see above) **and** the [Graphiti git repository](https://github.com/preesm/graphiti) from:
* [https://github.com/preesm/graphiti.git](https://github.com/preesm/graphiti.git) (https)
* **or** ```git@github.com:preesm/graphiti.git``` (SSH)

Then, import projects from all 3 repositories (Graphiti, DFTools and Preesm) using Import as Existing Maven Projects, as detailed in the [Preesm procedure](/docs/buildpreesm).
