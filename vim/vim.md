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

# Vim basic setting
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

