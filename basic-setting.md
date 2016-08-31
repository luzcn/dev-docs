## ssh key
* generate ssh key
```bash
ssh-keygen -t rsa -b 4096 -C
```
* Ensure ssh-agent is enabled
```bash
eval "$(ssh-agent -s)"
```

* load the private key
```bash
ssh-add ~/.ssh/id_rsa
```
