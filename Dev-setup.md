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
# If you come from bash you might have to change your $PATH.
# export PATH=$HOME/bin:/usr/local/bin:$PATH

# Path to your oh-my-zsh installation.
export ZSH="/Users/zheng.lu/.oh-my-zsh"

# Set name of the theme to load --- if set to "random", it will
# load a random theme each time oh-my-zsh is loaded, in which case,
# to know which specific one was loaded, run: echo $RANDOM_THEME
# See https://github.com/robbyrussell/oh-my-zsh/wiki/Themes
ZSH_THEME="agnoster"

# default user
DEFAULT_USER=zheng.lu

# Set list of themes to pick from when loading at random
# Setting this variable when ZSH_THEME=random will cause zsh to load
# a theme from this variable instead of looking in ~/.oh-my-zsh/themes/
# If set to an empty array, this variable will have no effect.
# ZSH_THEME_RANDOM_CANDIDATES=( "robbyrussell" "agnoster" )

# Uncomment the following line to use case-sensitive completion.
# CASE_SENSITIVE="true"

# Uncomment the following line to use hyphen-insensitive completion.
# Case-sensitive completion must be off. _ and - will be interchangeable.
# HYPHEN_INSENSITIVE="true"

# Uncomment the following line to disable bi-weekly auto-update checks.
# DISABLE_AUTO_UPDATE="true"

# Uncomment the following line to automatically update without prompting.
# DISABLE_UPDATE_PROMPT="true"

# Uncomment the following line to change how often to auto-update (in days).
# export UPDATE_ZSH_DAYS=13

# Uncomment the following line if pasting URLs and other text is messed up.
# DISABLE_MAGIC_FUNCTIONS=true

# Uncomment the following line to disable colors in ls.
# DISABLE_LS_COLORS="true"

# Uncomment the following line to disable auto-setting terminal title.
# DISABLE_AUTO_TITLE="true"

# Uncomment the following line to enable command auto-correction.
# ENABLE_CORRECTION="true"

# Uncomment the following line to display red dots whilst waiting for completion.
# COMPLETION_WAITING_DOTS="true"

# Uncomment the following line if you want to disable marking untracked files
# under VCS as dirty. This makes repository status check for large repositories
# much, much faster.
# DISABLE_UNTRACKED_FILES_DIRTY="true"

# Uncomment the following line if you want to change the command execution time
# stamp shown in the history command output.
# You can set one of the optional three formats:
# "mm/dd/yyyy"|"dd.mm.yyyy"|"yyyy-mm-dd"
# or set a custom format using the strftime function format specifications,
# see 'man strftime' for details.
# HIST_STAMPS="mm/dd/yyyy"

# Would you like to use another custom folder than $ZSH/custom?
# ZSH_CUSTOM=/path/to/new-custom-folder

# Which plugins would you like to load?
# Standard plugins can be found in ~/.oh-my-zsh/plugins/*
# Custom plugins may be added to ~/.oh-my-zsh/custom/plugins/
# Example format: plugins=(rails git textmate ruby lighthouse)
# Add wisely, as too many plugins slow down shell startup.
plugins=(git 
zsh-syntax-highlighting 
# vi-mode
zsh-autosuggestions
)

source $ZSH/oh-my-zsh.sh

# User configuration

# export MANPATH="/usr/local/man:$MANPATH"

# You may need to manually set your language environment
# export LANG=en_US.UTF-8

# Preferred editor for local and remote sessions
# if [[ -n $SSH_CONNECTION ]]; then
#   export EDITOR='vim'
# else
#   export EDITOR='mvim'
# fi

# Compilation flags
# export ARCHFLAGS="-arch x86_64"

# Set personal aliases, overriding those provided by oh-my-zsh libs,
# plugins, and themes. Aliases can be placed here, though oh-my-zsh
# users are encouraged to define aliases within the ZSH_CUSTOM folder.
# For a full list of active aliases, run `alias`.
#
# Example aliases
# alias zshconfig="mate ~/.zshrc"
# alias ohmyzsh="mate ~/.oh-my-zsh"
alias gitlg="git log --graph --oneline"
alias gs="git status"
alias cdp="cd ~/project/dnr"
alias cdg="cd /Users/zheng.lu/project/go/src"

# heroku
alias h="heroku"
alias hsu="heroku sudo"

# python
alias python="/usr/local/bin/python3"
alias pip="/usr/local/bin/pip3"

# java maven
alias mci="mvn clean install -DskipTests"

# presto
alias presto-cli="/usr/local/Cellar/prestodb/0.230_1/libexec/presto-cli-0.230-executable.jar"

# terrform
alias tf="/usr/local/bin/terraform"

# delete .DS_Store
alias nomore='find ./ -iname .DS_Store -delete'

# kubectl
alias kc="kubectl"


# if [ -f $(brew --prefix)/etc/bash_completion ]; then
#   source $(brew --prefix)/etc/bash_completion.d
# fi

# source $(brew --prefix)/etc/bash_completion.d/git-prompt.sh

# zsh-syntax-highlighting
# source /usr/local/share/zsh-syntax-highlighting/zsh-syntax-highlighting.zsh

# display timestamp for each command
RPROMPT='[%D{%L:%M:%S %p}]'

# autosuggestion color
ZSH_AUTOSUGGEST_HIGHLIGHT_STYLE='fg=60'

# golang
export GOROOT="$(brew --prefix golang)/libexec"
export GOPATH=$HOME/project/go
export PATH="${GOPATH}/bin:${PATH}" 

# rbenv 
eval "$(rbenv init -)"

# Java home
export JAVA_HOME="/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home"

# Hook direnv
# eval "$(direnv hook zsh)"

# Apache Airflow
# export AIRFLOW_HOME=~/airflow

# Kwest prod config for cli
export KWEST_CONFIG_PATH=/Users/zheng.lu/hunt_cert_csr

# Kwest dev config for cli
# export KWEST_CONFIG_PATH=/Users/zheng.lu/hunt_cert_csr/dev

# Kwest test config for cli
# export KWEST_CONFIG_PATH=/Users/zheng.lu/hunt_cert_csr/test


# kubernetes autocompletion
# source <(kubectl completion zsh)

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
