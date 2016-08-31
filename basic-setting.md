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
