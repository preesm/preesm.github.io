---
title: "Build from Graphiti or DFTools Sources"
permalink: /docs/buildfromgraphiti-dftools/
toc: true
---

**As of Preesm v3.0.0, DFTools code base has been merged in Preesm git repository.**

_We discourage development within Graphiti. This project will eventually be removed._

Since version 2.2.6 of Preesm, Graphiti is a dependency resolved using the update site mechanism of Eclipse. Therefore the source code does not need to be present in the workspace. Nonetheless, it is still possible to setup an Eclipse to work with Graphiti source code.

The new Eclipse setup procedure is based on an Eclise feature that references other features. Indeed, the feature "PREESM  Developper Requirements (Meta Feature)" references Graphiti, Xtend, GEF, etc. The Graphiti project have received the same update in their build and deployment process. Therefore, configuring Eclipse for Graphiti is similar to Preesm.

## Preesm source only

Follow the [Building Preesm](/docs/buildpreesm) procedure.

## Graphiti + Preesm source

Follow the [same procedure](/docs/buildpreesm), but instead of installing the "PREESM  Developper Requirements (Meta Feature)", select the Graphiti one (and only this one: having several of these meta features installed at the same time can cause conflicts).

In addition to the Preesm git repository, clone the [Graphiti git repository](https://github.com/preesm/graphiti) from:
* [https://github.com/preesm/graphiti.git](https://github.com/preesm/graphiti.git) (https)
* **or** ```git@github.com:preesm/graphiti.git``` (SSH)

Then, import projects from the 2 repositories (Graphiti and Preesm) using Import as Existing Maven Projects, as detailed in the [Preesm procedure](/docs/buildpreesm).
