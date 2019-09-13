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
Updated the 21.11.2018 by [A. Morvan](mailto:antoine.morvan@insa-rennes.fr)

## Disclaimers

1.  When building PREESM, you have to **run a second Eclipse Application** (the guest) within the Eclipse where the source code is imported (the host). This is explained below in  the "Execution of Preesm" section;
2.  When using unreleased source code (that is any other commit different from the last tag), there could be some **inconsistencies** between released zip files used in tutorials and the tool;


## Eclipse installation

### Install Java  

Preesm requires the Eclipse environment, which runs on the Java platform. If you do not have Java yet, you will need to install it, either in your package manager, or on this website: [http://www.java.com](http://www.java.com). Please install Java 8 Java Runtime Environment (JRE) or Java Development Kit (JDK). **Under Windows, you may need to uninstall Java 6 or 7.**

### Eclipse

Eclipse is an extensible platform programmed in Java that allows developers to write their own IDE (Integrated Development Environment).

You can download Eclipse here: [https://www.eclipse.org/downloads/packages/](https://www.eclipse.org/downloads/packages/). Please download the **"Eclipse IDE for Java Developers"** package to develop in Preesm. Install or extract Eclipse in a folder where you have write access.

Run Eclipse, you need to choose a workspace, i.e. a directory where the metadata of and/or your projects will be stored.

### Ensure use of Java 8 or higher in Eclipse

In Eclipse, go to: Window > Preference > Java > Compiler and choose Compiler compliance level=1.8. You may check the linked JRE in Window > Preference > Java > Installed JREs.

Under Windows and Linux, you can check that Java version 1.8 is installed by typing "java -version" in a command terminal. 

Under Linux, ensure Java 8 is the default version. In the eclipse.ini file, you can set:  
-Dosgi.requiredJavaVersion=1.8

Under Linux command line, you can set java 1.8 as default with following commands (example with 64-bit openjdk):  
```bash
sudo update-java-alternatives -l  
sudo update-java-alternatives -s java-1.8.0-openjdk-amd64
```

### Install Development Requirements

In Eclipse:

1.  Go to "Help > Install New Software...";
2.  In "work with:", add a new location named "Preesm" with location "http://preesm.insa-rennes.fr/repo/"
3.  Select the "Developer Resources > **PREESM Developer Requirements (Meta Feature)**"
    (this Eclipse feature contains all requirements for developing Preesm):
    ![](/assets/docs/03-buildingpreesm/dev_feature.png)
4.  Click Next twice, agree on the terms of the license, and click on Finish to install the features. This step will take a few minutes.
5.  During the installation process, a security warning for unsigned content will appear. Click on OK to continue the installation.
6.  Restart eclipse to complete the installation.

## Import Preesm projects

You need to get the code from the git repository and import the projects into Eclipse. Our git repository is hosted by [Github](https://github.com/) (see the [Preesm team page on Github](https://github.com/preesm/)).

Clone the [Preesm](https://github.com/preesm/preesm) repository (git clone \<url\>, in the folder where you want to place your local repository):

*   [https://github.com/preesm/preesm.git](https://github.com/preesm/preesm.git) (https)
*   or ```git@github.com:preesm/preesm.git``` (SSH)

**Switch to the develop** branch (git checkout develop) to get the latest developer version of our source.

Connect your local repository obtained through git clone to your Eclipse workspace

*   Window > Perspective > Open Perspective > Other ... then select Git
*   Add an existing local repository to this view (GIT button with a green plus symbol), then browse to the local repositories
*   Check the box and click on finish.

The PREESM plugins are plain Maven projects. In order to import them in Eclipse, we rely on the M2Eclipse project (automatically installed with the dev feature). This allow to centralize all the configuration in the POM files. The .project, .classpath and .settings/* files are automatically generated (see [maintainer doc](https://github.com/preesm/preesm/blob/develop/releng/README.md)).

*   In the Java perspective, click on File > Import... then Maven > Existing Maven Projects;
*   In the next window, browse to the git repository location (the Preesm root folder);
*   The project import wizard should automatically find all the projects;
    *   Note: releng/* projects are for Release Engineering, and can be omitted.:
        ![](/assets/docs/03-buildingpreesm/unselect-releng.png)
*   Click on finish. The import of Preesm projects will start automatically. The progress is visible in the bottom right of the Eclipse window:
[![](/assets/docs/03-buildingpreesm/import-progress1.png)](/assets/docs/03-buildingpreesm/import-progress1.png)
*   **Note: this step can take few minutes as the Maven local repository has to be initialized and filled with project dependencies.** More details about the progress can be observed by click on the right most button of the state bar:
[![](/assets/docs/03-buildingpreesm/import-progress2.png)](/assets/docs/03-buildingpreesm/import-progress2.png)

If the import step is interrupted, this might corrupt the local Maven repository. See [issue #129](https://github.com/preesm/preesm/issues/129) for more details and fix procedure.

## Compilation of Preesm

**Change perspective**  
For a better user experience in eclipse, we strongly advice that you use the "Plug-In Development" perspective of eclipse. To use this perspective, select "Plug-In Development" in Windows > Open Perspective > Others...

**Compilation**  
Compilation is triggered automatically by Eclipse. If there still are errors after applying the previous operations, try again to clean, refresh and build the projects.

**Note: because of an issue in the Eclipse PDE framework, some error can remain. See [this issue](https://github.com/preesm/preesm/issues/116) for a workaround.**

## Execution of Preesm

In the Java or 'Plug-in Development' perspective, right-click on the 'org.ietr.preesm.ui' plug-in, the click on Debug As > Eclipse Application. A new Eclipse session is launched that behaves like the one obtained by Preesm users from the update site. The difference is that you can debug and modify code in the first Eclipse and test at the same time in the second Eclipse. You can see the tutorials to learn how to use the second Eclipse, import Preesm projects and test.

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
*   **Building with Maven 3.6.1 fails**: Maven version 3.6.1 breaks P2 repository dependency resolution. See See [this issue](https://github.com/preesm/preesm/issues/235) for a workaround.


