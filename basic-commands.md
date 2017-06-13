## Sublime Text 3 configuration
* [How to change default code snippets in Sublime Text 3](http://stackoverflow.com/questions/21190392/how-to-change-default-code-snippets-in-sublime-text-3)


## ssh key
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


# vim basic setting
Add the following lines to `~/.vimrc`
```vim
" enable syntax processing
syntax enable
let g:solarized_termcolors=256
set background=dark
colorscheme solarized

" set tab size
set tabstop=4
set softtabstop=4
set expandtab
set shiftwidth=4

" UI Config
set number
set showcmd

filetype indent on      " load filetype-specific indent files
" set cursorline

set showmatch           " highlight matching [{()}]

" Searching
set incsearch           " search as characters are entered
set hlsearch            " highlight matches

" move to beginning/end of line
nnoremap B ^
nnoremap E $
```

### Intellij 注册码

[支持正版](http://idea.lanyus.com/)
