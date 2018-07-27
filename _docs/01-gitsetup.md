---
title: "Documentation | Install and Configure git"
permalink: /docs/gitsetup/
toc: true
---

git is the Decentralized Version Control System (DVCS) used by the Preesm development team to version the PREESM, DFTools and Graphiti sources. In order to get the sources of these projects and to contribute to them, you will need to install git and to create a Github account.

The following topics are covered in this tutorial:

*   Installation and configuration of the git Decentralized Version Control System
*   Creation and configuration of a Github account
*   Development workflow for PREESM, DFTools and Graphiti

Learning git
------------

If you're not familiar with git and plan to contribute to PREESM, DFTools or Graphiti projects, you should first learn a little bit about git.

The [git book](http://git-scm.com/book/) is available in several languages and will present almost everything you need to know about git (you should at least read Chapters 2 and 3).

The --help option after the different git commands will also help you to understand their goals and the way to use it.

You can also find a few git tips & tricks more specific to PREESM development on [this page](http://preesm.insa-rennes.fr/website/index.php?id=git-tips-tricks).

Finally, the Internet is full of resources about git (see for example StackOverflow questions related to git: [https://stackoverflow.com/questions/tagged/git](https://stackoverflow.com/questions/tagged/git)).

Install git & git-gui
---------------------

### Windows

Download & launch the installer: [http://msysgit.github.com/](http://msysgit.github.com/)

### Ubuntu

apt-get install git  
apt-get install git-gui

### Mac

Download & launch the installer: [http://code.google.com/p/git-osx-installer](http://code.google.com/p/git-osx-installer)

Configure git
-------------

git uses your email address to identify your commits, as well as a user name (basically your full name) to make it easier to trace commits. In order to set the email address and user name git will use in your commits, you need to launch the following commandlines, where <name> is the name you want git to use to identify your commits and <mail> the email address you want git to attach to your commits.

*   git config --global user.name <name>
*   git config --global user.email <mail>

Create a github account
-----------------------

PREESM, DFTools and Graphiti sources are hosted on [Github](https://github.com) repositories.

In order to contribute to PREESM, DFTools or Graphiti (_i.e._, push to the corresponding Github repository), you will thus need to get a Github account and to be acknowledged as a contributor of the concerned project.  
  
To do so, first create a Github account and make sure the email address your registered in your git configuration is one of the registered email addresses of your Github account (Github > Account settings > Emails). You can then contact one of the administrators of the projects ([Clément Guy](mailto:clement.guy@insa-rennes.fr), [Maxime Pelcat](mailto:mpelcat@insa-rennes.fr) or [Jean-François Nezan](mailto:jnezan@insa-rennes.fr)) to become a collaborator of the Preesm development team.

You can also add one or more SSH key to your Github account (Github > Account settings > SSH Keys), which make it easier when working with Github repositories (Github won't ask you your login and password each time you want to pull or push).

_Note: You can also get the code and propose contributions without being part of the teams of contributors through the Fork & Pull request process ([learn more about the Fork & Pull model](https://help.github.com/articles/using-pull-requests))._

Development workflow for PREESM, DFTools and Graphiti
-----------------------------------------------------

Each of our repositories get two main branches (which are permanent):

*   The **master** branches are dedicated to contain the clean and stable code and are modified only by releases of new versions of our softwares ; you should **NOT** commit or push to the master branch of a repository and you should **NOT** merge into a master branch without the agreement of one of its administrators.
*   The **develop** branches are the integration branches where new features are merged and tested ; they contain the latest version of the code.

As a contributor of the PREESM, DFTools and/or Graphiti projects, we ask you to follow the following wokflow when developing:

*   Clone the needed repositories (git clone <url>, see also [Building Preesm](http://preesm.insa-rennes.fr/website/index.php?id=building-preesm))
*   Switch to the develop branches (git checkout develop)
*   Start your own development branch(es) dedicated to the new features you want to add/the bug you want to fix (git checkout -b <new-branch-name>)
*   Frequently check for latest version of the code in order to stay up to date with the remote repositories (git fetch)
*   When finished, merge your branch into your up to date local develop branch (git checkout develop + git pull + git merge <new-branch-name>), before to push your local develop branch (git push)

### Clean merge/rebase of your branches into develop

Depending what is the branch you want to merge into develop, you should prefer to use rebase before merging or to use merge -no-ff. See the following article: [getting solid at git rebase vs. merge](https://medium.com/@porteneuve/getting-solid-at-git-rebase-vs-merge-4fa1a48c53aa).
