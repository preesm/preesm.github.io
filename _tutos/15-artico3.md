---
title: "HW/SW code generation for MPSoCs"
permalink: /tutos/artico3/
toc: true
---

The following topics are covered in this tutorial:

* design and development of ARTICo³'s applications (with automatic code generation).

Prerequisite: 

* [Tutorial Introduction](/tutos/intro)
* [ARTICo³ - Framework Components and APIs](https://des-cei.github.io/tools/artico3).

###### Tutorial created the 07.23.2020 by [L. Suriano](mailto:leonardo.suriano@upm.es)

## Introduction

In order to despatch jobs on HW on the FPGA, we are making use of ARTICo³, an open-source runtime reconfigurable processing architecture to enable hardware-accelerated high-performance embedded computing. It provides (among the other features):

 * a HW architecture to dispatch jobs, send and receive data,  among multiple accelerators
 * a set of APIs to manage the HW accelerators
 * a framework to create the bitstreams for the FPGA 
 
 For more details (documentation and tutorials), please visit its [official website](https://des-cei.github.io/tools/artico3).

By describing the architecture (i.e., the set of CPUs and the set of accelerators), PREESM automatically generates the application code.

The application selected for this tutorial is a Matrix Multiplication. A tutorial for the HW accelerators' synthesis is available [here](https://des-cei.github.io/tools/artico3/tutorials/matmul). However, few modifications were necessary to allow a further parameter-input. The code is also provided within the instruction of this tutorial.

## Initial project setup

### 1 Clone the ARTICo3 repository

 1. clone the ARTICo³ repository and switch to the branch `a9c93c1`
 
 ```
$ git clone https://github.com/des-cei/artico3
$ cd artico3/
$ git checkout a9c93c1
 ```

## Deployment

Under construction.