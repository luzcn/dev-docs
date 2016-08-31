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

# Vim basic commands

#### Delete
- `dw` delete the current word.
- `dd` delete the entire line.
- `u` undo

#### copy/cut/paste
- `yy`  copy the current line, `p` to paste.
- `yw` copy to the next word,  `yiw`  copy the current word excluding the surrounded whitespaces, `yaw` includes spaces.
- `dw` to cut the word.

#### move your cursor
```
h   move one character left
j   move one row down
k   move one row up
l   move one character right
w   move to beginning of next word
b   move to previous beginning of word
e   move to end of word

Ctrl-D  move half-page down
Ctrl-U  move half-page up
Ctrl-B  page up
Ctrl-F  page down
```
# Mac OS terminal configuration
Add the following lines to `~/.bash_profile`
```bash  
# bash color
export CLICOLOR=1
export LSCOLORS=gxBxhxDxfxhxhxhxhxcxcx
PS1='\[\e[0;33m\]\h:\[\e[0;32m\]\W \[\e[0;33m\]\u\$\[\e[0m\] '
 
# Arcanist
alias arc='~/arcanist/arcanist/bin/arc'
```

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

