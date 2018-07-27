---
title: "Documentation | Building Preesm"
permalink: /docs/buildpreesm/
toc: true
---


Disclaimers
-----------

1.  When building PREESM, you have to **run a second Eclipse Application** (the guest) within the Eclipse where the source code is improted (the host). This is explained below in  the "Execution of Preesm" section;
2.  When using unreleased source code (that is any other commit different from the last tag), there could be some **inconsistencies** between SourceForge hosted zip files used in tutorials and the tool;

--

The following topics are covered in this tutorial:

*   Installation of Eclipse and all necessary plug-ins
*   Retrieving the source code for Preesm and related projects
*   Buidling and Executing of Preesm
*   Coding Policies
*   Troubleshooting

Prerequisite: [Install and Configure git](http://preesm.insa-rennes.fr/website/index.php?id=install-and-configure-git)

Note: if you need to work with Graphiti and/or DFTools source code, please read [these instructions](/docs/buildfromgraphiti-dftools/) first.

###### Tutorial created the 04.24.2012 by [M. Pelcat](mailto:mpelcat@insa-rennes.fr)  
Updated the 04.19.2017 by [A. Morvan](mailto:antoine.morvan@insa-rennes.fr)

Eclipse installation
--------------------

Follow the instructions of the [Eclipse Preparation](http://preesm.insa-rennes.fr/website/index.php?id=eclipse-preparation) tutorial to obtain an Eclipse installation, in which you will install all necessary tools for Preesm development. Make sure you have the Neon version.

Install XTend, GEF SDK, Graphiti, Maven, DFTools, ...
-----------------------------------------------------

1.  Go to "Help > Install New Software...";
2.  In "work with:", add a new location named "Preesm" with location "http://preesm.sourceforge.net/eclipse/update-site/"
3.  Select the "Developper Resources > PREESM  Developper Requirements (Meta Feature)"  
    (this Eclipse feature contains all requirements for developping Preesm)
4.  Click Next twice, agree on the terms of the license, and click on Finish to install the features. This step will take a few minutes.
5.  During the installation process, a security warning for unsigned content will appear. Click on OK to continue the installation.
6.  Restart eclipse to complete the installation.

Import Preesm projects
----------------------

You need to get the code from the git repository and import the projects into Eclipse. Our git repository is hosted by [Github](https://github.com/) (see the [Preesm team page on Github](https://github.com/orgs/preesm/)).

Clone the following repository (git clone <url>, in the folder where you want to place your local repository):

*   [Preesm](https://github.com/preesm/preesm): https://github.com/preesm/preesm.git (https) or git@github.com:preesm/preesm.git (SSH)

**Switch to the develop** branch (git checkout develop) to get the latest developer version of our source.

Connect your local repository obtained through git clone to your Eclipse workspace

*   Window > Perspective > Open Perspective > Other ... then select Git
*   Add an existing local repository to this view (GIT button with a green plus symbol), then browes to the local repositories
*   Check the box and click on finish.

The PREESM plugins are plain Maven projects. In order to import them in Eclipse, we rely on the M2Eclipse project (automatically installed with the dev feature). This allow to centralize all the configuration in the POM files. The .project, .classpath and .settings/* files are automatically generated (see [maintainer doc](https://github.com/preesm/preesm/blob/develop/releng/README.md)).

*   In the Java perspetive, click on File > Import... then Maven > Existing Maven Projects;
*   In the next window, browse to the git repository location (the Preesm root folder);
*   The project import wizard should automatically find all the projects;
    *   Note : releng/* projects are for Release Engineering, and can be omitted.
*   Click on finish.

Compilation of Preesm
---------------------

**Change perspective**  
For a better user experience in eclipse, we strongly advice that you use the "Plug-In Development" perspective of eclipse. To use this perspective, select "Plug-In Development" in Windows > Open Perspective > Others...

**Compilation**  
If there still are errors after applying the previous operations, try again to clean, refresh and build the projects.

Execution of Preesm
-------------------

In the Java or 'Plug-in Development' perspective, right-click on the 'org.ietr.preesm.ui' plug-in, the click on Debug As > Eclipse Application. A new Eclipse session is launched that behaves like the one obtained by Preesm users from the update site. The difference is that you can debug and modify code in the first Eclipse and test at the same time in the second Eclipse. You can see the tutorials to learn how to use the second Eclipse, import Preesm projects and test.

Coding Policies
---------------

The Preesm code base respects some coding policy. If you intend to develop within Preesm, we strongly advise you to follow these policy to prevent the continuous integration server to reject your contributions. These policies are simple :

1.  Follow the [checkstyle](http://checkstyle.sourceforge.net/) format provided in the [coding policy project](https://github.com/preesm/preesm-maven/blob/master/preesm-coding-policy/checkstyle/VAADER_checkstyle.xml);
2.  Make sure all the tests run without failure (run 'mvn clean verify' in the root folder of the git repository.

To help enforcing the checkstyle format, the "on-the-fly" checkstyle analyser using the Eclipse plug-in (automatically installed with the meta feature and configured through M2Eclipse connectors) will provide feedback and the Eclipse cleanup formatter will enforce most of the rules (triggered on save action).

### Build Status Notifications

To receive the build status notifications, you have to sign in on travis-ci.org with your github account and make sure your local git config mail address matches one of the github verified mail address:

1.  Check your local git config mail address, and change it to a valid one if it is not already the case  
    'git config --global --edit'
2.  Sign in on GitHub, got to Settings / Emails, and check that the mail address is present. If not, add it to the list and make sure that it is verified before going further.
3.  Got to [https://travis-ci.org/](https://travis-ci.org/) and Sign In with your GitHub account. This will allow Travis-CI to read the permissions on the repositories to make sure you have push access and also check that your mail address is verified.

Preesm team repositories set the notification policies to the Travis default : [https://docs.travis-ci.com/user/notifications/](https://docs.travis-ci.com/user/notifications/)

Troubleshooting
---------------

*   **I updated the source code and many errors appeared. Why ?**
    *   **(1)** The usual reason is that upstream API changed. Try "Help / Chek for updates". It may be required to [clean the Eclipse cache](https://stackoverflow.com/questions/9250041/how-to-clear-cache-of-eclipse-indigo) after such update.
    *   **(2)** Another reason would be the Maven configuration files that are not up to date. To update them, select all the Preesm related projects in the workspace, and press Alt+F5 then press OK.
    *   **(3)** Also, the build state of the Eclipse workspace can be corrupted after an update. To fix that, restart Eclipse then clean and rebuild your workspace.
    *   **(4)** Finaly, some updates can introduce new Preesm plug-ins that you would have to add in your workspace (File / Import ... > Maven / Existing Maven Projects > select the root folder of the git repository. Eclipse will automatically detect new plugins). If the procedures did not solve the errors, please contact us.
*   **Check for Updates is failing:** We moved the update site because of instability of the previous server. If you still have the old URL, you may have the following error: "HTTP Server 'Service Unavailable': \[....\] error response code 503." It is best to use the new update site:
    *   Go to Window > Preferences then Install/Update > Available Software Sites;
    *   Look for the PREESM site and Edit its URL;
    *   Paste the new URL : http://preesm.insa-rennes.fr/repo/complete/;
    *   Click on Apply and Close then try to check for updates again.If this does not solve the issue, please contact us.
*   **Missing artifact com.sun:tools:jar:1.8.0 is missing:** This happens because Eclipse is running using a JRE instead of a JDK. Some Eclipse plugins needs Java packages that are not bundled with the JRE, but only with the JDK. If you installed the JDK, note that by default the JDK distribution for Windows also installs a JRE. Also, the installer adds a section in the PATH variable, that points to 'C:\\ProgramData\\Oracle\\Java\\javapath', which is an alias to the JRE binaries. Please refer to [this page](https://douglascayers.com/2015/05/30/how-to-set-custom-java-path-after-installing-jdk-8/) for fixing the issue. Basically, it tells to prepend the PATH variable with the path to the JDK, while not removing the '...\\javapath' section.
