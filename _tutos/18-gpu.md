---
title: "CPU-GPU Design Space Exploration"
permalink: /tutos/gpu/
toc: true
---
In this tutorial, you will learn how to use the Scaling-up of Cluster of Actors on Processing Element (SCAPE) method to deploy a dataflow application on a heterogeneous CPU-GPU architecture.

The following topics are covered in this tutorial:

* Implementation of a Distributed FX (DiFX) correlator with PREESM
* Heterogeneous CPU-GPU Partitioning
* Heterogeneous CPU-GPU Code Generation
* Execution on laptop or High-Performance Computing (HPC) systems

Prerequisite:

* Install PREESM see [getting PREESM](https://preesm.github.io/get/)
* This tutorial is design for Unix system

###### Tutorial created the 8.5.2024 by [O. Renaud](mailto:orenaud@insa-rennes.fr) & [E. Michel](mailto:emichel@insa-rennes.fr)


## Introduction
### Principle 
SCAPE is a clustering method integrated into PREESM, designed to partitioning GPU-on-GPU compatible computations and CPU-on-CPU compatible computations, evaluating offloading gains and adjusting granularity [\[1\]](#references). The method happens upstream of the standard resource allocation process. It takes as input the GPU-oriented System-Level Architecture Model (GSLA) Model of Architecture (MoA) and the dataflow MoC and partially replicates the standard steps avoiding the need for a complete flattening such as:
- **Extraction:**
  - **GPU-friendly pattern identification**: This task identifies data parallel patterns such as URC and SRV as suitable dataflow models for GPUs as introduced in Section II-E.
  - **Subgraph generation**: The identified actors are then isolated into a subgraph where rates are adjusted to contain all data parallelism.
- **Scheduling:** The chosen scheduling strategy for the cluster is the APGAN method.
- **Timing:** This step estimates the execution time of the subgraph running on a GPU, considering memory transfers between GPUs during execution.
- **Mapping:** This task estimates parallelism gain and transfer loss based on GSLA information. If GPU offloading is beneficial, the process proceeds; otherwise, the original SCAPE generates a cluster of actors mapped on CPU.
- **Translation:** The subgraph is translated into a CUDA file and sent to the rest of the resource allocation process after transformation, simplification, and optimization.

### GSLA MoA
The System-Level Architecture Model (S-LAM) provides a structured framework for system architectures, including subsets like the Linear System-Level Architecture Model (LSLA) and the GPU-oriented System-Level Architecture Model (GSLA). GSLA allows internal parallelism modeling in Processing Elements (PEs), while LSLA requires modeling all PEs for reliability, leading to differences in cost definition. LSLA for GPUs requires modeling each kernel element as a processing element, making mapping and scheduling complex. We choose GSLA for this method because it handles complexities better while preserving data parallelism.

Below is a GSLA representation compose of one CPU core , one GPU kernel, one main communication node and one GPU communication node:
[![](/assets/tutos/gpu/gsla.png)](/assets/tutos/gpu/gsla.png)

### Use-case: DiFX correlator
The principle of the core of DiFX [\[2\]\[3\]](#references) is the following :

1. **Data Alignment:** Given the vast distances separating telescopes, signals they record may encounter delays due to varied travel paths. The correlator aligns these signals in time to ensure synchronicity with the observation moment.
2. **Correlation:** Once aligned, the correlator compares signals from each telescope, performing mathematical correlation by multiplying and summing received signals over a specific time interval.
3. **Image Formation:** Correlated data provide insights into the brightness and structure of observed astronomical objects.

Below is a simplified dataflow representation:
[![](/assets/tutos/gpu/difx.png)](/assets/tutos/gpu/difx.png)

**Data Acquisition:** Telescope data is retrieved from disk in binary format, devoid of headers. Each binary file (xx.bin) is packed into a 2D array, with one file per antenna. Subsequently, delays between telescopes are computed based on a polynomial from the configuration file (xx.conf).

**Floating Point Conversion:** Raw integer-encoded data is converted to floating-point numbers. This stage also creates complex numbers with zero imaginary values and divides data into independent channels, stored in a 3D array per polarization per antenna. An "offset" correction is applied to account for geometric signal reception time effects.

**Fringe Rotation:** Each sample undergoes 'fringe rotation' to adjust for telescopes' relative speeds (Doppler shift). This involves applying a time-varying phase shift to each sample.

**FFT of Samples:** "N" time samples are transformed into frequency-domain data, making it easier to extract information later.

**Cross-correlation (X):** Individual frequency channels of each telescope are multiplied and accumulated to form "visibilities" for each FFT block. This process generates unique baselines combinations and integrates sub-integration data over milliseconds, averaged for approximately one second to form the final visibility integration.

**Accumulation:** Cross-correlation values for each FFT block are added to previous iterations in the "visibilities" table, resulting in final visibility products. Phase and amplitude data for each frequency channel and baseline are stored in a vis.out file.

(more detail on running different DiFX implementation can be found on the readme of the DiFX dataflow model, notably FxCorr and Gcorr)
## Project Setup
- Download DiFX project from [Preesm-apps](#references)
- Launch Preesm and open the project: Click on “File / Import …”, then “General / Existing Projects into Workspace” then locate and import the “org.ietr.preesm.difx” project
- Create an GPU-accelerated architecture. Based on a classic CPU architecture: "right click on architecture > generate a custom x86 architecture > select 1 core". Select the GPU vertice from the palette and drop it on your design. Select a parallelComNode and drop it to your design. Connect each element with undirectedDataLink. Take the figure below as an example.

    [![](/assets/tutos/gpu/1core_1gpu.png)](/assets/tutos/gpu/1core_1gpu.png)
- Custom you GPU node: select the node and ajust the parameter to fit your target.

|Property|Value|Comment|
|--|--|--|
|dedicatedMemSpeed|20000|Speed of the dedicated memory (in MB/s), typically higher than unified memory.|
|definition|defaultGPU||
|hardwareId|1|Unique identifier for the GPU hardware.|
|id|GPU|Identifier name for the GPU in the system configuration.|
|memoryToUse|dedicated|Specify memory allocation type: "dedicated" for GPU-exclusive memory or "unified" for shared CPU-GPU memory.|
|memSize|4000|Size of the GPU's memory (in MB), determining the amount of data it can handle.|
unifiedMemSpeed|500|Speed of the unified memory (in MB/s), typically slower but shared between CPU and GPU.|

- Generate your scenario: right click on your project “Preesm >generate all scenarios.
- Custom the Codegen.workflow to generate the appropriated CPU-GPU code:
  - Open the Codegen.workflow
  - Add a new Task vertex to your workflow and name it *SCAPE*. To do so, simply click on “Task” in the Palette on the right of the editor then click anywhere in the editor.
  - Select the new task vertex. In the “Basic” tab of its “Properties”, set the value of the field “plugin identifier” to “scape.task.identifier”. 
  - In the “Task Variables” tab of its “Properties”, fill the varaible like this:
  
  | Parameter | Value | Comment |
  | -------- | -------- | -------- |
  | Level number     | 0     | Corresponds to the hierarchical level to coarsely cluster, works with all SCAPE mode 0, 1 and 2.     |
    | SCAPE mode     | 0     | 0: match data parallelism to the target on specified level, 1: match data and pipeline parallelism to the target on specified level, 2: match data and pipeline parallelism to the target on all admissible level.  |
    | Stack size     | 1000000     | Cluster-internal buffers are allocated statically up to this value, then dynamically.  |

(more detail, see [Workflow Tasks Reference ](/docs/workflowtasksref/#scape-transformation))

  - connect the new task to the “scenario” task vertex and to the “PiMM2SrDAG” task vertex as shown in the figure below.
[here insert a figure]
- Right-click on the workflow “/Workflows/Codegen.workflow” and select “Preesm > Run Workflow”;

The workflow execution generates intermediary dataflow graphs that can be found in the “/Algo/generated/” directory. The C code generated by the workflow is contained in the “/Code/generated/” directory.

## Execution of the DiFX correlator dataflow model 
- Download the IPP ([Intel Integrated Performance Primitives for Linux ,2021.11.0,19 MB,Online,Mar. 27, 2024](https://www.intel.com/content/www/us/en/developer/articles/tool/oneapi-standalone-components.html#ipp)):
```
chmod +x l_ipp_oneapi_p_2021.11.0.532.sh
./l_ipp_oneapi_p_2021.11.0.532.sh
```

### Execution on laptop equiped with GPU
- Check if your laptop is equiped with Nvidia GPU : `lspci | grep -i nvidia`
  <div style="background-color:#300A24; padding:10px; border-radius: 5px;font-size: 12px">
    <strong><em style="color:white;">Prompt:</em></strong><br>
    <em style="color:gray;"> 
        3D controller: NVIDIA Corporation GA107M [GeForce RTX 2050] (rev a1) <br>
  </em>
  </div>

- Install NVIDIA CUDA Drivers & CUDA Toolkit for your system, see NVIDIA's [tutorial](https://docs.nvidia.com/cuda/cuda-installation-guide-linux/
)
- Check CUDA compiler & CUDA install with `nvcc -V`
  <div style="background-color:#300A24; padding:10px; border-radius: 5px;font-size: 12px">
    <strong><em style="color:white;">Prompt:</em></strong><br>
    <em style="color:gray;"> 
        → nvcc: NVIDIA (R) Cuda compiler driver <br>
        → Copyright (c) 2005-2024 NVIDIA Corporation <br>
        → Built on Thu_Mar_28_02:18:24_PDT_2024 <br>
        → Cuda compilation tools, release 12.4, V12.4.131 <br>
        → Build cuda_12.4.r12.4/compiler.34097967_0 <br>
  </em>
  </div>

A MakeFile is stored on the /Code folder 
- correct the IPP path to your IPP path
- Compile and run
```
cmake .
make
./difx
```

### Execution on Grid5000
- Open an account [grid5000 account](https://www.grid5000.fr/w/Grid5000:Get_an_account).

*The example here are for rennes.*
- Choose the node you need based on its characteristics (number of GPUs, memory ...) presented in [Rennes:Hardware - grid5000](https://www.grid5000.fr/w/Rennes:Hardware).
Check the availability of your `chosen` node on [Rennes:node](https://intranet.grid5000.fr/oar/Rennes/drawgantt-svg/) or [Rennes:node(production)](https://intranet.grid5000.fr/oar/Rennes/drawgantt-svg-prod/) 
(more status [here](https://www.grid5000.fr/w/Status))
```
# ssh connect
ssh orenaud@access.grid5000.fr
ssh rennes
#connect 1 abacus node (they host NVIDIA GPU)
oarsub -q production -p abacus1 -I
#copy the folder
scp -r ~/path/Code orenaud@access.grid5000.fr:rennes
```

A MakeFile is stored on the /Code folder 

- correct the IPP path to your IPP path (download IPP on the machine)
- Compile and run
```
cmake .
make
./difx
```

DiFX execution generates a file containing visibility information *vis.out*

### Display results
You can visualize your data with the notebook available [here](https://colab.research.google.com/drive/1wEkoTKZVg0fBC_8KcRdCKvL0_GrnKbjb#scrollTo=JAQfFu9OwXy2).
Import your vis.out file and run the code.
[![](/assets/tutos/gpu/notebook.png)](/assets/tutos/gpu/notebook.png)

> Display of the 4 average caracheristics by baselines (detailed in the notebook).

<div style="background-color:#e6f7ff; padding:10px; border-radius: 5px;font-size: 12px">
    <strong><em style="color:gray;">Nota Bene:</em></strong><br>
    <em style="color:gray;">To run the original version of FxCorr and Gcorr follow the readme of the DiFX dataflow model stored on preesm-apps.</em>
</div>

## References
[[1] E. Michel, O. Renaud, A. Deller, K. Desnos, C. Phillips, J.-F. Nezan, Automated Deployment of Radio Astronomy Pipeline on CPU-GPU Processing Systems: DiFX as a Case Study, IETR, Swinburne, CSIRO, 2024](https://hal.science/hal-04744986). 

[[2] A.T. Deller, S.J. Tingay, M. Bailes, & C. West, DiFX: A software correlator for very long baseline interferometry using multi-processor computing environments, Swinburne, 2007](https://arxiv.org/abs/astro-ph/0702141).

[[3] A.T. Deller, W.F. Brisken, C.J. Phillips, J. Morgan, W. Alef, R. Cappallo, E.Middelberg, J. Romney, H. Rottmann, S.J. Tingay, R. Wayth
DiFX2: A more flexible, efficient, robust and powerful software correlator,Swinburne, 2011](https://arxiv.org/abs/1101.0885).



[![gcorr](https://img.shields.io/github/stars/XhrisPhillips/gcorr?style=social&label=XhrisPhillips/gcorr)](https://github.com/XhrisPhillips/gcorr)


[![preesm-apps](https://img.shields.io/github/stars/preesm/preesm-apps?style=social&label=preesm/preesm-apps&logo=data:image/svg%2bxml;base64,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)](https://github.com/preesm/preesm-apps)



## Acknowledgement
*This project has received funding from the European Union’s Horizon 2020 research and innovation program under the Marie Skłodowska-Curie grant agreement No 873120.*
