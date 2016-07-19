# The UX team work notes

### Build lein css/cljs
```bash
 lein clean
 lein css
 lein cljsbuild once
```
verify/test the correctness.

### Create and config the local git branch 
`git config --global alias.br "checkout --track origin/master -b"`

`git br <branch name>` will always track `master`

### git useful commands
- git squash to combine two commands as one `git rebase -i HEAD~3` will list the first 3 commits, then edit the popped file.

### The workflow of send out code review to *Phaboricator* 
```git
git add .
git commit -m "<Your commit message>"
git pull --rebase
arc diff HEAD^
```
arc summary pattern
[https://confluence.aka.lgl.grungy.us/display/UX/Development](https://confluence.aka.lgl.grungy.us/display/UX/Development)

### Develop on R2 if R0 is down
`CONFIGS=base,dev-r2 lein figwheel`

### REPL useful commands
- view the global application state
```clj
(use 'figwheel-sidecar.repl-api)
(cljs-repl)
(require '[hg.client.re-frame :as re-frame])

(-> @re-frame.db/app-db :viewstack)
```

### debug 
use `log/debug` to print out
```clj
  (log/debug "Resource Result:" (println-str @result))
```
