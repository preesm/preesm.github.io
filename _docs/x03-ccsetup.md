---
title: "CCS Setup"
permalink: /docs/ccssetup/
toc: true
---

This page adds some guide lines for correctly installing and configuring Code Composer Studio (CCS) Integrated Development Environment (IDE).

On Windows
==========

Todo

On Linux
========

This was tested on Ubuntu 16.04.2 x64. Information was gathered from several places (espacially TI Wiki: [CCSv5](http://processors.wiki.ti.com/index.php/Linux_Host_Support_CCSv5), [CCSv6](http://processors.wiki.ti.com/index.php/Linux_Host_Support_CCSv6), [CCSv7](http://processors.wiki.ti.com/index.php/Linux_Host_Support_CCSv7)).

**1) Install dependencies**

```bash
sudo apt-get update
sudo apt-get install libc6:i386 libx11-6:i386 libasound2:i386 libatk1.0-0:i386 libcairo2:i386 libcups2:i386 libdbus-glib-1-2:i386 libgconf-2-4:i386 libgcrypt20:i386 libgdk-pixbuf2.0-0:i386 libgtk-3-0:i386 libice6:i386 libncurses5:i386 libsm6:i386 liborbit2:i386 libudev1:i386 libusb-0.1-4:i386 libstdc++6:i386 libxt6:i386 libxtst6:i386 libgnomeui-0:i386 libusb-1.0-0-dev:i386 libcanberra-gtk-module:i386 gtk2-engines-murrine:i386 unzip
```

**2) Install non packaged (old stable) dependencies**

The version of libgcrypt on the latest versions of Ubuntu is too recent for CCS (available version is 20, required is 11). We host the deb packages with a compatible version of libgcrypt on [this repository](http://preesm.sourceforge.net/downloads/) (originally from [webupd8](http://www.webupd8.org/2015/04/fix-missing-libgcrypt11-causing-spotify.html)). Download the libgcrypt11 deb packages and install them:

```bash
curl -o libgcrypt11\_amd64.deb http://preesm.sourceforge.net/downloads/libgcrypt11\_1.5.3-2ubuntu4.2_amd64.deb
curl -o libgcrypt11\_i386.deb http://preesm.sourceforge.net/downloads/libgcrypt11\_1.5.3-2ubuntu4.2_i386.deb
sudo dpkg -i libgcrypt11*
```

**3) Make sur [InstallJammer](http://www.installjammer.com/) will not fail**

If you are logging with PAM or as non local user, there is [bug in the CCS installer preventing it to detect user id](https://e2e.ti.com/support/development_tools/code_composer_studio/f/81/p/241930/1033455). To prevent this bug, the [solution](http://processors.wiki.ti.com/index.php/Linux_Host_Support_CCSv5#Ubuntu_12.04_64bit) is to install "nscd" (Name Service Cache Daemon):

```bash
sudo apt-get install nscd
```

You may need to relog.

**4) Download and install CCS**

Download CCS v5.2.1 from [TI website](http://processors.wiki.ti.com/index.php/Download_CCS#Code_Composer_Studio_Version_5_Downloads). Once the tar.gz file is downloaded, extract and run the installer:
```bash
tar xf CCS5.2.1.00018_linux.tar.gz
./CCS5.2.1.00018\_linux/ccs\_setup_5.2.1.00018.bin
```

Install with complete feature set.

When the installer completes, install the drivers as root (change path if you changed default settings in the installer):

```bash
sudo ~/ti/ccsv5/install\_scripts/install\_drivers.sh
```

Then you should be able to run CCS :

```bash
~/ti/ccsv5/eclipse/ccstudio
```

**5) SYS/BIOS and Linux Multicore Software Development Kits**

Before going further, make sure to shutdown CCS.

```bash
mcsdk
```

http://processors.wiki.ti.com/index.php/BIOS\_MCSDK\_2.0\_Getting\_Started_Guide

http://www.ti.com/tool/bioslinuxmcsdk

Before beginning this tutorial, make sure that both CCS and the BIOS MCSDK are installed on your computer, as explained in the following [tutorial](http://processors.wiki.ti.com/index.php/BIOS_MCSDK_2.0_Getting_Started_Guide).

[Back to the tutorial](http://preesm.insa-rennes.fr/website/index.php?id=code-generation-for-multicore-dsp)
