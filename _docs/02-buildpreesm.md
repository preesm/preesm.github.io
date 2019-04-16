---
title: "Building Preesm"
permalink: /docs/buildpreesm/
toc: true
---

Prerequisite: 
*  [Install and Configure git](/docs/gitsetup)
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
    ![](/assets/docs/02-buildingpreesm/dev_feature.png)
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
        ![](/assets/docs/02-buildingpreesm/unselect-releng.png)
*   Click on finish. The import of Preesm projects will start automatically. The progress is visible in the bottom right of the Eclipse window:
[![](/assets/docs/02-buildingpreesm/import-progress1.png)](/assets/docs/02-buildingpreesm/import-progress1.png)
*   **Note: this step can take few minutes as the Maven local repository has to be initialized and filled with project dependencies.** More details about the progress can be observed by click on the right most button of the state bar:
[![](/assets/docs/02-buildingpreesm/import-progress2.png)](/assets/docs/02-buildingpreesm/import-progress2.png)

If the import step is interrupted, this might corrupt the local Maven repository. See [issue #129](https://github.com/preesm/preesm/issues/129) for more details and fix procedure.

## Compilation of Preesm

**Change perspective**  
For a better user experience in eclipse, we strongly advice that you use the "Plug-In Development" perspective of eclipse. To use this perspective, select "Plug-In Development" in Windows > Open Perspective > Others...

**Compilation**  
If there still are errors after applying the previous operations, try again to clean, refresh and build the projects.

## Execution of Preesm

In the Java or 'Plug-in Development' perspective, right-click on the 'org.ietr.preesm.ui' plug-in, the click on Debug As > Eclipse Application. A new Eclipse session is launched that behaves like the one obtained by Preesm users from the update site. The difference is that you can debug and modify code in the first Eclipse and test at the same time in the second Eclipse. You can see the tutorials to learn how to use the second Eclipse, import Preesm projects and test.

![](/assets/docs/02-buildingpreesm/debug_ui.png)

## Building the Eclipse Product

This section describes how to build and export the Eclipse Product of PREESM, i.e. the standalone executable of PREESM (as folder structure or Zip archive).

### From Eclipse

The product can be built from Eclipse. It requires [extra steps for multi-platform builds](https://wiki.eclipse.org/A_Brief_Overview_of_Building_at_Eclipse#Multi-platform_builds).
1.  In the Eclipse workspace, locate the **org.preesm.product** project, and open the **.product** file within;
![](/assets/docs/02-buildingpreesm/product-file.png)
2.  From the product editor, in the **Overview** tab, the **Exporting** section has an **Eclipse Product export wizard**;
[![](/assets/docs/02-buildingpreesm/product-overview.png)](/assets/docs/02-buildingpreesm/product-overview.png)
3.  The wizard has proper default values, except for the target folder. In the Destination section, choose any folder with write permission and click on finish. Alternatively, select a target archive file to automatically build a Zipped product;
[![](/assets/docs/02-buildingpreesm/product-export.png)](/assets/docs/02-buildingpreesm/product-export.png)
4.  The progress information is visible in the bottom right of the Eclipse window. Clicking on the right most button in the state bar will open a more detailed progress view;
[![](/assets/docs/02-buildingpreesm/product-progress.png)](/assets/docs/02-buildingpreesm/product-progress.png)
5.  When the export wizard is done, the product is runnable directly from the target folder. It can also be used with [Preesm CLI](https://github.com/preesm/preesm-cli) or for RCPTT tests (see [RCPTT Tests from Eclipse](#rcptt-tests-from-eclipse))

### Using Maven

If you have maven installed on your computer, run `mvn clean package` from the root of the git repository. When the build process has terminated successfully, the resulting products and archives will be located under **releng/org.preesm.product/target/products/**.

## Non-Regression Tests

Automatic testing is key to prevent regression. Preesm tests are run automatically at every push on the [Travis CI platform](https://travis-ci.org/preesm/preesm/branches). To run the tests, Travis simply calls the Maven goals **clean verify**. That triggers the full build and test of all Preesm plugins (see below for details).

Builds can fail for many reasons, as compilation error, broken coding policies, or test failures. Compilation errors and broken coding policies should be enforced by the Eclipse IDE. Tests however have to be triggered manually on your workstation. Indeed, having the Travis CI build fail on the tests means that one or several functionalities have been broken.

The tests are split in several test plug-ins:
*  **org.preesm.tests.algorithm**: unit testing of algorithms and codegen;
*  **org.preesm.tests.framework**: unit testing for commons and workflow;
*  **org.preesm.tests.integration**: integration test running workflows on Preesm projects;
*  **org.preesm.tests.model**: unit testing of PiSDF, SLAM and Scenario models;
*  **org.preesm.tests.ui**: unit testing for UI (workflow, PiSDF, Slam, Scenario);
*  **org.preesm.tests.ui.rcptt**: integration tests for UI using [RCPTT](https://www.eclipse.org/rcptt/), actually reproducing user behavior.


### Run Tests with Maven

Running the tests from Maven is done by calling `mvn clean verify` from the root of the git repository. Because the RCPTT tests actually run a graphical Eclipse, it is advised to run the tests within a virtual framebuffer, such as [Xvfb](https://www.x.org/releases/X11R7.7/doc/man/man1/Xvfb.1.xhtml). Running the following command from the root of the git repository will automatically instantiate an Xvfb and set the DISPLAY variable accordingly before running the tests:
```
./releng/build_and_test.sh --fast
```
This is the command that is run on Travis CI.

During the execution of the tests, if some failure occurs, the output will look like the following:
```
[INFO] PREESM :: Parent ................................... SUCCESS [ 20.540 s]
[INFO] PREESM :: Plugins :: Parent ........................ SUCCESS [  0.349 s]
[INFO] PREESM :: Plugins :: Commons ....................... SUCCESS [  7.951 s]
[INFO] PREESM :: Plugins :: Workflow ...................... SUCCESS [  1.797 s]
[INFO] PREESM :: Plugins :: PiSDF Model ................... SUCCESS [ 47.890 s]
[INFO] PREESM :: Plugins :: Slam Model .................... SUCCESS [ 17.142 s]
[INFO] PREESM :: Plugins :: Scenario Model ................ SUCCESS [  2.466 s]
[INFO] PREESM :: Plugins :: Algorithms .................... SUCCESS [ 21.024 s]
[INFO] PREESM :: Plugins :: Command Line Interface ........ SUCCESS [  1.398 s]
[INFO] PREESM :: Plugins :: Code Generation ............... SUCCESS [ 23.573 s]
[INFO] PREESM :: Plugins :: Xtend Code Generation ......... SUCCESS [ 12.189 s]
[INFO] fi.abo.preesm.dataparallel ......................... SUCCESS [  8.959 s]
[INFO] PREESM :: Plugins :: UI ............................ SUCCESS [  1.788 s]
[INFO] PREESM :: Plugins :: PiSDF UI ...................... SUCCESS [  4.025 s]
[INFO] PREESM :: Plugins :: Scenario UI ................... SUCCESS [  2.033 s]
[INFO] PREESM :: Plugins :: IBSDF UI ...................... SUCCESS [  1.291 s]
[INFO] PREESM :: Plugins :: Slam UI ....................... SUCCESS [  0.937 s]
[INFO] PREESM :: Tests :: Parent .......................... SUCCESS [  0.178 s]
[INFO] PREESM :: Tests :: Framework ....................... SUCCESS [ 11.508 s]
[INFO] PREESM :: Tests :: Models .......................... SUCCESS [ 14.291 s]
[INFO] PREESM :: Tests :: Algorithms ...................... SUCCESS [ 17.343 s]
[INFO] PREESM :: Tests :: Integration ..................... FAILURE [07:36 min]
[INFO] PREESM :: Tests :: UI .............................. SKIPPED
[INFO] fi.abo.preesm.dataparallel.test .................... SKIPPED
[INFO] PREESM :: Releng :: Parent ......................... SKIPPED
[INFO] PREESM :: Releng :: Feature ........................ SKIPPED
[INFO] PREESM :: Releng :: Product ........................ SKIPPED
[INFO] PREESM :: Releng :: Update Site .................... SKIPPED
```
Such output is generated after every Travis build (i.e. [this log](https://api.travis-ci.org/v3/job/518319450/log.txt) for a successful build), in the last lines of the log. 

From this summary, it is straightforward to locate which plug-in caused the failure. More details can be found when scrolling up the log, for instance:
```
Failures: 
  PiMM2SRDAGTest.testSrdag:114 Workflow [StaticPiMM2SRDAGCodegen.workflow] with scenario [4corePrediction.scenario] caused failure
  PiMM2SRDAGTest.testSrdag:114 Workflow [StaticPiMM2SRDAGCodegen.workflow] with scenario [4coreTraining.scenario] caused failure
  PiMM2SRDAGTest.testSrdag:114 Workflow [StaticPiMMCodegen.workflow] with scenario [1corePrediction.scenario] caused failure
  PiMM2SRDAGTest.testSrdag:114 Workflow [StaticPiMMCodegen.workflow] with scenario [1coreTraining.scenario] caused failure
  PiMM2SRDAGTest.testSrdag:114 Workflow [StaticPiMMCodegen.workflow] with scenario [4corePrediction.scenario] caused failure
  PiMM2SRDAGTest.testSrdag:114 Workflow [StaticPiMMCodegen.workflow] with scenario [4coreTraining.scenario] caused failure
  PiMM2SRDAGTest.testSrdag:114 Workflow [PiMM2SRDAGCodegenMemoryScriptsMixedMerged.workflow] with scenario [8coresC6678.scenario] caused failure
  PiMM2SRDAGTest.testSrdag:114 Workflow [StaticPiMM2SRDAGCodegen.workflow] with scenario [8coresC6678.scenario] caused failure
  PiMM2SRDAGTest.testSrdag:114 Workflow [XMLCodegen.workflow] with scenario [4core.scenario] caused failure
  PiMM2SRDAGTest.testSrdag:114 Workflow [XMLCodegen.workflow] with scenario [YUV4core.scenario] caused failure
  PiMM2SRDAGTest.testSrdag:114 Workflow [XMLCodegen.workflow] with scenario [lowMaxDisparity.scenario] caused failure
  PiMMFlattenerTest.testPiMMFlattener:93 Workflow [StaticPiMMFlattenerCodegen.workflow] with scenario [1corePrediction.scenario] caused failure
  PiMMFlattenerTest.testPiMMFlattener:93 Workflow [StaticPiMMFlattenerCodegen.workflow] with scenario [1coreTraining.scenario] caused failure
  PiMMFlattenerTest.testPiMMFlattener:93 Workflow [StaticPiMMFlattenerCodegen.workflow] with scenario [1core.scenario] caused failure
  TutorialsTest.test:106 Workflow [CodegenMemoryDeGreef.workflow] with scenario [4core.scenario] caused failure
  TutorialsTest.test:106 Workflow [CodegenMemoryDeGreef.workflow] with scenario [YUV4core.scenario] caused failure
  TutorialsTest.test:106 Workflow [CodegenMemoryDeGreef.workflow] with scenario [lowMaxDisparity.scenario] caused failure
  TutorialsTest.test:106 Workflow [CodegenMemoryScriptsMixedMerged.workflow] with scenario [1core.scenario] caused failure
  TutorialsTest.test:106 Workflow [CodegenMemoryScriptsMixedMerged.workflow] with scenario [4core.scenario] caused failure
  TutorialsTest.test:106 Workflow [CodegenMemoryScriptsMixedMerged.workflow] with scenario [8coresC6678.scenario] caused failure
  TutorialsTest.test:106 Workflow [CodegenMemoryScriptsMixedMerged.workflow] with scenario [YUV4core.scenario] caused failure
  TutorialsTest.test:106 Workflow [CodegenMemoryScriptsMixedMerged.workflow] with scenario [lowMaxDisparity.scenario] caused failure
  TutorialsTest.test:106 Workflow [StaticPiMMCodegen.workflow] with scenario [1core.scenario] caused failure
  TutorialsTest.test:106 Workflow [StaticPiMMCodegen.workflow] with scenario [4core.scenario] caused failure
  TutorialsTest.test:106 Workflow [StaticPiMMCodegen.workflow] with scenario [8coresC6678.scenario] caused failure
  TutorialsTest.test:106 Workflow [StaticPiMMCodegen.workflow] with scenario [YUV4core.scenario] caused failure
  TutorialsTest.test:106 Workflow [StaticPiMMCodegen.workflow] with scenario [lowMaxDisparity.scenario] caused failure
  TutorialsTest.test:106 Workflow [StaticPiMMCodegenMemoryScripts.workflow] with scenario [1core.scenario] caused failure
  TutorialsTest.test:106 Workflow [StaticPiMMCodegenMemoryScripts.workflow] with scenario [4core.scenario] caused failure
  TutorialsTest.test:106 Workflow [StaticPiMMCodegenMemoryScripts.workflow] with scenario [8coresC6678.scenario] caused failure
  TutorialsTest.test:106 Workflow [StaticPiMMCodegenMemoryScripts.workflow] with scenario [YUV4core.scenario] caused failure
  TutorialsTest.test:106 Workflow [StaticPiMMCodegenMemoryScripts.workflow] with scenario [lowMaxDisparity.scenario] caused failure

Tests run: 137, Failures: 32, Errors: 0, Skipped: 0
```
This log excerpt shows which tests actually failed. 

In order to fix the code, we strongly advise to run the tests from Eclipse once the issue has been located. 

### Run Tests from Eclipse

Using Eclipse, tests are run plug-in wise only. That means you have to manually trigger the tests for all the test plug-ins independently. This is done by right clicking on a test project and then on "Run As / JUnit Plug-in Test":

[![](/assets/docs/02-buildingpreesm/tests-runas.png)](/assets/docs/02-buildingpreesm/tests-runas.png)

This has to be done on all plug-ins, except for the RCPTT test plug-in, where "Run As / Test Cases" (see below). Once execution of all tests terminates, a new view displays all test results:

[![](/assets/docs/02-buildingpreesm/tests-eclipseresults.png)](/assets/docs/02-buildingpreesm/tests-eclipseresults.png)

This view will only tell which Preesm Workflow successfully terminated or not. To inspect the actual cause of the failure, the log has to be inspected. Test failures can be re-run independently in order to check the log more accurately (otherwise the log can be several thousands of lines). This is done by scrolling in the Console log. Among all the outputs, the workflow logs always start with **Starting workflow execution**:
```
[...]
13:21:03 NOTICE: Starting workflow execution: /org.ietr.preesm.stereo/Workflows/CodegenMemoryScriptsMixedMerged.workflow.
13:21:03 NOTICE: Workflow Step: 'StaticPiMM2SDF' (id 'org.ietr.preesm.experiment.pimm2sdf.StaticPiMM2SDFTask'): [StaticPiMM2SDFTask] Transforming PiGraph to SDFGraphs
13:21:04 NOTICE: PiMM2SDF transformation: 00:00:00.064s.
[...]
13:21:07 NOTICE: Workflow Step: 'Code Generation' (id 'org.ietr.preesm.codegen.xtend.task.CodegenTask'): [CodegenTask] Generate xtend code
13:21:07 SEVERE: Unexpected Exception: java.util.ConcurrentModificationException:null
 Contact Preesm developers if you cannot solve the problem.
java.util.ConcurrentModificationException
	at org.eclipse.emf.common.util.AbstractEList$EIterator.checkModCount(AbstractEList.java:751)
	at org.eclipse.emf.common.util.AbstractEList$EIterator.doNext(AbstractEList.java:699)
	at org.eclipse.emf.common.util.AbstractEList$EIterator.next(AbstractEList.java:685)
	at org.preesm.codegen.xtend.task.CodegenModelGenerator.generateActorFiring(CodegenModelGenerator.java:613)
[...]
```
This log shows that the Code Generation task threw an exception while attempting to modify a List while iterating on it.

#### RCPTT Tests from Eclipse

**Note: at the time of writing, RCPTT tests are disabled because of an incompatibility with latest Eclipse (see [https://github.com/xored/rcptt/issues/16](https://github.com/xored/rcptt/issues/16)).**

This procedure is specific to running tests from Eclipse. Maven uses the product built during the full process to run the tests.

1.  Export the product using [procedure described above](#from-eclipse);
2.  Select plugin "org.preesm.tests.ui.rcptt", right click on it then "Run As / Test Cases" 
3.  The following window ask to choose an Application Under Test (AUT). Add a new one with the product exported from step 1; **this step fails because of bug**
4.  **TODO**

## Coding Policies

The Preesm code base respects some coding policy. If you intend to develop within Preesm, we strongly advise you to follow these policies to prevent the continuous integration server to reject your contributions. These policies are simple:

1.  Follow the [checkstyle](http://checkstyle.sourceforge.net/) format provided in the [coding policy project](https://github.com/preesm/preesm-maven/blob/master/preesm-coding-policy/checkstyle/VAADER_checkstyle.xml);
2.  Make sure all the tests run without failure (run 'mvn clean verify' in the root folder of the git repository.

To help enforcing the checkstyle format, the "on-the-fly" checkstyle analyzer using the Eclipse plug-in (automatically installed with the meta feature and configured through M2Eclipse connectors) will provide feedback and the Eclipse cleanup formatter will enforce most of the rules (triggered on save action).

### Build Status Notifications

To receive the build status notifications, you have to sign in on travis-ci.org with your github account and make sure your local git config mail address matches one of the github verified mail address:

1.  Check your local git config mail address, and change it to a valid one if it is not already the case: ```git config --global --edit```
2.  Sign in on GitHub, got to Settings / Emails, and check that the mail address is present. If not, add it to the list and make sure that it is verified before going further.
3.  Got to [https://travis-ci.org/](https://travis-ci.org/) and Sign In with your GitHub account. This will allow Travis-CI to read the permissions on the repositories to make sure you have push access and also check that your mail address is verified.

Preesm team repositories set the notification policies to the Travis default : [https://docs.travis-ci.com/user/notifications/](https://docs.travis-ci.com/user/notifications/)

## Troubleshooting

*   **Many errors show up in source code. Why?**
    *   **(1)** The usual reason is that upstream API changed. Try "Help / Check for updates". It may be required to [clean the Eclipse cache](https://stackoverflow.com/questions/9250041/how-to-clear-cache-of-eclipse-indigo) after such update.
    *   **(2)** Another reason would be the Maven configuration files that are not up to date. To update them, select all the Preesm related projects in the workspace, and press Alt+F5 then press OK.
    *   **(3)** Also, the build state of the Eclipse workspace can be corrupted after an update. To fix that, restart Eclipse then clean and rebuild your workspace.
    *   **(4)** Finally, some updates can remove or introduce new Preesm plug-ins that you would have to add in your workspace. The safest way is to (1) remove the plugins from the workspace (select all then delete, without removing from file system); (2) clean the git repository (```git clean -xdf```); (3) then reimport the plugins (File / Import ... > Maven / Existing Maven Projects > select the root folder of the git repository. Eclipse will automatically detect plugins). If the procedures did not solve the errors, please contact us.
*   **Check for Updates is failing:** We moved the update site because of instability of the previous server. If you still have the old URL, you may have the following error: ```HTTP Server 'Service Unavailable': [...] error response code 503.``` It is best to use the new update site:
    *   Go to Window > Preferences then Install/Update > Available Software Sites;
    *   Look for the PREESM site and Edit its URL;
    *   Paste the new URL: [http://preesm.insa-rennes.fr/repo/complete/](http://preesm.insa-rennes.fr/repo/complete/);
    *   Click on Apply and Close then try to check for updates again. If this does not solve the issue, please contact us.
*   **Missing artifact com.sun:tools:jar:1.8.0 is missing:** This happens because Eclipse is running using a JRE instead of a JDK. Some Eclipse plugins needs Java packages that are not bundled with the JRE, but only with the JDK. If you installed the JDK, note that by default the JDK distribution for Windows also installs a JRE. Also, the installer adds a section in the PATH variable, that points to ```C:\ProgramData\Oracle\Java\javapath```, which is an alias to the JRE binaries. Please refer to [this page](https://douglascayers.com/2015/05/30/how-to-set-custom-java-path-after-installing-jdk-8/) for fixing the issue. Basically, it tells to prepend the PATH variable with the path to the JDK, while not removing the ```...\javapath``` section.
