### fix brew install not writable problem
```
sudo chown -R `whoami`:admin /usr/local/bin
```

### copy file to remote host
```
scp ./config.yml streaming-util-2001.node.ad2.r1:<your path>
```

### Sublime Text 3 configuration
* [How to change default code snippets in Sublime Text 3](http://stackoverflow.com/questions/21190392/how-to-change-default-code-snippets-in-sublime-text-3)

### generate PEM key
```
openssl genrsa -out ~/.oci/oci_api_key.pem 2048
```

### ssh key
* generate ssh key
```bash
ssh-keygen -t rsa -b 4096
```
* Ensure ssh-agent is enabled
```bash
eval "$(ssh-agent -s)"
```

* load the private key
```bash
ssh-add ~/.ssh/id_rsa
```
* copy to clipboard
```bash
pbcopy < ~/.ssh/id_rsa.pub
```

## Mac OS terminal 
### configuration
Add the following lines to `~/.bash_profile`
```bash  
# bash color
export CLICOLOR=1
export LSCOLORS=gxBxhxDxfxhxhxhxhxcxcx
# export PS1='\[\e[0;33m\]\h:\[\e[0;32m\]\W \[\e[0;33m\]\u\$\[\e[0m\] '
export PS1="\[\e[0;33m\]\h:\[\e[0;32m\]\W\$(parse_git_branch) \[\e[0;33m\]\u\$\[\e[0m\] "

# git branch info
parse_git_branch() {

git branch 2> /dev/null | sed -e '/^[^*]/d' -e 's/* \(.*\)/ (\1)/'

}


```
### display running process
`ps -e` will list all the running process.
or, use `top -o cpu` will lively display the process info order by cpu usage.

### Open iBook folder
```bash
open ~/Library/Mobile\ Documents/iCloud\~com\~apple\~iBooks/Documents
```

### Intellij 注册码

[支持正版](http://idea.lanyus.com/)
