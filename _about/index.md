---
title: "About Preesm"
permalink: /about/
toc: true
---


## Presentation

**PREESM** is an **open source rapid prototyping tool**. It simulates signal processing applications and generates code for **heterogeneous multi/many-core embedded systems**. Its dataflow language eases the description of parallel signal processing applications.

The PREESM tool inputs are an **algorithm graph**, an **architecture graph**, and a **scenario** which is a set of parameters and constraints that specify the conditions under which the deployment will run. The chosen type of algorithm graph is a parameterized and hierarchical extension of Synchronous Dataflow (SDF) graphs named PiSDF. The architecture graph is named System-Level Architecture Model (S-LAM). From these inputs, **PREESM maps and schedules automatically** the code over the multiple processing elements and generates multi-core code.

![Sobel PiSDF Graph](http://preesm.insa-rennes.fr/website/data/uploads/tutorial_sobel/sequential_sobel_pisdf.png)

This image pictures a sobel filter application expressed using PiSDF.

PREESM supports and has been used to generate code for:

*   x86 multiprocessors over Linux & Windows
*   Texas Instruments Keystone I & II
*   Kalray MPPA many-core (Bostan)
*   Xilinx Zynq SoC
*   ARM Big.LITTLE & Multi-core ARM over Linux

## Partners

*   The project originates from the [VAADER Team](https://www.ietr.fr/spip.php?article1619) part of the [IETR](https://www.ietr.fr/?lang=en) UMR and hosted at [INSA Rennes](https://www.insa-rennes.fr/en.html)  
    [![Logo VAADER](/assets/logos/logo-vaader-blue-inverted-tagged.svg){:height="90px" width="200px"}](https://www.ietr.fr/spip.php?article1619) [![Logo IETR](/assets/logos/logo_IETR_rvb.jpg){:height="90px" width="180px"}](https://www.ietr.fr/?lang=en) [![Logo INSA Rennes](/assets/logos/Insa-rennes-logo.svg){:height="90px" width="230px"}](https://www.insa-rennes.fr/en.html)
*   Current work is funded by the [Cerbero European Project](http://www.cerbero-h2020.eu/) under the [H2020 Research Program](https://ec.europa.eu/programmes/horizon2020/)  
    [![Logo CERBERO](/assets/logos/cropped-cerbero-1.png){:height="90px" width="150px"}](http://www.cerbero-h2020.eu) [![Logo H2020](/assets/logos/EU-Logo-H2020_sc.svg){:height="90px" width="230px"}](https://ec.europa.eu/programmes/horizon2020/)
*   We would like to thank project contributors:
    *   [Texas Instruments](http://www.ti.com/)
    *   [Åbo Akademi University](http://www.abo.fi/)
    *   [Universidad Politécnica de Madrid (UPM)](http://www.upm.es/internacional)
    *   [Kalray](http://www.kalray.eu/)

[![Logo Abo Akademi](/assets/logos/aalogo.svg){:height="90px" width="90px"}](http://www.abo.fi) [![Logo UPM](/assets/logos/LogoUPM.svg){:height="90px" width="200px"}](http://www.upm.es/internacional) [![Logo Texas Instruments](/assets/logos/TexasInstruments-Logo.svg){:height="90px" width="200px"}](http://www.ti.com) [![Logo Kalray](/assets/logos/kalray.png){:height="90px" width="200px"}](http://www.kalray.eu/)

## Installation

Instructions for installing Preesm can be found in the [documentation](/docs/). Preesm consists in Eclipse plug-ins. If you are already familiar with Eclipse update sites, the different ways for getting Preesm are detailed in the [Getting Preesm](/get/) part.

## Contact

For general information, do not hesitate to contact as at [contact@preesm.insa-rennes.fr](mailto:contact@preesm.insa-rennes.fr)

For technical issues, please contact us via [GitHub Issues](https://github.com/preesm/preesm/issues) tracker.

## Hall of Fame

Following is the list of contributors to this project:

*  Yaset Oliva
*  Sudeep Kanur
*  Hamza Deroui
*  Jérôme Croizer
*  Thanh Hai Dao
*  Karol Desnos
*  Clément Guy
*  Julien Hascoët
*  Julien Heulot
*  Robert McNeill
*  Judicaël Menant
*  Daniel Ménard
*  Pierrick Menuet
*  Hugo Miomandre
*  Pengcheng Mu
*  Jean-François Nezan
*  Erwan Nogues
*  Ronan Parois
*  Maxime Pelcat
*  Jonathan Piat
*  Romina Racca
*  Alexandre Honorat
*  Florian Arrestier
*  Daniel Madronal
*  Antoine Morvan
*  Matthieu Wipliez
*  Leonardo Suriano

