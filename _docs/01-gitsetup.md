---
title: "Install and Configure git"
permalink: /docs/gitsetup/
toc: true
---

git is the Decentralized Version Control System (DVCS) used by the Preesm development team to version the PREESM and Graphiti sources. In order to get the sources of these projects and to contribute to them, you will need to install git and to create a Github account.

## Learning git

If you're not familiar with git and plan to contribute to PREESM or Graphiti projects, you should first learn a little bit about git.

The [git book](http://git-scm.com/book/) is available in several languages and will present almost everything you need to know about git (you should at least read Chapters 2 and 3).

The `--help` option after the different git commands will also help you to understand their goals and the way to use it.

You can also find a few git tips & tricks more specific to PREESM development on [this page](/docs/gittips).

Finally, the Internet is full of resources about git (see for example StackOverflow questions related to git: [https://stackoverflow.com/questions/tagged/git](https://stackoverflow.com/questions/tagged/git)).

## Install git & git-gui

### Windows

Download & launch the installer: [http://msysgit.github.com/](http://msysgit.github.com/)

### Ubuntu

```bash
apt-get install git  
apt-get install git-gui
```

### Mac

Download & launch the installer: [http://code.google.com/p/git-osx-installer](http://code.google.com/p/git-osx-installer)

## Configure git

git uses your email address to identify your commits, as well as a user name (basically your full name) to make it easier to trace commits. In order to set the email address and user name git will use in your commits, you need to launch the following commandlines, where <name> is the name you want git to use to identify your commits and <mail> the email address you want git to attach to your commits.

*   git config --global user.name <name>
*   git config --global user.email <mail>

## Create a github account

PREESM and Graphiti sources are hosted on [Github](https://github.com) repositories.

In order to contribute to PREESM or Graphiti (_i.e._, push to the corresponding Github repository), you will thus need to get a Github account and to be acknowledged as a contributor of the concerned project.  
  
To do so, first create a Github account and make sure the email address your registered in your git configuration (see above) is one of the registered email addresses of your Github account (Github > Account settings > Emails).

You can also add one or more SSH keys to your Github account (Github > Account settings > SSH Keys), which make it easier when working with Github repositories (Github won't ask you your login and password each time you want to pull or push).

You can get the code and propose contributions without being part of the teams of contributors through the Fork & Pull request process ([learn more about the Fork & Pull model](https://help.github.com/articles/using-pull-requests)).

## Development workflow for PREESM and Graphiti

Each of our repositories get two main branches (which are permanent):

*   The **master** branches are dedicated to contain the clean and stable code and are modified only by releases of new versions of our softwares ; you should **NOT** commit or push to the master branch of a repository and you should **NOT** merge into a master branch. Push to masters are blocked for all except members of preesm/admins.
*   The **develop** branches are the integration branches where new features are merged and tested ; they contain the latest version of the code. Push to masters are blocked for all except members of preesm/admins.

As a contributor of the PREESM and/or Graphiti projects, we ask you to follow the following wokflow when developing:

*   Clone the needed repositories (git clone \<url\>, see also [Building Preesm](/docs/buildpreesm))
*   Switch to the develop branches (git checkout develop)
*   Start your own development branch(es) dedicated to the new features you want to add/the bug you want to fix (git checkout -b \<new-branch-name\>)
*   Frequently check for latest version of the code in order to stay up to date with the remote repositories (git fetch)
*   When finished, after rebasing it on latest develop (see [below](/docs/gitsetup/#clean-mergerebase-of-your-branches-into-develop)), create a pull request for one of the admins to merge it:

```bash
##
## 1. sync develop with remote repository
##

git checkout develop
git pull #there should be no conflict since you should not commit on develop

##
## 2. rebase new branch on develop
## note:  conflicts might occur during this step
##  -> use 'git mergetool' and 'git rebase --continue'
##

git checkout <new-branch-name>
git rebase develop
git push -f
```
