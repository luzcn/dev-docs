### Terminal + Oh My Zsh + Solarized
- iTerm2
  - http://www.iterm2.com/downloads.html
- iTerm2 Color Schemes
  - http://iterm2colorschemes.com/
  - The color settings will be imported into iTerm2. Apply them in iTerm through iTerm -> preferences -> profiles -> colors -> load presets

- Oh My Zsh
Install with Curl `sh -c "$(curl -fsSL https://raw.github.com/robbyrussell/oh-my-zsh/master/tools/install.sh)"`

- Install a patched font
  - Set this font in iTerm2 (14px is my personal preference) (iTerm -> Preferences -> Profiles -> Text -> Change Font).
  - Reference  https://github.com/robbyrussell/oh-my-zsh

### Theme
```

# Set name of the theme to load. Optionally, if you set this to "random"
# it'll load a random theme each time that oh-my-zsh is loaded.
# See https://github.com/robbyrussell/oh-my-zsh/wiki/Themes
ZSH_THEME="agnoster"

# Default User
DEFAULT_USER=zhenlu

# Which plugins would you like to load? (plugins can be found in ~/.oh-my-zsh/plugins/*)
# Custom plugins may be added to ~/.oh-my-zsh/custom/plugins/
# Example format: plugins=(rails git textmate ruby lighthouse)
# Add wisely, as too many plugins slow down shell startup.
plugins=(git
zsh-syntax-highlighting)

source $ZSH/oh-my-zsh.sh
   
# Example aliases
# alias zshconfig="mate ~/.zshrc"
# alias ohmyzsh="mate ~/.oh-my-zsh"
# alias chrome="open /Applications/Google\ Chrome.app &"
alias gitlg="git log --graph --oneline"
alias gs="git status"
alias arc="~/arcanist/arcanist/bin/arc"
alias mci="mvn clean install -DskipTests"
alias mciskipall="mvn clean install -DskipTests -Dcheckstyle.skip=true -Dfindbugs.skip=true -Dpmd.skip=true -Dmaven.javadoc.skip=true"

### cd project alias
alias cdp="cd ~/projects/console"
```

### install zsh-syntax-highlighter
```
git clone https://github.com/zsh-users/zsh-syntax-highlighting.git ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-syntax-highlighting


# add plugin
plugins=( [plugins...] zsh-syntax-highlighting)
```

### Bash Completion
```brew install bash-completion```
  
#### Add in your bash profile
```
if [ -f $(brew --prefix)/etc/bash_completion ]; then
  source $(brew --prefix)/etc/bash_completion
fi
```
#### if you run zsh, use the following one in .zshrc
```
if [ -f $(brew --prefix)/etc/bash_completion ]; then
  source $(brew --prefix)/etc/bash_completion.d
fi

source $(brew --prefix)/etc/bash_completion.d/git-prompt.sh
```

### Setup alias

```
alias zshconfig="vim ~/.zshrc"
alias ohmyzsh="vim ~/.oh-my-zsh"
alias chrome="open /Applications/Google\ Chrome.app &"
alias chat="open /Applications/HipChat.app &"
alias gitlg="git log --graph --oneline"
alias gs="git status"
```

### My vim configuration

```
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


" load filetype-specific indent files
filetype indent on      
" set cursorline



" highlight matching [{()}]
set showmatch           

" Searching
 
" search as characters are entered
set incsearch   
 
" highlight matches        
set hlsearch           

" move to beginning/end of line
nnoremap B ^
nnoremap E $

" fix the delete key in mac os x
:set backspace=indent,eol,start
```


### Visual Studio Code
- [Download](https://code.visualstudio.com/)
- Install extensions
  - Code Runner
  - Language Support for Java(TM) by Red Hat
- Customize settings
```javascript
 {
    "java.errors.incompleteClasspath.severity": "ignore",
    "workbench.editor.enablePreview": false,
    "java.home": "/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home",

    "code-runner.executorMap": {
        "javascript": "node",
        "java": "cd $dir && javac $fileName -d ./out && java -cp ./out $fileNameWithoutExt",
        "cpp": "cd $dir && g++ $fileName -o $fileNameWithoutExt && $dir$fileNameWithoutExt",
        "python": "python",
        "go": "go run",
        "shellscript": "bash",
        "csharp": "cd $dir && dotnet run",
        "scala": "scala"
    },
    "code-runner.clearPreviousOutput": true,
    "python.pythonPath": "/usr/local/opt/python/libexec/bin/python",
    "editor.minimap.enabled": false,
    "workbench.startupEditor": "newUntitledFile",
    "window.zoomLevel": 0,
    "java.referencesCodeLens.enabled": false,
    "editor.showFoldingControls": "always",
    "editor.renderControlCharacters": true,
    "files.autoSave": "onFocusChange",
    "workbench.statusBar.feedback.visible": false,
    "workbench.colorTheme": "Darcula",
    "editor.suggestSelection": "recentlyUsedByPrefix",
}

```
