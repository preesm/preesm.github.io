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

## Pull a Rebased Branch

If someone else is working on your branch **A**, he/she might rebase the remote reference of **A** on a more recent version of **develop**. In this situation, if you try to use  `git pull` to update your local reference of **A**, this will lead to errors since the local and remote references have *diverged*.

The proper approach to deal with such situation is to use `git pull --rebase`. This will rebase your local reference of **A** on the remote one that is being pulled. The changes done locally since the last pull will be rebased on the changes pushed remotely, and conflicts may occurs, as in a usual rebase. This command can be decomposed in several steps, as follows:

```sh
# assuming HEAD is on local reference of A

## Step 1 - fetch updates

# fetch updates
git fetch --all -p
# create a copy of local reference
git branch A_local
# stash changes
git stash -u
# forces A to the state of the remote reference
git reset --hard origin/A

## Step 2 - rebase and apply local changes

# get back on the local reference of A before the fetch/reset
git checkout A_local
# rebase (conflicts can occur)
git rebase A
# restore (conflicts can occur)
git stash pop

## Step 3 - update A

# position HEAD on A
git checkout A 
# merge the rebased changes in A. No conflicts to expect here 
# since they should have been handled during rebase.
git merge --ff A_local
# remove local copy
git branch -D A_local
```


## Solving git Conflicts

Following the [Development workflow for PREESM and Graphiti](/docs/devdoc/#development-workflow-for-preesm-and-graphiti), the developer might encounter conflicts when rebasing before a pull request. The sequence of command would look like the following:

```shell
# position HEAD on branch with new features
git checkout newFeatureBranch
# make sure remote develop branch is sync
git fetch --all -p

# rebase HEAD (new feature branch) on remote develop
git rebase origin/develop

### Here a conflits can occur
### and solving them needs to be done

# when conflicts are solved, force push the branch 
# for later merge by admins
git push -f
```

There are 3 ways of handling a conflict, as described [here](https://help.github.com/en/articles/resolving-merge-conflicts-after-a-git-rebase):

*  `git rebase --abort` : simply cancels everything and revert back to before the first rebase command;
*  `git rebase --skip` : skip the commit causing the conflict. This might implies more conflicts when applying later patches but is usefull in some cases (see below);
*  Manually resolve the conflicts, [using git commands](https://help.github.com/en/articles/resolving-a-merge-conflict-using-the-command-line) or assisted by a [merge tool](https://gist.github.com/karenyyng/f19ff75c60f18b4b8149/e6ae1d38fb83e05c4378d8e19b014fd8975abb39).

The preferred way is to resolve the conflict with a merge tool. Indeed such tools are designed for that specific purpose, and automate most of the git operations. On top of that, some of the tools (such as [kdiff3](https://github.com/KDE/kdiff3)) can automatically merge parts of the conflict, and ask the user to manually merge only the one it could not handle.

### Setting Up a Merge Tool

To get the list of merge tools installed on the machine, simply run

```shell
git mergetool --tool-help
```

Most of linux distribution have kdiff3, diffuse, meld, vimdiff, etc. available in their package repositories. To set the default merge tool, the git config command can be used:

```shell
git config --global merge.tool <mergetool>
# skip prompt before merging files
git config --global mergetool.prompt false
```

### Using a Merge Tool

When a conflict is encountered, the `git rebase` command will stop and display which files caused the conflict. Running `git mergetool` will automatically run the merge tool on the files causing the conflict.

We refer the developers to the documentation of their merge tool of choice.

### Continuing after Merging

Once all conflicts have been solved, the command `git rebase --continue` will resume the procedure, untill the next conflict or all commits have been applied.

A naive approach of the full rebase would be similar to the following bash sequence:

```bash
#!/bin/bash

git checkout newFeatureBranch
git fetch --all -p

git rebase origin/develop
REBASE_CODE=$?

while [ $REBASE_CODE != 0 ]; do
  git mergetool
  git rebase --continue
  REBASE_CODE=$?
done

git push -f
```

### Why `git rebase --skip` ?

A situation where two divergent branches actually apply the same evolution in the code but with textual differences, although rare, is not uncommon. For instance, using a different name for the same new variable. This would not impact the code behavior, but lead to a conflict.

In such a situation, one (or more) commit on each branch would add at the same location a different sequence of characters, for which only one needs to be selected. At this point, one of the two commit can be discarded. The `git rebase --skip` command will discard the "useless" commit from the LOCAL branch.

More about this:
*  [https://mindriot101.github.io/blog/2014/08/27/git-rebase-skip-is-fine/](https://mindriot101.github.io/blog/2014/08/27/git-rebase-skip-is-fine/)
*  [https://stackoverflow.com/questions/9539067/what-exactly-does-git-rebase-skip-do](https://stackoverflow.com/questions/9539067/what-exactly-does-git-rebase-skip-do)
