# The UX team work notes

###
https://console.us-phoenix-1.oraclecloud.com/
tenant: uxdev
user: uxdev
pass: uxdevPass!1!

hg.client.debug.enable_feature("streaming")

### useful links
- [Hg builds](https://teamcity.aka.lgl.grungy.us/viewType.html?buildTypeId=UX_HgBuild)

### R2 access ssh
`ssh -C -L localhost:8082:ingress-proxy.svc.ad1.r2:8080 bastion.r2.oracleiaas.com`



### Build lein css/cljs
```bash
 lein do clean, css, cljsbuild once
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


```
git checkout -b <new branch>
git commit -am "commit message"
git pull --rebase origin master
git push --set-upstream origin <new branch>
```
summary pattern
[https://confluence.aka.lgl.grungy.us/display/UX/Development](https://confluence.aka.lgl.grungy.us/display/UX/Development)

### git useful commands
- git squash to combine two commands as one `git rebase -i HEAD~3` will list the first 3 commits, then edit the proper files.

### Specify a custom config
- Develop on R2 if R0 is down: `CONFIGS=base,dev-r2 lein figwheel`
- Test in integ-next: `CONFIGS=base,dev-r0-next lein figwheel`
- The console links:
```
r0/next: https://console-next-integ.aka.r0.grungy.us/ 
r0/stable: https://console-stable-integ.aka.r0.grungy.us/
r2: https://console.us-az-phoenix-1.oracleiaas.com/?tenant=uxdev
```

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

### Test
1. Download the Chome webdriver here: https://sites.google.com/a/chromium.org/chromedriver/downloads
2. Place it in /opt/google/chrome/
3. Run the commands `CLIENT_STAGE=integ-stable HG_ENDPOINT="http://localhost:8181" HG_WEBDRIVER_BROWSER="chrome" lein ui-test`
```
CLIENT_STAGE=r2-ad1 CONFIG_FILES=./config/base-config.edn,./config/r2-ad1-config.edn,./config/automated-test-config.edn HG_ENDPOINT=http://localhost:8181 HG_WEBDRIVER_BROWSER=chrome lein ui-test -f d -t post-deploy -t dep-cns 
```

### artifacts 
use this [link](http://artifactoryui.oraclecorp.com/artifactory/webapp/#/artifacts/browse/tree/search/package/eyJxdWVyeSI6eyJzZWFyY2giOiJnYXZjIiwiZ3JvdXBJRCI6ImNvbS5vcmFjbGUucGljLmNvbW1vbnMiLCJhcnRpZmFjdElEIjoiY29yZXNlcnZpY2VzLWFwaS1zcGVjIiwic2VsZWN0ZWRSZXBvc2l0b3JpZXMiOltdfSwic2VsZWN0ZWRQYWNrYWdlVHlwZSI6eyJpZCI6ImdhdmMiLCJkaXNwbGF5TmFtZSI6IkdBVkMiLCJpY29uIjoicG9tIn0sInNlbGVjdGVkUmVwb3NpdG9yaWVzIjpbXSwiY29sdW1ucyI6WyJhcnRpZmFjdCIsImdyb3VwSUQiLCJhcnRpZmFjdElEIiwidmVyc2lvbiIsImNsYXNzaWZpZXIiLCJyZXBvIiwicGF0aCIsIm1vZGlmaWVkIl19) to review the service-api spec.



# CM and oncall
### Deplpy to R0
- Send an email (sic_technical_content_us_grp@oracle.com,opc_ux_us_grp@oracle.com) to Technical Content and UX describing the changes and when they will be in R2.
- In [Hg Master](https://teamcity.aka.lgl.grungy.us/project.html?projectId=UX_HgMaster) -> Hg Builds, pin the build you want to deploy and tag it as "stable".
- Follow the [Deploying a specific build](https://confluence.aka.lgl.grungy.us/display/UX/Deploying+a+Specific+Build), deploy the pined build number in "Hg R0 Stable Deploy".
- Run the Post Deployment tests.

### Deplpy to R2
-  create the CM, find [instructions](https://confluence.aka.lgl.grungy.us/display/UX/Change+Management+in+a+Nutshell)
- When deploying, update the CM Status to 'Implementing'.
- Follow the [Deploying a specific build](https://confluence.aka.lgl.grungy.us/display/UX/Deploying+a+Specific+Build), deploy to each AD.
- Pin the build number as "prod", which should also be pinned as "stable".

### interview summary
- Decision: Strong Inclined / Inclined / Not Inclined / Strong Not Inclined
- Summary: Couple of sentences summarizing your interview experience with this candidate
- Pros / Cons: 3-4 pros and cons that you observed for this candidate
