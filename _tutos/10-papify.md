---
title: "Papify - [BETA]"
permalink: /tutos/papify/
toc: true
---

**Writing of this tutorial is currently in progress. If you wish to follow this tutorial, please consider yourself a beta tester (and don't hesitate to send us any improvement suggestions).**

Tutorial prerequisites:
*   This tutorial has been developed and tested for Ubuntu distributions

###### Tutorial created the 06.20.2018 by [D. Madroñal](mailto:daniel.madronal@upm.es)

## Project setup

In addition to the default requirements (see [Requirements for Running Tutorial Generated Code](/tutos/intro/#requirements-for-running-tutorial-generated-code)), download the following files:

*   Complete [Sobel Preesm Project](/assets/tutos/parasobel/tutorial1_result.zip)
*   [YUV Sequence (7zip)](/assets/downloads/akiyo_cif.7z) (9 MB)
*   [DejaVu TTF Font](/assets/downloads/DejaVuSans.ttf) (757KB)
*   Papify (see below).

## PAPI and Papify Setup

In order to get access to the Performance Monitor Counters (PMCs) existing in the current processors, it is necessary to install the Performance API (PAPI) library. Additionally, to increase the abstraction layer and to reduce the length of the auto-generated papified code, Papify tool will be used.

### PAPI instalation

1.  Create a new directory to download PAPI repository
2.  Clone it using git -> git clone https://bitbucket.org/icl/papi.git
3.  Go to papi/src ('cd papi/src')
4.  Run './configure'
5.  Run 'make'
6.  Run 'make fulltest' -> This will run the testing part of PAPI (it may take a while)
7.  Run 'sudo make install-all' -> This will install the library as a system library
8.  Run 'papi_avail' -> This command will display the available events on your system and will help you to check whether the library has been correctly installed

### Papify installation

To include Papify in the project, there are two options. The first one is the starting project with all the changes included while the second explians, step by step, all the required changes to work with both PAPI and Papify:


- (1) The starting project with all the changes included is available [\[here\]](/assets/tutos/papify/tutorialpapify.zip).
- (2) Download Papify from the repository:
  - (a) Clone from [https://github.com/dmadronal/Papify.git](https://github.com/dmadronal/Papify.git)
  - or (b) Download it from [https://github.com/dmadronal/Papify/archive/master.zip](https://github.com/dmadronal/Papify/archive/master.zip)
- (3) Copy "src/eventLib.c" to "org.ietr.preesm.sobel/Code/src/" directory
- (4) Copy "include/eventLib.h" to "org.ietr.preesm.sobel/Code/include/" directory
- (5) Open "CMakeLists.txt" and insert the following lines after the pthread section 

```cmake
# *******************************************
# ************ PAPI LIBRARY *****************
# *******************************************

find_library(papi_LIBRARY papi)
```

- (6) Change the target\_link\_libraries to add PAPI linkage 

```cmake
target_link_libraries(sobel ${SDL2_LIBRARY} ${SDL2TTF_LIBRARY} ${CMAKE_THREAD_LIBS_INIT} ${papi_LIBRARY})
```

- (7) In case it is not present, add this line at the end of the CMakeLists.txt ```set(CMAKE_C_FLAGS "-std=gnu99")```
- (8) Finally, in order to find Papify library in compilation time, add these lines in the /include/preesm.h file

```c
#define _PREESM_MONITOR_INIT
#ifdef _PREESM_MONITOR_INIT
#include "eventLib.h"
#endif
```

## Generate the platform supported PAPI events

1.  Open a terminal and go to your project directory
2.  run ```papi_xml_event_info > PAPI_info.xml```

This will generate an xml file with the available PAPI components and events of your computer. If you want to develop code for a different platform, run this command on the target platform and its monitoring options will be gathered in that file.

## Configure the monitoring of the project

1.  Go to the Scenarios folder a duplicate the 4core.scenario by creating a new one called 4corePapify.scenario
2.  Open the new 4corePapify.scenario and go to the Papify tab
3.  Click on the browse button and select the PAPI_info.xml file previously generated
4.  Select Split actor (for example) in the comboBox selector
5.  First, select the PAPI component: perf_event for the CPU
6.  Secondly, select the PAPI events --> For example, select 'Timing' (for monitoring execution time)
7.  Repeat 4.3 to 4.5 steps for the cores you want to monitor. For example, to check all the possible monitoring options:
8.  Sobel --> perf\_event --> PAPI\_L1\_DCM and PAPI\_L1_ICM
9.  Read\_YUV --> perf\_event --> Timing, PAPI\_L1\_DCM and PAPI\_L1\_ICM
10.  display --> No selection (no monitoring)

The resulting aspect of, for example, Read_YUV actor should be the equivalent to the one displayed on the following image.

![](/assets/tutos/papify/scenariopapify2.png)

## Configure the workflow of the project

1.  In the Workflows folder copy the one called Codegen.workflow and create a new one called CodegenPapify.workflow
2.  Create a new Task and call it Papify Engine Task
3.  Select the new task and go to the property tab. After that, write "org.ietr.preesm.codegen.xtend.task.CodegenPapifyEngineTask" as the plugin identifierTask
4.  Delete the arrow connecting Scheduling task DAG output and Code Generation DAG input
5.  Create a new data transfer connecting the scenario task scenario output and a new input of the Papify Engine task and call both scenario
6.  Create a new data transfer connecting the Scheduling task DAG output and a new input of the Papify Engine task and call both DAG
7.  Create a new data transfer connecting the Papify Engine Task DAG output and a new input of the Code Generation task and call both DAG

As a result, the workflow should look like the one displayed in the following image.

![](/assets/tutos/papify/codegenpapifyworkflowtask.png)

## Generate and run the application

1.  Run the workflow selecting as Scenario the one called 4corePapify.scenario
2.  Run the CMakeGCC.sh file --> sh CMakeGCC.sh
3.  If not done yet, please, copy the Papify files:
4.  src/eventLib.c into org.ietr.preesm.sobel/Codet/src
5.  include/eventLib.h into org.ietr.preesm.sobel/Code/include
6.  Go to make/bin --> 'cd bin/make'
7.  Run 'make'
8.  Run the application './Release/sobel' (Don't forget to add the akiyo_cif.yuv and the DejaVuSans.ttf files in the /Code/dat folder)

If any error appears, please, create an issue on github.

## Check the results

Once the application finishes, a new folder called papify-output should have appeared. In this folder, a different file for each actor is created. In each of them, a new line is printed after every actor execution gathering the monitoring information.

In order to analyze these results, we provide below a shell script code to extract the minimum/average/maximum execution time of each actor (or papify_ouput file). This script can be used as a template to analyze other events.

```bash
#!/bin/bash
 
ACTORS=$(ls *.csv | cut -d"." -f1 | colrm 1 14)
 
for ACTOR in ${ACTORS}; do
  [ "$ACTOR" == "profiling" ] && continue
  TIMES_ALL=$(cat papify_output_${ACTOR}.csv | cut -d',' -f 3-4 | tail -n +2)
  MIN=$((2**31))
  MAX=0
  COUNT=0
  SUM=0
  for TIMES in ${TIMES_ALL}; do
    TINIT=$(echo ${TIMES} | cut -d',' -f1)
    TSTOP=$(echo ${TIMES} | cut -d',' -f2)
    LATENCY=$((TSTOP - TINIT))
    SUM=$((SUM + LATENCY))
    MIN=$((MIN>LATENCY?LATENCY:MIN))
    MAX=$((MAX<LATENCY?LATENCY:MAX))
    COUNT=$((COUNT+1))
  done
  MEAN=$((SUM / COUNT))
  echo "Actor '${ACTOR}' timing (min/mean/max) over ${COUNT} measures: $MIN / $MEAN / $MAX"
done
```
