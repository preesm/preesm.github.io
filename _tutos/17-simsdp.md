---
title: "SimSDP: Multinode Design Space Exploration"
permalink: /tutos/simsdp/
toc: true
---
In this tutorial, you will learn how to use the Simulator for Science Data Processor (SimSDP) to deploy a dataflow application on a heterogeneous multi-Node multicore architecture.

The following topics are covered in this tutorial:

* Implementation of a Median Absolute Deviation (MAD)-based Radio Frequency Interference (RFI) filter with PREESM
* Multinode Multicore Partitioning
* Multinode Multicore Code Generation

Prerequisite:

* Install PREESM see [getting PREESM](https://preesm.github.io/get/)
* This tutorial is design for Unix system

###### Tutorial created the 7.11.2023 by [O. Renaud](mailto:orenaud@insa-rennes.fr)

## Introduction

### Heterogeneous multi-Node multicore architecture

The architectures considered in this tutorial are called heterogeneous multinode multicore architectures. These architectures consist of multiple nodes (or machines), and within each node, there are multiple processing cores. However, what sets this architecture apart is that the nodes and cores are not all identical but rather heterogeneous, meaning they can have different performance characteristics. Notably, communication between cores takes place through shared memory within a node and through distributed memory between nodes as illustrated below. 
This architecture is commonly used in high-performance computing (HPC) systems, computing clusters, embedded systems, and other domains where parallelization is essential.

[![](/assets/tutos/simsdp/archi.png)](/assets/tutos/simsdp/archi.png)

In the rest of this tutorial, we'll characterize the architecture as follows:
- **The number of nodes**: This refers to the count of individual machines or computational units in the architecture.
- **The internode communication rate**: This characterizes the speed at which data can be transmitted between different nodes within the architecture. It is a measure of how fast information can be shared or exchanged between the various nodes.
- **The number of core per nodes**: This represents the quantity of processing cores (or computing units) present within each individual node. 
- **The intranode communication rate per node**: This indicates the speed at which data can be communicated or shared among the cores within a single node. It measures how quickly cores within the same node can exchange data or work togethe
- **The core frequency**: Core frequency, often measured in Hertz (Hz), represents the clock speed of each processing core. It determines how quickly the core can execute instructions. Higher core frequencies typically result in faster individual core performance.

### Principle
SimSDP is an iterative algorithm integrated into PREESM, designed to achieve load balancing across the available nodes, thereby optimizing performance in terms of latency [\[1\]](#references).
* **Node-Level Partitioning**: This step is to divide the graph into subgraphs, each associated with a node.The subgraphs are constructed in such a way as to obtain an balance workload, taking into account the heterogeneity of the nodes.
* **Thread-Level Partitioning**: Once a subgraph is assigned to a node, we use optimized resource allocation which is a clustering based method called Scaling up of Clusters of Actors on Processing Element (SCAPE).
* **Simulation**: PREESM simulates both intra-node and inter-node execution, verified by SimGrid, to check if any previously ignored communication affects the final allocation and performance.
* **Node-Level Readjustment**: Accordingly, the method readjusts the construction of the subgraphs.

[![](/assets/tutos/simsdp/principe.png)](/assets/tutos/simsdp/principe.png)


### Use-case: Radio-Frequency Interference (RFI) filter
The process involves filtering Radio Frequency Interference (RFI) from an acquisition file obtained by a radio telescope [\[2\]](#references). The file is in the ".dada" format (DADA stand for Distributed Acquisition and Data Analysis) and is comprised of two parts: the header, which contains information about the radio telescope, and the data part. The data part consists of complex numbers. The first step of the process is to separate the real and imaginary components of the data in order to apply filters to both. Two filters are computed simultaneously, and one of them is applied to the data. 
- The first filter is the Standard Deviation (STD) filter. See [Wiki STD deviation](https://en.wikipedia.org/wiki/Standard_deviation)

$$ {\displaystyle MAD = median (|X_{i}-{\tilde {X}}|)} $$

- The second filter is the Median Average Deviation (MAD) filter. See [Wiki MAD deviation](https://en.wikipedia.org/wiki/Median_absolute_deviation).

$$ \sigma =\sqrt{\frac{1}{N}\sum\limits_{i=1}^N (x_i - \mu)}$$

Both filters aim to find a threshold and remove data points above this threshold. Finally, the filtered real and imaginary parts are combined by taking their conjugates to reconstruct the complex numbers. These reconstructed complex numbers are then used to generate a new ".dada" file.

## Project Setup

1. Download rfi.pi, and source, and include, and timing [here](https://github.com/Ophelie-Renaud/RFI).
1. Launch Preesm and create a simSDP project using “File > create simSDP Project…”
1. Configure your architecture right click on your project "Preesm >generate custom SimSDP architecture" it generate a csv file store in the **Archi** folder (you can update it as you want)

[![](/assets/tutos/simsdp/popup.png)](/assets/tutos/simsdp/popup.png)


You can start with an homogeneous configuration as follow:

| node name | core ID | Core frequency |intranode rate | internode rate |
| -------- | -------- | -------- |-------- | -------- |
| Paravance     | 0     | 2000.0     |472.0|9.42|
| Paravance     | 1     | 2000.0     |472.0|9.42|
| Paravance     | 2     | 2000.0     |472.0|9.42|
| Paranoia     | 3     | 2000.0     |477.6|9.42|
| Paranoia     | 4     | 2000.0     |477.6|9.42|
| Paranoia     | 5     | 2000.0     |477.6|9.42|
| Abacus     | 6     | 2000.0     |1351.68|9.42|
| Abacus     | 7     | 2000.0     |1351.68|9.42|
| Abacus     | 8     | 2000.0     |1351.68|9.42|

4. Add the downloaded files
     - In the folder **Algo**, add *rfi.pi*
     - In the folder **Code/source** add *source*
     - In the folder **Code/include** add *include*


You can set the hypervisor to control process iterations:

5. Open the workflow “/Workflows/hypervisor.workflow” and select the hypervisor task
6. Select "Properties>Task Variables" and customize fields

| Name | Value | Comment |
| -------- | -------- | -------- |
| Deviation Target     | 1     | *It represent the worload Standard Deviation between node [0;1], 0 mean well balanced distribution. The iteration will stop when this float value is achieved.*   |
| Latency Target     | 1     | *It represent the final latency simulated.The iteration will stop when this Long value is achieved.*     |
| Round     | 5     | *It represent a fixed number of iteration. The iteration will stop when this Integer value is achieved.*     |
| SimGrid     | false     | *True: SimGrid simulate, False : PREESM simulate*     |

## Run SimSDP Project

7. Right-click on the workflow “/Workflows/hypervisor.workflow” and select “Preesm > Run Workflow”;
1. In the scenario selection wizard, select “/Scenarios/rfi.scenario

During its execution, the workflow will log information into the Console of Preesm. When running a workflow, you should always check this console for warnings and errors (or any other useful information). 

[![](/assets/tutos/simsdp/files.png)](/assets/tutos/simsdp/files.png)

Additionnaly, the workflow execution generates intermediary dataflow graphs that can be found in the **/Algo/generated/** directory. The C code generated by the workflow is contained in the **/Code/generated/** directory.

9. Check that you get the tree structure shown below:

[![](/assets/tutos/simsdp/starterpack.png)](/assets/tutos/simsdp/starterpack.png)

## Simulation analysis
At the end of the iterative process (expect about 1 minute per round), the simulator displays a 3-pages popup.
- **Internode Analysis** (page 1): The upper graph show the the internode partitioning based on the computation of the workload standard deviation. Given that the workload is represented as a percentage, in a well-balanced workload, the deviation tends to approach zero.

[![](/assets/tutos/simsdp/simSDPresult.png)](/assets/tutos/simsdp/simSDPresult.png)

## SimSDP code generation
The code generated by SimSDP consists of 3 levels of files. Each machine has its own file. The main file identifies the machine on which it is running via MPI, and calls the functions associated with the node. The sub file contains the thread launch information associated with each node core. The last level is the thread function, which contains the function calls of the placed and scheduled actors.

[![](/assets/tutos/simsdp/code.png)](/assets/tutos/simsdp/code.png)


## Run the generated C Project
Prerequisite:

10. Install dependencies:

```
# install GNU
sudo apt update
sudo apt install -y autotools-dev autoconf libtool make 
sudo apt install g++ gfortran
sudo apt install libfftw3-dev pgplot5 libcfitsio-dev
sudo apt-get update
sudo apt-get install gnuplot
# install make
sudo make install
# install pthread
# install mpi
sudo apt install openmpi-bin
```

11. Copy files on multinode target: 

```
scp -r ~/.../Code user@IP:Target
```

12. Compile remote file:

```
ssh target
oarsub -I -l nodes=3	
cd Code/
cmake .
make
```

13. Run your generated code:

```
mpirun -hostfile $OAR_NODEFILE Code
```

[![](/assets/tutos/simsdp/RFIresult.png)](/assets/tutos/simsdp/RFIresult.png)

## References
[[1] O. Renaud, A. Gougeon, K. Desnos, C. Phillips, J. Tuthill, M. Quinson, J.-F. Nezan, SimSDP: Dataflow Application Distribution on Heterogeneous Multi-Node Multi-Core Architectures, IETR, CSIRO, IRISA, 202_](https://fr.overleaf.com/read/fstmtvmvmgxk) .

[[2] Kaushal D. Buch*, Shruti Bhatporia, Yashwant Gupta, Swapnil Nalawade, Aditya Chowdhury, Kishor Naik, Kshitij Aggarwal and B. Ajithkumar, Towards Real-Time Impulsive RFI Mitigation for Radio Telescopes, GMRT 2016](http://gmrt.ncra.tifr.res.in/subsys/digital/DigitalBackend/target_files/Publications/JAI_Towards_Real-Time_Impulsive_RFI_Mitigation_for_Radio_Telescopes.pdf) .


