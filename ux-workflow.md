# The UX team work notes

### Build lein css/cljs
```bash
 lein clean
 lein css
 lein cljsbuild once
```
compile locally to catch any optimization errors
```
lein ring uberjar
```
> " what I do after doing a git pull on master (then arc patch DXXXX, if needed) is lein do clean, css, uberjar and make sure to look at the optimizer output (starts with: Applying optimizations :advanced to 373 sources, ends with: Successfully compiled "resources/public/js/gen/hg.js" in 91.026 seconds.)  look for the line "WARNING: 5 error(s), 1 warning(s)" if the N error(s) number is anything but 0 then the .js is broken, meaning the build is broken."


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

### Specify a custom config
- Develop on R2 if R0 is down: `CONFIGS=base,dev-r2 lein figwheel`
- Test in integ-next: `CONFIGS=base,dev-r0-next lein figwheel`

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

### artifacts 
use this [link](http://artifactoryui.oraclecorp.com/artifactory/webapp/#/artifacts/browse/tree/search/package/eyJxdWVyeSI6eyJzZWFyY2giOiJnYXZjIiwiZ3JvdXBJRCI6ImNvbS5vcmFjbGUucGljLmNvbW1vbnMiLCJhcnRpZmFjdElEIjoiY29yZXNlcnZpY2VzLWFwaS1zcGVjIiwic2VsZWN0ZWRSZXBvc2l0b3JpZXMiOltdfSwic2VsZWN0ZWRQYWNrYWdlVHlwZSI6eyJpZCI6ImdhdmMiLCJkaXNwbGF5TmFtZSI6IkdBVkMiLCJpY29uIjoicG9tIn0sInNlbGVjdGVkUmVwb3NpdG9yaWVzIjpbXSwiY29sdW1ucyI6WyJhcnRpZmFjdCIsImdyb3VwSUQiLCJhcnRpZmFjdElEIiwidmVyc2lvbiIsImNsYXNzaWZpZXIiLCJyZXBvIiwicGF0aCIsIm1vZGlmaWVkIl19) to review the service-api spec.
