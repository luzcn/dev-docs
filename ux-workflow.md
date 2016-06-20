# The UX team work notes

### Build lein css/cljcs
- lein clean
- lein css
- lein cljsbuild once
verify/test the correctness.

### Create and config the local git branch 
`git config --global alias.br "checkout --track origin/master -b"`

`git br <branch name>` will always track `master`

### The workflow of send out code review to *Phaboricator* 
```
Git add .
Git commit -m “”
Git pull --rebase
arc diff HEAD^

arc summary pattern
https://confluence.aka.lgl.grungy.us/display/UX/Development
```

### REPL useful commands
- view the global application state
```clj
(require '[hg.client.re-frame :as re-frame])
(re-frame/app-db)
```
