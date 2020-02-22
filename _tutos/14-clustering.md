---
title: "Clustering - [BETA]"
permalink: /tutos/clustering/
toc: true
---

**Writing of this tutorial is currently in progress. If you wish to follow this tutorial, please consider yourself a beta tester (and don't hesitate to send us any improvement suggestions).**

The following topics are covered in this tutorial:
*   Complexity reduction of an input PiSDF graph
*   Generate OpenMP pragma on top of for-loops

Prerequisite:
* [Tutorial Introduction](/tutos/intro)
* [Parallelize an Application on a Multicore CPU](/tutos/parasobel)

###### Last update the 22.02.2020 -  Tutorial created the 22.02.2020 by [D. Gageot](mailto:dgageot@insa-rennes.fr)

## Project setup

In addition to the default requirements (see [Requirements for Running Tutorial Generated Code](/tutos/intro/#requirements-for-running-tutorial-generated-code)), please download the following files:
*   Complete [Sobel Preesm Project](/assets/tutos/parasobel/tutorial1_result.zip)
*   [YUV Sequence (7zip)](/assets/downloads/akiyo_cif.7z) (9 MB)
*   [DejaVu TTF Font](/assets/downloads/DejaVuSans.ttf) (757KB)

## Clustering

Mapping/scheduling an Synchronous Dataflow Graph (SDFG) is a NP-complete problem, meaning that the time required to map/schedule an input graph is exponential to its number of actor firings. A technique can be used to reduce the mapping/scheduling time: this technique is based on graph clustering. The disadvantage of clustering is the loss of parallelism information. The clustering technique used in PREESM is able to keep data parallelism within a cluster by using OpenMP on top of for-loops. In this tutorial, you will learn how to set up a workflow that features clustering technique and apply it to the Sobel project from previous tutorials.

 ## Workflow modification
