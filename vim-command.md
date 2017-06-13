# Vim basic commands

#### Delete
- `diw` to delete in the word (doesn't include spaces)
- `daw` to delete around the word (includes spaces before the next word).
- `dd` delete the entire line.
- `u` undo

#### Copy/Cut and paste:
- Position the cursor where you want to begin cutting.
- Press `v` to select characters (or uppercase `V` to select whole lines, or `Ctrl-v` to select rectangular blocks).
- Move the cursor to the end of what you want to cut.
- Press `d` to cut (or `y` to copy).
- Move to where you would like to paste.
- Press `P` (upper case) to paste before the cursor, or `p` to paste after.

`d` stands for delete in Vim, which in other editors is usually called cut
`y` stands for yank in Vim, which in other editors is usually called copy

- `yy`  copy the current line, `p` to paste after the cursor.
- `yw` copy to the next word,  `yiw`  copy the current word excluding the surrounded whitespaces, `yaw` includes spaces.

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

" fix the delete key in mac os x
:set backspace=indent,eol,start

```
