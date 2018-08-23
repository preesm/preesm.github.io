---
title: "Git Tips and Tricks"
permalink: /docs/gittips/
toc: true
---

You'll find here a few tips & tricks to use git and manage mistakes (erroneous commits and pushes).

## Get the hash of a commit

You can get the hash of a commit (which can be useful when correcting mistakes, see below) through different ways:

*   through command lines, using **git log** and navigating to the commit of interest
*   through gitk, by clicking on a commit, its hash will appear in the "Id SHA1" panel
*   through gitg, by clicking on a commit, its hash will appear in the "SHA" panel

## Mistake: Commit on the wrong branch

Committing on the wrong branch is quite a common error, and happily, easy to correct. If you committed directly on master or develop, or erroneously committed on one of your own branches rather than an other (typically when you often switch bteween several branches), you can easily get your commits back to the right branch **if you did NOT push anything to a distant repository**. If you did so, see the Erroneous push tip.

1.  bring the branch on which you erroneously committed to its previous state:
    ```git reset --soft <latest-correct-commit-hash>```
2.  checkout the branch on which you want to have your commits:
    ```git checkout <right-branch>```
    **or**, if this branch does not exist yet, use:
    ```git checkout -b <right-branch>```
3.  commit your changes (which are now in your index) on the right branch:
    ```git commit```

This method is the cleaner way to do, but you will have to rewrite all your commit messages.

Another (less clean) way to go which will keep your commit messages is to use git reset in a reverse way in the third step (after moving to the right branch) rather than commit the changes:
```git reset --soft <latest-commit-hash>```

## Mistake: Erroneous Push

If you erroneously pushed some commits, it is possible to get the distant repositories back to a functional and correct state, but not to erase your error from history.

In such a case, the first thing to do is to send an issue in the [Github tracker](https://github.com/preesm/preesm/issues) to warn other developers of your error.

Then, you can work on your local repository in order to get back a correct state and push it:

1.  create a branch dedicated to your corrections:
    ```git checkout -b reverting-errors```
2.  revert all your erroneous commits one by one, without committing:
    ```git revert --no-commit <commit-to-revert-hash>```
3.  commit all the changes from the revert commands with an **EXPLICIT** commit message
4.  checkout the branch on which you pushed erroneously
5.  pull possible changes
6.  merge the corrections branch into the oe on which you pushed erroneously
7.  push to the distant repository

Finally, update the issue on the [Github tracker](https://github.com/preesm/preesm/issues) to announce the problem have been corrected.
