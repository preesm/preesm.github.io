---
title: "Building Preesm"
permalink: /docs/buildpreesm/
toc: true
---

Prerequisite: 
*  [Install git](/docs/gitsetup)
*  Building Preesm requires an active Internet connection, used to fetch Maven and/or Eclipse dependencies. This Internet connection is not required for running Preesm once built.

Note: if you need to work with Graphiti source code, please read [these instructions](/docs/buildfromgraphiti/) first.

###### Tutorial created the 04.24.2012 by [M. Pelcat](mailto:mpelcat@insa-rennes.fr)  
Updated the 27.02.2023 by [H. Miomandre](mailto:hugo.miomandre@insa-rennes.fr)

## Disclaimers

1.  When building PREESM, you have to **run a second Eclipse Application** (the guest) within the Eclipse where the source code is imported (the host). This is explained below in  the "[Execution of Preesm](#execution-of-preesm)" section;
2.  When using unreleased source code (that is any other commit different from the last tag), there could be some **inconsistencies** between released zip files used in tutorials and the tool;


## Eclipse installation

### Install Java  

Preesm requires the Eclipse environment, which runs on the Java platform. If you do not have Java yet, the Eclipse IDE will provide it. Otherwise, make sure the to have the version 17 Java*.
*Java 18 and 19 can be used to run and develop in Preesm, but may not be usable to run Maven commands for unit tests and product generation.

### Eclipse

Eclipse is an extensible platform programmed in Java that allows developers to write their own IDE (Integrated Development Environment).

You can download the Eclipse installer here: [https://www.eclipse.org/downloads/](https://www.eclipse.org/downloads/). Please choose the **"Eclipse IDE for RCP and RAP Developers"** package to develop in Preesm. Install Eclipse in a folder where you have write access.

Run Eclipse, you need to choose a workspace, i.e. a directory where the metadata of and/or your projects will be stored.

### Install Development Requirements

In Eclipse:

1.  Go to "Help > Install New Software...";
2.  In "Work with:", choose the first Eclipse address: ```2022-12 - https://download.eclipse.org/releases/2022-12/``` from the drop-down menu (at the time of writing).
3.  Look for Modeling > **EMF - Eclipse Modeling Framework Xcore SDK** (search for "xcore") and Testing > **RCPTT IDE** (search for "rcptt").
Click Next, accept the terms of the license agreement when asked.
No need to restart Eclispe when prompted.
4.  Go to "Help > Eclipse Marketplace...";
5.  From the marketplace, install **Eclipse Xtend** (search for "xtend") and **Eclipse Xtext** (search for "xtext").
6.  Restart Eclipse to complete the installation.

## Import Preesm projects

You need to get the code from the git repository and import the projects into Eclipse. Our git repository is hosted by [Github](https://github.com/) (see the [Preesm team page on Github](https://github.com/preesm/)).

Clone the [Preesm](https://github.com/preesm/preesm) repository (git clone \<url\>, in the folder where you want to place your local repository):

*   ```git clone https://github.com/preesm/preesm.git``` (https)
*   ```git clone git@github.com:preesm/preesm.git``` (SSH)

**Switch to the develop** branch (git checkout develop) to get the latest developer version of our source.

Add the Preesm sources into Eclipse:

1.  Go to "File > Open Projects from File System..."
2.  In "Import source:" provide the path to the cloned git repository.
3.  Ensure the option "Search for nested projects" is checked.
4.  Select everything apart from:
    * ```preesm```
    * ```preesm/plugins```
    * Everything in ```preesm/releng``` but ```preesm/releng/org.preesm.target-platform``` and ```preesm/releng/org.preesm.product```
    * ```preesm/tests```
    * Everything in ```preesm/tests/org.preesm.integration.tests```
        * Only the main ```preesm/tests/org.preesm.integration.tests``` is kept.
    * **There should 24 projects selected.**
5.  Click on **Finish**.

## Compilation of Preesm

Close "Welcome" tab.

Eclipse should start the compilation right-away while the dependancies of Preesm as not yet been imported, resulting in compilation errors. While Preesm is being compiled, open ```org.preesm.target-platform/org.preesm.target-platform.target``` and click on **Set as Active Target Platform** in the top right corner to instruct Eclipse to fetch the dependencies required by Preesm.

## Execution of Preesm

Right-click on the 'org.preesm.ui' plug-in, the click on Debug As > Eclipse Application. A new Eclipse session is launched that behaves like the one obtained by Preesm users from the update site. The difference is that you can debug and modify code in the first Eclipse and test at the same time in the second Eclipse. You can see the tutorials to learn how to use the second Eclipse, import Preesm projects and test.

![](/assets/docs/03-buildingpreesm/debug_ui.png)

## Building the Eclipse Product

This section describes how to build and export the Eclipse Product of PREESM, i.e. the standalone executable of PREESM (as folder structure or Zip archive).

**Note: Make sure your Eclipse either uses a different copy of the code or it is shutdown. Indeed the automatic builds of Eclipse can corrupt Maven build.**

After installing Maven, run `mvn clean package -DskipTests` from the root of the git repository. When the build process has terminated successfully, the resulting products and archives will be located under ```releng/org.preesm.product/target/products/```.

## Troubleshooting

*   **I followed all steps but there are compilation errors**
    *   **(1)** The build state of the Eclipse workspace can be corrupted after an update. To fix that, restart Eclipse then clean and rebuild your workspace.
    *   **(2)** Try reloading the target platform in ```org.preesm.target-platform/org.preesm.target-platform.target``` by clicking on **Relaod Target Platform**.
*   **Missing artifact com.sun:tools:jar:1.8.0 is missing:** This happens because Eclipse is running using a JRE instead of a JDK. Some Eclipse plugins needs Java packages that are not bundled with the JRE, but only with the JDK. If you installed the JDK, note that by default the JDK distribution for Windows also installs a JRE. Also, the installer adds a section in the PATH variable, that points to ```C:\ProgramData\Oracle\Java\javapath```, which is an alias to the JRE binaries. Please refer to [this page](https://douglascayers.com/2015/05/30/how-to-set-custom-java-path-after-installing-jdk-8/) for fixing the issue. Basically, it tells to prepend the PATH variable with the path to the JDK, while not removing the ```...\javapath``` section.
*   **The Preesm runtime throws a popup error message on every interaction:** Ensure that the version 17 of Java is used if you happen to have multiple installe at the same time.

## Known Issues

*   **I still have issues setting up the Preesm developement environment**: There are no known issues yet. If you find one that can be reliably reproduced, please create [an issue](https://github.com/preesm/preesm/issues/) with the required informations.


