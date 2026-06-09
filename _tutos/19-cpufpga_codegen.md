---
title: "Heterogeneous C/HLS code generation for CPU-FPGA"
permalink: /tutos/cpufpga_codegen/
toc: true
---

The following topics are covered in this tutorial:

*   Implementation of a [Wavelet](https://en.wikipedia.org/wiki/Wavelet_transform) filter on a CPU-FPGA platform
*   Code generation for Xilinx FPGA using Preesm

Prerequisite: 
* [Preesm installation](/get/)
* [Vitis >2024.1](https://www.xilinx.com/products/design-tools/vitis/vitis-hls.html), required for timing estimation and hardware synthesis, tested on Ubuntu 20.04, 22.04 and 24.04
* Previous Preesm tutorials will help understanding the notions of workflow, scenario, etc.

###### Tutorial created the 29.05.2026 by [J. Morin](mailto:jamorin@insa-rennes.fr)


## Requirements
### For the timing extraction step
wget https://old-releases.ubuntu.com/ubuntu/pool/main/libf/libffi/libffi6_3.2.1-8_amd64.deb
sudo apt install ./libffi6_3.2.1-8_amd64.deb
sudo LD_LIBRARY_PATH=/tools/Xilinx/Vitis/202X.X/tps/lnx64/python-3.X.X/lib/ /tools/Xilinx/Vitis/202X.X/tps/lnx64/python-3.X.X/bin/python3.X -m pip install xlrd openpyxl

## Project Setup

* Download the [Heterogeneous Wavelet project on github in preesm-apps/tutorials](https://github.com/preesm/preesm-apps).
* Launch Preesm and open the project using "File > open Projects from File Systems...".
* Select the project and import it.

Notice that the algorithm is exactly the same as for the FPGA wavelet tutorial : the differences will be in the architecture and scenario mappings.

## Architecture specification
We will describe a system with 2 CPU cores and an FPGA die, all linked through a shared memory. Note the KRIA has 4 cores available, but for simplicity we will only use two.

Create a new architecture file, named for example "2core_fpga". Insert a parallelComNode ("shared_mem"), 2 CPUs ("core0/1") and an FPGA ("KRIA260"). Set the hardware id of the cores to 0 and 1, and their definition to ARM. For the FPGA, set : board to "xilinx.com:kr260_som:part0:1.1", definition to "FPGA", frequency to "50", hardwareid to "2" and part to "xck26-sfvc784-2LV-c".
Link the CPUs and the FPGA to the shared memory with undirectedDataLinks.

## Application development

the application is pre-made in "wavelet_heterogeneous_tuto.pi". Generate the .diagram file. Notice that actors in the DWT and IDWT sub-graphs have HLS-style refinements set, while the threshold actors have C-style refinements set.


## Scenario
We will now map the actors to the available processing elements. Open the "tuto_heterogeneous" scenario. Set the algorithm and the scenario to the ones we are using. In the Simulation tab, set core0 as the main operator, shared_mem as the main ComNode, and check all operators to execute broadcast/explode/implode.
In the constraints tab, map the DWT and IDWT sub-graphs to the FPGA, and the other actors to the CPU cores.
In the timing tab, we will insert an approximation of the FPGA actors' timings. For all FPGA actors, set as execution interval and initation interval the maximum input/output rates of each actor. For example, DWT's "Conv_row_height" actor has a rate of 396 in its "input" port, 5 in its "filter" port, and 396 in its "output" port. The maximum is 396, the number we use for this actos's timings. It corresponds to the minimum number of cycles needed to produce and consume all tokens. (If you're lazy, you can also just set them all to 1000000).
In the simulation tab, set all the processing elements as able to execute broadcast/implode/explode.
In the codegen tab, set the codegen directory to "/Wavelet_Filter/Code/generated_tuto".

## Workflow and code generation
Run the workflow "HeterogeneousWorkflow" on the scenario.

## Application synthesis

Application synthesis is performed in 3 steps, 1. by generating a first, timing-inaccurate C++ implementation for HLS using Preesm, 2. by synthesizing the generating design to extract the timing information, and 3. By re-generating the code and performing hardware implementation. 

### Workflow and code generation
Run the workflow "HeterogeneousWorkflow" on the scenario.

### Timings extraction
We used inaccurate timings for the FPGA actors in our workflow. Now that the HLS code has been generated, we can synthesize the generated cluster code with vitis to know the actual cycle counts. 
In the generated_tuto folder, open a terminal with the xilinx envirenment set up and run `vitis -s extract_synth_results.py timings`. This will synthesize the actors and store their associated timings in the "timings.csv" and "timings.xlsx" files.
Now, go back to the scenario. In the Timings tab, section "Timings file path", click "browse" and select the "timings.xlsx" file.
In case this does not work, you can also manually write the actors' timings to the scenario.
Once this is done, re-run the workflow. The scheduling will be done with accurate timings now.

### Hardware synthesis for Kria260

First, setup the KRIA260 board by following the tutorial fit to your linux version : https://xilinx.github.io/kria-apps-docs/kr260/build/html/docs/linux_boot.html
*make sure to provide the common image folder as path to the script when prompted* so that the `syroots/` file is extracted directly inside it.

Then, follow these steps to generate the final hardware implementation:

- Open a terminal with the Xilinx toolchain included in the PATH;
- Navigate to the Codegen folder
- run `python gui.py gui`
- in the gui, tick "build vivado platform" and leave the other options as default.
- click "run all".

## Deployment on Kria260

Follow these steps to deply on Kria260:
- run the packaging script : `bash package_app.sh generated_tuto`. This will store all the necessary files in a single PACKAGE folder.
- before booting the board, blug the ethernet and uart cables to the computer. Start a uart communication with the board : `picocom -b 115200 /dev/ttyUSB1`.
- plug the power cord into the board to boot it. The initial login and password are "ubuntu".
- connect to the board through ssh
- copy the PACKAGE folder to the board to `/lib/firmware/xilinx`.
- unload any potential application : `sudo xmutil unloadapp`.
- make sure the package is recognised : `sudo xmutil listapps` must list the PACKAGE folder.
- inside the PACKAGE folder, set the app as executable : `sudo chmod +x app_component`
- load the bitstream to the FPGA : `sudo xmutil loadapp PACKAGE`
- run the application : `./package_app`