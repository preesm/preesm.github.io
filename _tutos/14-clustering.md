---
title: "Clustering - [BETA]"
permalink: /tutos/clustering/
toc: true
---

**Writing of this tutorial is currently in progress. If you wish to follow this tutorial, please consider yourself a beta tester (and don't hesitate to send us any improvement suggestions).**

The following topics are covered in this tutorial:
* Reduction of actor firings in PiSDF graph to reduce mapping and scheduling time.
* Code generation for a distributed architecture using the hierarchical mapping approach.

Prerequisite:
* [Tutorial Introduction](/tutos/intro)
* [Parallelize an Application on a Multicore CPU](/tutos/parasobel)

###### Last update the 19.05.2020 -  Tutorial created the 22.02.2020 by [D. Gageot](mailto:dgageot@insa-rennes.fr)

## Project setup

In addition to the default requirements (see [Requirements for Running Tutorial Generated Code](/tutos/intro/#requirements-for-running-tutorial-generated-code)), please download the following files:
*   Complete [Sobel-Morpho Preesm Project](https://github.com/preesm/preesm-apps/tree/master/org.ietr.preesm.sobel-morpho)
*   [YUV Sequence (7zip)](/assets/downloads/akiyo_cif.7z) (9 MB)
*   [DejaVu TTF Font](/assets/downloads/DejaVuSans.ttf) (757KB)

## Clustering techniques

### Why clustering a PiSDF graph?

Mapping and scheduling a Synchronous Dataflow Graph (SDFG) is a NP-complete problem.
This means that the time required to map and schedule an input graph is exponential to its number of actor firings and cores.
Methods such as hierarchical scheduling and hierarchical mapping are used to reduce the amount of data in input of these algorithms.

The **hierarchical scheduling** method consists of clustering actors in an SDFG to reduce the number of actor firings, then reducing mapping and scheduling time.
Actors contained in a dataflow cluster are sequentialized since the resulting cluster will be mapped on a single processing element.

The **hierarchical mapping** method is used with distributed architecture and aims to reduce the number of PEs of the targeted architecture by modeling only the cluster hierarchy in the System-Level Architecture (S-LAM).
For example, the manycore processor Kalray MPPA Bostan is composed of 16 compute clusters of 16 PEs.
It architecture model within PREESM only contains the cluster hierarchy.
In combination with the hierarchical scheduling method, actor firings contained in a dataflow cluster are parallelized inside a compute cluster.
The clustering technique used in PREESM is able to keep data parallelism over repeated actor firings in a dataflow cluster by using OpenMP.

In this tutorial, you will learn how to set up a workflow that features hierarchical scheduling and hierarchical mapping and apply it to the Sobel-Morpho project.
We are going to cover three different usage:
- Hierarchical scheduling with manual and automatic partitioning,
- Hierarchical mapping on one compute cluster,
- Hierarchical mapping on multiple compute clusters.

### Introduction to the clustering workflow

This section introduces the basic clustering workflow for both hierarchical scheduling and mapping. The workflow shown in the figure below has four components:
1. *Cluster Partitioner*:

  The Cluster Partitioner clusters automatically groups of actors in the input PiSDF graph.
2. *Cluster Scheduler*:

  The Cluster Scheduler computes the schedule for each cluster founded by the cluster partitioner or specified by hand by the user.
  The resulting schedule is modeled with a Cluster Schedule (CS), an intermediate representation that describes the internal schedule of a dataflow cluster while keeping parallelism information.
  If the targeted architecture is represented with its cluster hierarchy but has only one compute cluster, for example a basic shared-memory x86 processor, the Cluster Scheduler can be configured to cluster the whole input graph so that all actor firings are contained in a dataflow cluster.

3. *Mapping and scheduling*:

  It is the legacy mapping and scheduling workflow of PREESM.
  It chooses on which compute cluster to execute actor firings at coarse-grained level.

4. *Code Generator*:

  The Code Generator generates code from both DAG and CSs. Since CSs contain parallelism information such as data parallelism, OpenMP pragmas are printed on top of for-loops.

[![](/assets/tutos/clustering/workflow.png)](/assets/tutos/clustering/workflow.png)

### Setting-up the clustering workflow

To set-up the clustering workflow introduced in the previous section, please download the following [PREESM workflow](assets/tutos/clustering/CodegenClustering.workflow) and insert it into the `Workflows/` folder of the Sobel-Morpho project.

### Hierarchical scheduling

#### Manual partitioning

Manual partitioning of a PiSDF graph is used to force a specific PiSDF subgraph to be considered as a cluster.
It might be needed when the automatic clustering isn't able to cluster a group of actors that the developer wishes it to be.
To do so, the developer has to set-up a proper PiSDF subgraph, add it to the top graph and set it cluster attribute to true.
To practice, lets cluster actors Sobel, Dilation and Erosion together:

1. Create a new .pi graph and named it `cluster.pi`,
2. Duplicate `sobel_morpho.pi` and rename it `sobel_morpho_clustered.pi`,
3. Select actors Sobel, Dilation and Erosion in `sobel_morpho_clustered.pi` and copy them in `cluster.pi`,
4. Create *Config Input Interface* for each parameter `sobelHeight`, `dilationHeight`, `erosionHeight`, `width` and `window` in `cluster.pi` then connect them with the *Dependency* tool to actors of the cluster,
5. Create one *Data Input Interface* and one *Data Output Interface* in `cluster.pi`,
6. Connect the *Data Input Interface* with the *FIFO* tool to Sobel and then Erosion to the *Data Output Interface*,
7. Change type of new FIFOs from `void` to `uchar`,
8. Connect parameters to data port interfaces and set their firings as follow:
  - data input interface: `sobelHeight*width`,
  - data output interface: `(erosionHeight-2*window)*width`.
9. As this point, your subgraph should look like this one:
[![](/assets/tutos/clustering/cluster.png)](/assets/tutos/clustering/cluster.png)
10. We need to specify to PREESM that this subgraph is a cluster. To do so, open `cluster.pi` with *Text Editor* and add the attribute `cluster="true"` in the `graph` element such as shown by the following figure:
[![](/assets/tutos/clustering/attribute.png)](/assets/tutos/clustering/attribute.png)
11. Delete Sobel, Dilation and Erosion actors from `sobel_morpho_clustered.pi` and replace them with a new actor called Cluster with a refinement to `cluster.pi`,
12. Set-up its ports rates and parameters,
13. Connect Cluster to the output of Split and to the input of Display with the *FIFO* tool, don't forget to change type of FIFOs.
14. At this point, the top graph should look like this one:
[![](/assets/tutos/clustering/top_graph.png)](/assets/tutos/clustering/top_graph.png)
