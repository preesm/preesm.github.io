---
title: "Tutorial Introduction"
permalink: /tutos/intro/
toc: true
---

The following topics are covered in this tutorial:

*   Installation of PREESM, Graphiti graph editor and DFTools plugins for Eclipse
*   Presentation of PREESM layout (Workflow, Scenario, Architecture graph, Algorithm graph...)
*   Simulation and scheduling of a multicore code
*   Generation of a multicore code

Prerequisites: None

###### Tutorial created the 04.24.2012 by [M. Pelcat](mailto:mpelcat@insa-rennes.fr)

Install Eclipse
---------------

Go to the page [Eclipse Preparation](index.php?id=eclipse-preparation) to prepare an Eclipse version to host Preesm.

Install Preesm plug-ins
-----------------------

To install Preesm, you need to add the following site to the Eclipse Software Update Manager: [http://preesm.sourceforge.net/eclipse/update-site](http://preesm.sourceforge.net/eclipse/update-site). To do that, follow these seps:

1.  Go to: "Help > Install New Software..."
2.  Click Add... to add an update site set its name (e.g. Preesm) and its url to [http://preesm.sourceforge.net/eclipse/update-site](http://preesm.sourceforge.net/eclipse/update-site)
3.  Select the different features: Graphiti, DFTools and Preesm,
4.  click Next, and Finish.
5.  At some point Eclipse will ask you if you really want to install an unverified feature, just accept and restart Eclipse.

Open Tutorial Project
---------------------

A Tutorial Project can be found [here](data/uploads/tutorial_zips/org.ietr.preesm.tutorials.tutorial1.zip). The project needs to be imported in your workspace. To do so, follow these steps:

1.  Go to "File > Import..."
2.  In the wizard, select  "General > Existing Projects into Workspace".
3.  Click "Next"
4.  Selec "Select archive file", then "Browse..."
5.  Select the zip archive you downloaded
6.  Click "Next".

Once these steps are completed, the unzipped project files can be accessed either from the "Package Explorer" view of Eclipse, or directly in the "Workspace" directory of eclipse (using your favorite browser).

Downloads
---------

Pthread (POSIX Threads) library is used to obtain a portable code with threads. For more information on POSIX threads, see [https://computing.llnl.gov/tutorials/pthreads](https://computing.llnl.gov/tutorials/pthreads).

CMake is used to generate C compilation projects for Microsoft Visual, Codeblocks, Eclipse CDT... For more information on CMake, go to [http://www.cmake.org](http://www.cmake.org/).

Download the following files and follow the instruction given in "Code/lib/ReadMe.txt" to make sure that the required libraries are in the right place:

*   **CMake**: (OS and IDE specific) For Ubuntu use "sudo apt-get install cmake cmake-curses-gui". [\[link\]](https://cmake.org/download/)
*   **Pthread development library**: [\[link\]](http://preesm.insa-rennes.fr/website/data/uploads/other/pthreads-w32-2-8-0-release.exe) (For Win only, pthreads are natively supported under Linux)

Understand the Inputs and Run the Workflow
------------------------------------------

The Introduction project contains folders with the name "Algo" for the algorithm model, "Archi" for the architecture model, "Scenarios" for the parameters and constraints of rapid prototyping, "Code" for the generated code, "Algo/generated" for the exported intermediate information, and "Workflows" for the different available rapid prototyping workflows.

The tested algorithm is a dummy graph called "TestCom".

![](/assets/tutos/intro/editeur_testcom_pisdf.png)

"size" is a parameter influencing the productions and consumption rates of tokens on the data FIFO queues.

A workflow is an executable graph that applies transformations to models. The workflow file available in Tutorial Introduction calls simulation and code generation for a dummy application running on 2 cores communicating via shared memory. To run the workflow, follow these steps:

1.  Right click on the Codegen.workflow file in the "Workflows" directory
2.  Click "Run As > Preesm Workflow"
3.  Choose the TestComPC.scenario file in the "Scenarios" directory
4.  A Gantt chart is displayed and you can find the code that has been generated in "/Code/generated".
5.  The code can be compiled and run using MS Visual Studio, Codeblocks, GCC, Eclipse CDT... from "/Code". Scripts are included for generating gnu Makefile, MS Visual Studio 2013 project or Codeblocks project.

The execution Gantt chart on two cores looks as the following figure.

![](/assets/tutos/intro/editeur_gantt_pisdf.png)

The obtained code simply displays OK if the data was correctly transmitted between data generators and data getters. You can look at the code in "/Code/generated" to understand the shape of the generated code.

In order to study a more realistic application, try to parallelize the Sobel filter in the [next tutorial](index.php?id=parallelize-an-application-on-a-multicore-cpu).
