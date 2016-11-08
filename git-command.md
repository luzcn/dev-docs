### git push to remote branch
When you have your local changes that you want to push them to upstream. The command for this is simple: `git push <remote-name> <branch-name>`. 

For example, push your `development` branch to your origin server
```
git push origin development
```
- To sync your remote branch with `master` branch, you need to `pull` from the `origin master`, resolve any merge conflicts, then force to `push` to remote.
```
## in your development branch
git pull origin/master development

## resolve any merge conflicts

git pull -f origin/development development
```

### git delete remote branch
```
git push origin --delete <branch_name>
```

### git log
- display commits in single lines
```
git log --oneline 
```
- showing a range of commits 
You can specify the range with two commits id, for example:
```
git log --oneline 94cae8a8b373...3cd514d304b2
```
Or, you can simpley use `-<number>` to display the latest `<number>` commits.
```
git log --oneline -2
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

### git revert
