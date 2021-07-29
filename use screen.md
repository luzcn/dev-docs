### start a new session with name
#### https://stackoverflow.com/questions/3202111/how-to-assign-name-for-a-screen 

```
screen -S <session name, e.g. myHappyProcess>
```
### resume or create a session with the specific name
#### http://aperiodic.net/screen/sessionnames
```
screen -xR <session name>
```

### to detach from a session
```
Ctrl+a, then Ctrl+d
```

### list all sessions for current user
```
screen -ls
# OR: screen -list
# screen sessions are saved in the <pid>.<screen_name> format. 
```

  

### quit a screen session
#### https://stackoverflow.com/questions/1509677/kill-detached-screen-session
```
screen -X -S <session id> quit
```
