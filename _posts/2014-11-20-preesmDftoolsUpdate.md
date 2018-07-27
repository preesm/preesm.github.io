---
title:  "PREESM 2.1.0 and DFTools 1.1.3"
search: false
last_modified_at: 2014-11-20T08:06:00-05:00
toc: false
---

The last versions of PREESM (2.1.0) and DFTools (1.1.3) has been released on our update-site (see the side bar for a link).

These versions provide several bugfixes and improvements :

*   a new workflow task for transformation of static PiMM algorithms (without configuration actors) is now available;
    *   id: org.ietr.preesm.pimm.checker.task.PiMMAlgorithmCheckerTask;
    *   input and output are a PiGraph (port name: PiMM).
*   a checker for PiMM algorithm is now available, it can be accessed by a right click on a .pi file > PREESM > Algorithm Checker or through a workflow task;
    *   id: org.ietr.preesm.experiment.pimm2sdf.StaticPiMM2SDFTask;
    *   inputs are a PiGraph (port name: PiMM) and a PreesmScenario (port name: scenario);
    *   output is a SDFGraph (port name: SDF).
*   editor now supports rename refactorings and copy/paste of pairs of .pi/.diagram files;
*   bug preventing to set a new refinement to an actor without an initialization function has been corrected;
*   export of .graphml files (through DAG and SDF exporters) in non-existing folders is now possible (folders are created).
