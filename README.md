[![license](https://img.shields.io/github/license/mashape/apistatus.svg) ](LICENSE)

# mapcha
A simple captcha plugin, which uses maps as the captcha.

### Description
On player join, the player's inventory is cleared, and they are given an empty map. On right-click, the captcha will show. From there the player will have a fixed amount of time to complete the captcha. The player also has a limited number of tries. Once the time has reached or the tries limit has been reached the player is kicked. If the captcha is completed the player's items are returned to them.

### Screenshot
![screenshot](https://i.imgur.com/2gK9mEV.png)

### Permissions
* mapcha.bypass
    * Allows the player to bypass the captcha.

### Default config
```yaml
# Title shown on the map
title: 'Captcha'
# Prefix used for the messages sent.
prefix: '[&aMapcha&r]'

# Commands the player is allowed to type while in captcha mode.
commands:
- /register
- /login

captcha:
  # Allows the player to only complete the captcha once per server restart.
  cache: true

  # Amount of tries the player will get to solve the captcha.
  tries: 3

  # The time limit in seconds the player has to solve the captcha.
  time: 30

# Send the player to a connected server after captcha completion.
server:
  enabled: false

  # The server name.
  name: ''

messages:
  # The success message the player receives after they solve the captcha.
  success: Captcha &asolved!

  # The retry message the player receives after they fail one of their tries.
  retry: Captcha &efailed, &rplease try again. ({CURRENT}/{MAX})

  # The fail message the player receives after they fail the captcha.
  fail: Captcha &cfailed!

styles:
  # Use black(white) background, white(black) foreground.
  invert-color: false
  # Draw points on the captcha image
  points: true
  # Draw lines on the captcha image
  lines: true
```
