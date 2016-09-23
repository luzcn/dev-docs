
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
