---
title: "Getting Preesm"
permalink: /get/
toc: true
---

## Prerequisites

Windows (32/64), Linux (32/64) and MacOSX (64) are supported.

⚠ **Preesm requires Java 8 or higher available in the PATH** ⚠

## Released Binaries

To use Preesm using these releases, simply download, unzip and run ```./eclipse``` (```eclipse.exe``` on Windows).

*  [https://github.com/preesm/preesm/releases](https://github.com/preesm/preesm/releases)
*  Mirror: [http://preesm.insa-rennes.fr/releases/](http://preesm.insa-rennes.fr/releases/)

## Eclipse Update-Site

Update site is another name for P2 repository. Update sites usually hold OSGi bundles (Elcipse features, plugins, etc.). This is usefull when integrating Preesm within an existing Eclipse installation. 

To install Preesm using the update-site:

1. Add the update site; Refer to [Eclipse documentation to add a new update site](http://help.eclipse.org/photon/index.jsp?topic=%2Forg.eclipse.platform.doc.user%2Ftasks%2Ftasks-127.htm);
  Use the Preesm Update site URL (see below);
2. Refer to [Eclipse documentation to install new software](http://help.eclipse.org/photon/index.jsp?topic=%2Forg.eclipse.platform.doc.user%2Ftasks%2Ftasks-124.htm);
  Select the **Preesm** feature;

#### URLs

*  **Stable** Releases: [http://preesm.insa-rennes.fr/repo/](http://preesm.insa-rennes.fr/repo/)
*  Snapshot Releases: [https://preesm.github.io/preesm-snapshot-site/](https://preesm.github.io/preesm-snapshot-site/)
*  Old Releases: [http://preesm.insa-rennes.fr/repo/update-site-backups/](http://preesm.insa-rennes.fr/repo/update-site-backups/) 

## Source Code

The source code of Preesm is available on [Github](https://github.com/preesm/). Please see [Building Preesm](/docs/buildpreesm/) for instructions to build the project.

*  [https://github.com/preesm/](https://github.com/preesm/)
