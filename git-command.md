### sync forked repo with original
```
git remote add upstream https://github.com/[Original Owner Username]/[Original Repository].git
git fetch upstream
git checkout master
git merge upstream/master
git push
```

### prune the removed remote branches
```
git remote prune origin
```

### git change current branch HEAD to older commit
```
git reset --hard <commit id>
```

### git push to remote branch
The command for this is simple: 
```
git push <remote-name> <branch-name>
``` 
For example, push your `development` branch to your origin server
```
git push origin development
```
### Sync your remote branch with `master` branch
you need to `pull` and `rebased` from the `origin master`, resolve any merge conflicts, then force to `push` to remote.
```
;; in your development branch
git pull --rebase origin master

;; resolve any merge conflicts, then push to remote

;; if the remote branch is not set
git push --set-upstream origin development

;; Or you can push with force
git push -f
```

### pull and overwrite your local branch
You can `fectch` the remote branch without merge, then `reset` 

```
git fetch --all
git reset --hard origin/<branch_name> ;; not need the <branch_name> if reset from the upstream remote
```

**Important: If you have any local changes, they will be lost. With or without --hard option, any local commits that haven't been pushed will be lost.[*]
If you have any files that are not tracked by Git (e.g. uploaded user content), these files will not be affected.**


```
git fetch --all
```
Then, you have two options:
```
git reset --hard origin/master
```
OR If you are on some other branch:
```
git reset --hard origin/<branch_name>
```
Explanation:
git fetch downloads the latest from remote without trying to merge or rebase anything.

Then the git reset resets the master branch to what you just fetched. The `--hard` option changes all the files in your working tree to match the files in origin/master

### git delete remote branch
```
git push origin --delete <branch_name>
```

### git log
- display commits in single lines
```
git log --oneline 
```
- listing a range of commits 
You can specify the range with two commits id, for example:
```
git log --oneline 94cae8a8b373...3cd514d304b2
```
Or, you can simpley use `-<number>` to display the latest `<number>` commits.
```
git log --oneline -2
```
- Display the log of a single file
```
git log --follow -p <file name>
```

### git checkout
You can use `git checkout` to change your code back to a previous commit, it will detach your `HEAD` and remove the commits.
```
git log --pretty=format:"%h - %an, %ar : %s"
git checkout <commit>
```
You can `checkout` a specific file, if the file is not commited. It will reset the file to `HEAD`.
```
git checkout <filename>
```
Or, you can change back to the specific commit
```
git checkout <commit> <filename>
```

### display and config git username/email
```
git config user.name  ;; will display the user name

git config --list   ;; list all the config information
```
