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
Updated the XX.02.2023 by [H. Miomandre](mailto:hugo.miomandre@insa-rennes.fr)

## Disclaimers

1.  When building PREESM, you have to **run a second Eclipse Application** (the guest) within the Eclipse where the source code is imported (the host). This is explained below in  the "[Execution of Preesm](#execution-of-preesm)" section;
2.  When using unreleased source code (that is any other commit different from the last tag), there could be some **inconsistencies** between released zip files used in tutorials and the tool;


## Eclipse installation

### Install Java  

Preesm requires the Eclipse environment, which runs on the Java platform. If you do not have Java yet, the Eclipse IDE will provide it. Otherwise, make sure the to have the version 17 or 18 of Java.
(need to try with 18, can run preesm and unit test with 19 but not mvn)

### Eclipse

Eclipse is an extensible platform programmed in Java that allows developers to write their own IDE (Integrated Development Environment).

You can download the Eclipse installer here: [https://www.eclipse.org/downloads/](https://www.eclipse.org/downloads/). Please choose the **"Eclipse IDE for RCP and RAP Developers"** package to develop in Preesm. Install Eclipse in a folder where you have write access.

Run Eclipse, you need to choose a workspace, i.e. a directory where the metadata of and/or your projects will be stored.

### Install Development Requirements

In Eclipse:

1.  Go to "Help > Install New Software...";
2.  In "Work with:", choose the first Eclipse address: "2022-12 - https://download.eclipse.org/releases/2022-12/" (at the time of writing).
3.  Look for Modeling > **EMF - Eclipse Modeling Framework Xcore SDK** (search for "xcore") and Testing > **RCPTT IDE** (search for "rcptt").  
Accept the terms of the license agreement when asked.  
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
    * **preesm**
    * **preesm/plugins**
    * Everything in **preesm/releng** but **preesm/releng/org.preesm.target-platform** and **preesm/releng/org.preesm.product**
    * Everything in **preesm/tests/org.preesm.integration.tests/**
        * Only the main **preesm/tests/org.preesm.integration.tests** is kept.
    * There should 24 projects selected.
5.  Click on **Finish**.

## Compilation of Preesm

Eclipse should start the compilation right-away while the dependancies of Preesm as not yet been imported, resulting in compilation errors. While Preesm is being compiled, open "org.preesm.target-platform > org.preesm.target-platform.targer" and click on "Set as Active Target Platform" to instruct Eclipse to fetch the dependencies required by Preesm.

## Execution of Preesm

Right-click on the 'org.preesm.ui' plug-in, the click on Debug As > Eclipse Application. A new Eclipse session is launched that behaves like the one obtained by Preesm users from the update site. The difference is that you can debug and modify code in the first Eclipse and test at the same time in the second Eclipse. You can see the tutorials to learn how to use the second Eclipse, import Preesm projects and test.

![](/assets/docs/03-buildingpreesm/debug_ui.png)

## Building the Eclipse Product

This section describes how to build and export the Eclipse Product of PREESM, i.e. the standalone executable of PREESM (as folder structure or Zip archive).

### From Eclipse

The product can be built from Eclipse. It requires [extra steps for multi-platform builds](https://wiki.eclipse.org/A_Brief_Overview_of_Building_at_Eclipse#Multi-platform_builds).
1.  In the Eclipse workspace, locate the **org.preesm.product** project, and open the **.product** file within;
![](/assets/docs/03-buildingpreesm/product-file.png)
2.  From the product editor, in the **Overview** tab, the **Exporting** section has an **Eclipse Product export wizard**;
[![](/assets/docs/03-buildingpreesm/product-overview.png)](/assets/docs/03-buildingpreesm/product-overview.png)
3.  The wizard has proper default values, except for the target folder. In the Destination section, choose any folder with write permission and click on finish. Alternatively, select a target archive file to automatically build a Zipped product;
[![](/assets/docs/03-buildingpreesm/product-export.png)](/assets/docs/03-buildingpreesm/product-export.png)
4.  The progress information is visible in the bottom right of the Eclipse window. Clicking on the right most button in the state bar will open a more detailed progress view;
[![](/assets/docs/03-buildingpreesm/product-progress.png)](/assets/docs/03-buildingpreesm/product-progress.png)
5.  When the export wizard is done, the product is runnable directly from the target folder. It can also be used with [Preesm CLI](https://github.com/preesm/preesm-cli) or for RCPTT tests (see [RCPTT Tests from Eclipse](/docs/devdoc/#rcptt-tests-from-eclipse))

### Using Maven

**Note: when using Maven, make sure your Eclipse either uses a different copy of the code or it is shutdown. Indeed the automatic builds of Eclipse can corrupt Maven build.**

If you have maven installed on your computer, run `mvn clean package -DskipTests` from the root of the git repository. When the build process has terminated successfully, the resulting products and archives will be located under **releng/org.preesm.product/target/products/**.

## Troubleshooting

*   **Many errors show up in source code. Why?**
    *   **(1)** The usual reason is that upstream API changed. Try "Help / Check for updates". It may be required to [clean the Eclipse cache](https://stackoverflow.com/questions/9250041/how-to-clear-cache-of-eclipse-indigo) after such update.
    *   **(2)** Another reason would be the Maven configuration files that are not up to date. To update them, select all the Preesm related projects in the workspace, and press Alt+F5 then press OK. If the keyboard shortcut is overriden by your desktop GUI (i.e. using Ubuntu Unity), this command can be accessed via: (1) select all the Preesm related projects; (2) right click on the selected projects, then click on "Maven > Update Project..."; (3) click on OK in the new window.
    *   **(3)** Also, the build state of the Eclipse workspace can be corrupted after an update. To fix that, restart Eclipse then clean and rebuild your workspace.
    *   **(4)** Finally, some updates can remove or introduce new Preesm plug-ins that you would have to add in your workspace. The safest way is to (1) remove the plugins from the workspace (select all then delete, without removing from file system); (2) clean the git repository (```git clean -xdf```); (3) then reimport the plugins (File / Import ... > Maven / Existing Maven Projects > select the root folder of the git repository. Eclipse will automatically detect plugins). If the procedures did not solve the errors, please contact us.
*   **Check for Updates is failing:** We moved the update site because of instability of the previous server. If you still have the old URL, you may have the following error: ```HTTP Server 'Service Unavailable': [...] error response code 503.``` It is best to use the new update site:
    *   Go to Window > Preferences then Install/Update > Available Software Sites;
    *   Look for the PREESM site and Edit its URL;
    *   Paste the new URL: [http://preesm.insa-rennes.fr/repo/complete/](http://preesm.insa-rennes.fr/repo/complete/);
    *   Click on Apply and Close then try to check for updates again. If this does not solve the issue, please contact us.
*   **Missing artifact com.sun:tools:jar:1.8.0 is missing:** This happens because Eclipse is running using a JRE instead of a JDK. Some Eclipse plugins needs Java packages that are not bundled with the JRE, but only with the JDK. If you installed the JDK, note that by default the JDK distribution for Windows also installs a JRE. Also, the installer adds a section in the PATH variable, that points to ```C:\ProgramData\Oracle\Java\javapath```, which is an alias to the JRE binaries. Please refer to [this page](https://douglascayers.com/2015/05/30/how-to-set-custom-java-path-after-installing-jdk-8/) for fixing the issue. Basically, it tells to prepend the PATH variable with the path to the JDK, while not removing the ```...\javapath``` section.

## Known Issues

*   **I followed all steps but there are compilation errors**: The Eclipse PDE framework has a know issue when resolving project dependencies. See [this issue](https://github.com/preesm/preesm/issues/116) for a workaround.
*   **Building with Maven 3.6.1 fails**: Maven version 3.6.1 breaks P2 repository dependency resolution. See [this issue](https://github.com/preesm/preesm/issues/235) for a workaround.


