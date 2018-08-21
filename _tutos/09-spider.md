---
title: "Reconfigurable Application with SPIDER - [BETA]"
permalink: /tutos/spider/
toc: true
---

**Writing of this tutorial is currently in progress. If you wish to follow this tutorial, please consider yourself as a beta tester (and don't hesitate to send us any improvement suggestions).**

The following topics are covered in this tutorial:

*   Spider's operating principle
*   Retreving a compiled version of the Spider library
*   Modification the [Sobel filter](http://en.wikipedia.org/wiki/Sobel_operator) example to add reconfiguability
*   C Code generation for Spider using Preesm
*   Compiling the Sobel filter with the Spider library

Prerequisite: [Parallelize an Application on a Multicore CPU](http://preesm.insa-rennes.fr/website/index.php?id=parallelize-an-application-on-a-multicore-cpu)

###### Tutorial created the 11.07.2017 by H. Miomandre

1\. Project setup
-----------------

The starting point of this tutorial is the Preesm project obtained as a result of the [Parallelize an Application on a Multicore CPU](index.php?id=parallelize-an-application-on-a-multicore-cpu) tutorial. The project resulting from this tutorial is available [\[here\]](data/uploads/tutorial_zips/tutorial1_result.zip). The external libraries, the YUV sequence and the generated C code are not included in this archive. Explanation on how to setup these external elements, compile and run the project are available [\[here\]](index.php?id=parallelize-an-application-on-a-multicore-cpu).

2\. About reconfigurability
---------------------------

All applications developed and optimized in previous tutorials, where modeled with "static" dataflow graphs. A graph is said to be static when the numbers of data tokens consumed and produced by an actor at each firing are defined with expressions whose integer values can be computed at compile time. This static property enforces the predictability of the dataflow model, and paves the way for powerful optimizations of the applications during the compilation process, like those presented in the memory optimization tutorials.

Although the static property is a strong asset for compile-time optimizations, this property limits the range of application behaviors that can be modeled. For example, a static graph can not be used to model an algorithm where the execution of some actors may be enabled or disabled at runtime, depending on the value of a data token produced by a sensor. A concrete algorithm with such a behavior is a facial expression recognition algorithm with a first set of actors responsible for locating a face in a picture, and a second set of actors responsible for recognising its expression. In this example, if no face has been located in a picture by the first set of actors, it is useless to try to recognise a facial expression in this frame, and the second set of actors should be disabled.

The purpose of this tutorial is to show how a reconfigurable behavior can be modeled with the dataflow graph editor of Preesm, by specifying special actors, called configuration actors, that may set new values for parameters of the graph at runtime.

Details on the semantics and mechanisms of the PiSDF reconfigurable dataflow model of computation implemented in Preesm are available in [\[1\]](#ref).

3\. Spider presentation
-----------------------

#### 3.1. Spider Runtime Objectives

Setting a new value for a parameter of a graph at runtime has a strong impact on how actors of this graph will be executed. For example, increasing the value of a parameter used as a production rate by an actor will have an impact on the amount of memory allocated for the output buffer of this actor. By changing consumption and production rates of actors dynamically, the number of execution of these actors may be modified, thus requiring a new mapping of these executions on the processing elements of the architecture.

In order to support these runtime reconfigurations of the graph parameters, a special process, called a runtime, is needed to manage their impact on the graph and deploy dynamically the application on the targeted architecture. The runtime can be seen as a small operating system acting as an adaptation layer between the reconfigurable dataflow graph, and the resources (processing elements, memory, means of communication, ...) of the targeted architecture.

The runtime responsible for managing reconfigurable dataflow graph modeled in Preesm is called "Spider". Spider stands for Synchronous Parameterized and Interfaced Dataflow Embedded Runtime.

#### 3.2. Structure of Spider

The Spider runtime software architecture is separated into several layers. These layers make the runtime independent from both application and platform.

Layers are separated as followed:

![](/assets/tutos/spider/runtime_layers_full.svg)

*   **Application Layer** : The application layer is application specific, it is composed of actor sources (written in C/C++) and the PiSDF graph that models the application. The PiSDF graph can be either generated with handwritten code or using a code generator embedded in Preesm framework.
*   **Runtime Layer** : The runtime layer is the core of the Spider runtime, it is composed of two parts, the master part called global runtime (**GRT**) and the slave parts called local runtime (**LRT**). The master part is used to schedule the application on the several slave parts that execute the different actors.
*   **Hardware Specific Layer** : The hardware specific layer, also called **Platform layer**, is the part that handle the inter-core communication and synchronization. It is platform specific and designed to fully exploit the platform specificities to reach maximum performance.

#### 3.3. Execution Scheme

The typical execution scheme of the Spider runtime is expressed as followed:

![](/assets/tutos/spider/archi_full.svg)

1.  **Schedule Actors**: The **GRT** schedule the application's actors onto the several cores available on the platform.
2.  **Send Order**: Then it sends orders in the **Jobs Queues**, jobs are a data structure that allows the **LRT** to execute a specific actor. It includes, but is not limited to, which actor function to execute and with which data.
3.  **Fire Actors**: **LRTs** execute the actor codes
4.  **Exchange Dataflow Tokens**: **LRTs** post and retrieve data tokens from **Data queues**.
5.  **Set Resolved Parameters**: When a configuration actor is executed, parameter values may be resolved, then **LRTs** send this result to the **GRT**.
6.  **Execution Traces**: Execution trace are sent into the **Timings Queues** to give feedback on the current execution, it allows the Spider runtime to print a Gantt chart of past executions.

The Spider runtime is currently compatible with x86 architectures (Windows, Linux), Keystone II architectures from Texas Instruments, and the MPPA256 many-core processor from Kalray. More information on Spider is available in [\[2\]](#ref).

4\. Spider library
------------------

The Spider runtime is provided as a library when building a dynamically reconfigurable application. Pre-built versions of the Spider library are available in the following archive \[[link](/assets/tutos/spider/spider_lib.zip)\], with both a debug and a release versions. Currently provided library binaries are compatible with:

*   **GCC**: libSpider.so.
*   **Visual Studio**: Spider.dll and Spider.lib.
*   **CodeBlocks**: libSpider.dll and libSpider.dll.a.

To use one of these versions, simply copy-paste the content of the Debug or Release folder into "/Code/lib/spider/". Instead, if you want to get the latest version of Spider, go to the Building Spider tutorial \[[link](http://preesm.insa-rennes.fr/website/index.php?id=building-spider)\] to compile it yourself.

5\. Modification of the Sobel algorithm
---------------------------------------

We need to modify the current Sobel algorithm to add a configuration actor and make the graph reconfigurable.

#### 5.1 Prepare the project

It is important to note that the project resulting from this tutorial will no longer be compatible with the workflows used in tutorials 1 to 7. Hence, we strongly advise you to back up your current sobel project.

Remove the following files from the "/Code/" directory:

*   In "include/":
    *   communication.h
    *   dump.h
    *   fifo.h
    *   memory.h
    *   x86.

*   In "src/":
    *   communication.c
    *   dump.c
    *   fifo.c
    *   memory.c
    *   main.c

Empty the "/Code/generated/" directory and change the extension of every .c file in "/Code/src/" to .cpp.

#### 5.2 Update algorithm model

Download the following archive \[[link](/assets/tutos/spider/tutorial_spider_files.zip)\] which contains:

*   the code of a configuration actor that you're about to add in Preesm,
*   an additional file that might be needed if you compile your program with Visual Studio.

The next steps will add a configuration actor in our Sobel algorithm graph to make it a reconfigurable.

1.  Copy-paste the "nbSliceSetter.cpp" and "nbSliceSetter.h" files from the archive into "/Code/src/" and "/Code/include/", respectively.
2.  In Preesm, refresh the package explorer (either by pressing \[F5\], or with a right-click > "Refresh")
3.  In the graph editor of Preesm, create a new actor and name it "**nbSliceSetter**"
4.  Right-click on the new actor and select "Add new configuration output port", name it "**nbSlice**".
5.  Select "Dependency" in the left-hand palette of the graph editor, and connect the "nbSlice" configuration output port to the nbSlice parameter.
6.  Using the procedure presented in previous tutorials, set "nbSliceSetter.h" as the refinement for the configuration actor.

The resulting graph should look like this:

![](/assets/tutos/spider/top_display.bmp)

The white dots that appear on the **nbSliceSetter** actor, and on the **nbSlice** and **height** parameters show that these graph elements constitute a reconfigurable part of the graph. It should be noted that a configuration actor changing the value of a parameter can not receive data tokens from another actor of its graph. As detailed in [\[1\]](#ref), hierarchy levels must be used to allow a configuration actor to consume data tokens.

At this step, you must update the mapping constraints specified in the scenario to allow the execution of the newly created configuration actor on at least 1 core.

#### 5.3 Create a Spider workflow

We now need to tell Preesm to use the Spider codegen instead of the C code generation used in previous tutorials.

1.  Right-click in the "Package Explorer" and select "New > Other". In the opened wizard, select "Preesm > Preesm Workflow" to create a new workflow in the "/Workflows/" directory and name it "SpiderCodegen.workflow".
2.  Add a "Scenario" source bloc from the Palette with:
    *   id : "scenario"
    *   plugin identifier : "org.ietr.preesm.scenario.task"
3.  Add a "Task" bloc with:
    *   id : "Spider Codegen"
    *   plugin identifier : "org.ietr.preesm.pimm.algorithm.spider.codegen.SpiderCodegenTask"
4.  Link them with two "Data transfer" connections:
    *   "scenario"
    *   "PiMM".

![](/assets/tutos/spider/spidercodegen-workflow.png)

Once it's done, you can start the codegen by running the workflow with the 4 cores scenario.

Some actors like Read_YUV need to run an initialisation function in order to work properly. Currently, Spider does not handle those initialisation functions. In such cases, Preesm will display a warning message in the console. Take note of the concerned actors, as we will need to manually add the corresponding function calls later.

6\. Sobel filter with Spider
----------------------------

#### 6.1 Update the CMake

We need to change the CMake behavior to have it look for and link the Spider library.

- (1) Copy "FindSpider.cmake" from the provided archive into "/Code/lib/cmake_modules/"
- (2) Open "CMakeLists.txt" and insert the following text **AFTER** the pthread section:

```cmake
# *******************************************
# ************ Spider LIBRARY ***************
# *******************************************
 
# find the spider folder in the libspider directory.
file(GLOB SPIDERDIR "${LIBS_DIR}/spider")
set(ENV{SPIDERDIR} ${SPIDERDIR})
 
Find_Package (Spider REQUIRED )
 
if(NOT SPIDER_FOUND)
  MESSAGE(FATAL_ERROR "SPIDER not found !")
endif()
 
if (WIN32)
    file(GLOB
        SPIDER_DLL
        ${SPIDERDIR}/lib/*.dll
    )
     
    MESSAGE("Copy SPIDER DLLs into ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}")
    if(NOT ${CMAKE_GENERATOR} MATCHES "Visual Studio.*")
        file(COPY ${SPIDER_DLL} DESTINATION ${CMAKE_RUNTIME_OUTPUT_DIRECTORY})
    else()
        file(COPY ${SPIDER_DLL} DESTINATION ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}/Debug)
        file(COPY ${SPIDER_DLL} DESTINATION ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}/Release)
    endif()
endif()
```

- (3) At the end of the CMakeLists.txt file, add **${SPIDER\_INCLUDE\_DIR}** as an argument of **include_directories****()**
- (4) In the **file()** command, change arguments from "*.c" to "*.cpp" to tell CMake to look for c++ source files instead of c source files.
- (5) In the **file()** command, add "**./generated/*.h**" as an argument.
- (6) Add **${SPIDER_LIBRARY}** as an argument of **target\_link\_libraries()**.

#### 6.2 Set up Spider

As Spider is a library, it needs to be initialised and called from the main function. For that purpose, we are providing a "main.cpp" file. Copy the "main.cpp" provided in the archive to "/Code/src/". It is currently needed to edit it manually, though it might be generated by the Spider codegen in the future. For now, you need to replace every occurence of **<topgraph\_name\_in_preesm>** by the name given to the top level graph in Preesm. In this example, the top level graph should be "top_display" (you need to match the upper case in one place).

We saw earlier that the Spider codegen in Preesm is not able to handle initialisation/finalisation function (yet). You need to check concerned actors in the Preesm console and manually insert their initialisation/finalisation function in main.cpp (check commentary in the code to know where to insert them) and include their respective header files.

When it's done, simply run the updated CMake script to set up the project and compile the program.

**Note**: On Windows, the version of PThread provided with the Sobel project is the version 2.8.0. Spider uses functions that were implemented on version 2.10.0. For your convenience, we are providing this library already compiled \[[link](/assets/tutos/spider/pthread-2.10.0.zip)\].

**Note**: CodeBlocks users, make sure you do not use the version of MinGW provided with CodeBlocks installer (4.9.2) as it is shiped with its own outdated, incompatible implementation of PThread. Please install the latest version of MinGW (5.3.0) from the MinGW website \[[link](https://sourceforge.net/projects/mingw/)\].

**Visual Studio users only**: if the executable refuses to start because of a missing MSVCR90.dll or giving a R6034 error, do the following:

1.  Open Windows Registry Editor (or use [this .reg file](/assets/tutos/spider/externalmanifest.reg) and go to step 4).
2.  Go to "HKEY\_LOCAL\_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\SideBySide".
3.  Create a DWORD key named "PreferExternalManifest" with value "1".
4.  Copy the "sobel_spider.exe.manifest" from the provided archive in the same directory as the compiled program.
5.  Rename "sobel\_spider.exe.manifest" to "<your\_program_name>.exe.manifest".
6.  Run the program.

The number of core used by Spider to run the application can be changed by modifying the constant "NB_LRT" in "main.cpp". In the normal Preesm codegen, mapping and scheduling is done by Preesm, and therefore fixed at compile time. With Spider, mapping and scheduling is determined at runtime. It means that if an actor is executable on every core (by the "Constraints" tab), Spider is free to send the corresponding job to the first available core.

7\. Monitoring the Graph Execution with Spider
----------------------------------------------

\[Soon...\]

References
----------

**\[1\]** Desnos, Karol; Pelcat, Maxime; Nezan, Jean-François; Bhattacharyya, Shuvra S.; Aridhi, Slaheddine (2013) ["PiMM: Parameterized and Interfaced Dataflow Meta-Model for MPSoCs Runtime Reconfiguration"](http://hal.inria.fr/hal-00877492). [SAMOS XIII](http://www.samos-conference.com/), Samos, Greece.  
**\[2\]** Heulot, Julien; Pelcat, Maxime; Desnos, Karol; Nezan, Jean-François; Aridhi, Slaheddine (2014) ["SPIDER: A Synchronous Parameterized and Interfaced Dataflow-Based RTOS for Multicore DSPs"](http://hal.univ-nantes.fr/docs/01/06/70/52/PDF/ederc2014.pdf). [EDERC 2014](https://www.google.fr/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&uact=8&ved=0CCMQFjAA&url=https%3A%2F%2Fe2e.ti.com%2Fgroup%2Funiversityprogram%2Fc%2Fe%2F205&ei=AfDuVIjiI4Kd7gb5noCYCA&usg=AFQjCNHjGY59v7kumq9XCG0v1dhx41JJIQ&sig2=GSiz_9Qck533B58k78NOqA&bvm=bv.86956481,d.ZGU), Milan, Italy.
