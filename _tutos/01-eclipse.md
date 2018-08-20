---
title: "Eclipse Preparation"
permalink: /tutos/eclipse/
toc: true
---

Install Java
------------

Preesm requires the Eclipse environment, which runs on the Java platform. If you do not have Java yet, you will need to install it, either in your package manager, or on this website: [http://www.java.com](http://www.java.com). Please install Java 8 Java Runtime Environment (JRE) or Java Development Kit (JDK). **Under Windows, you may need to uninstall Java 6 or 7.**

Eclipse
-------

Eclipse is an extensible platform programmed in Java that allows developers to write their own IDE (Integrated Development Environment).

Download and Installation
-------------------------

You can download Eclipse here: [http://www.eclipse.org/downloads/](http://www.eclipse.org/downloads/). Please download the **"Photon Eclipse"** package to develop in Preesm. Install or extract Eclipse in a folder where you have write access.

Run Eclipse, you need to choose a workspace, i.e. a directory where your projects will be stored.

Ensure use of Java 8 or higher
------------------------------

In Eclipse, go to: Window > Preference > Java > Compiler and choose Compiler compliance level=1.8. You may check the linked JRE in Window > Preference > Java > Installed JREs.

Under Windows and Linux, you can check that Java version 1.8 is installed by typing "java -version" in a command terminal. 

Under Linux, ensure Java 8 is the default version. In the eclipse.ini file, you can set:  
-Dosgi.requiredJavaVersion=1.8

Under Linux command line, you can set java 1.8 as default with following commands (example with 64-bit openjdk):  
sudo update-java-alternatives -l  
sudo update-java-alternatives -s java-1.8.0-openjdk-amd64

If Using Ubuntu 16.04
---------------------

As documented in many places, Eclipse in Linux Ubuntu 16.04 is very slow unless GTK3 is disabled. One can solve the problem by adding:

export SWT_GTK3=0

in ~/.bashrc.

Ensure UTF8 text file encoding
------------------------------

In Eclipse, go to: Window > Preference > General > Workspace > Text file encoding and choose UTF-8.

At that stage, you may install Preesm from the [update site](http://preesm.insa-rennes.fr/website/index.php?id=tutorials) to use Preesm or install its [sources](http://preesm.insa-rennes.fr/website/index.php?id=developer) to program in Preesm.
