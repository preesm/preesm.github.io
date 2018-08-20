---
title: "Documentation | Building Spider - [BETA]"
permalink: /docs/buildspider/
toc: true
---

**Writing of this tutorial is currently in progress. If you wish to follow this tutorial, please consider yourself as a beta tester (and don't hesitate to send us any improvement suggestions).**

Compile Spider library with PThread
-----------------------------------

**Note**: CodeBlocks users, make sure you do not use the version of MinGW provided with CodeBlocks installer (4.9.2) as it ships with its own outdated, incompatible version of PThread. Please install the latest version (5.3.0) from the MinGW website \[[link](http://preesm.insa-rennes.fr/website/data/uploads/tutorial_spider/pthread-2.10.0.zip)\].

1.  Go on Spider's GitHub page \[[link](https://github.com/preesm/spider.git)\], choose the **master** branch and download the source code.  
    Or
    *   git clone https://github.com/preesm/spider.git
    *   cd spider/
2.  Inside the "/master/" directory, use the CMake script corresponding to your IDE/compiler and compile the library.  
    **Note**: If you are using Windows, follow the instruction given in "/master/libspider/lib/ReadMe.md" before compiling Spider. PThread 2.10.0 is provided already compiled here \[[link](http://preesm.insa-rennes.fr/website/data/uploads/tutorial_spider/pthread-2.10.0.zip)\] for your convenience.
3.  Copy "spider.h" from "/master/libspider/spider/" to "/<project>/Code/lib/spider/include/"
4.  Copy the library file(s) from the output directory to "/<project>/Code/lib/spider/lib/"
    *   With **GCC**: libSpider.so
    *   With **Visual**: Spider.dll and Spider.lib
    *   With **CodeBlocks**: libSpider.dll and libSpider.dll.a