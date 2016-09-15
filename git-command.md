## Undoing Changes
- git checkout
You can use `git checkout` to change your code back to a previous commit, it will detach your `HEAD` and remove the commits.
```
git log --oneline 
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


