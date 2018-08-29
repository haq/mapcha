[![license](https://img.shields.io/github/license/mashape/apistatus.svg) ](LICENSE)

# Mapcha
A simple captcha plugin, which uses maps as the captcha.

### Default config
```yaml
# The amount of tries the player will get to solve the captcha.
tries: 3

# The time limit in seconds the player has to solve the captcha.
time_limit: 10

# The server name to connect to when the user completes a captcha
# Leave empty if you don't want it to do anything
success_server:

messages:
  # The success message the player receive after they solve the captcha.
  success: Captcha &asolved!
  
  # The retry message the player receive after they fail one of their tries.
  retry: Captcha &efailed, &rplease try again. ({CURRENT}/{MAX})
  
  # The fail message the user receive after they fail the captcha.
  fail: Captcha &cfailed!
```
