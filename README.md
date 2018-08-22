[![license](https://img.shields.io/github/license/mashape/apistatus.svg) ](LICENSE)

# Mapcha
A simple captcha plugin, which uses maps as the captcha. The spigot thread can be found [here](https://www.spigotmc.org/resources/mapcha.51630/).

### Default config
```yml
# The amount of tries the player will get to solve the captcha.
tries: 3

# The time limit in seconds the player has to solve the captcha.
time_limit: 10

messages:
  # The success message the player receive after they solve the captcha.
  success: Captcha &asolved!
  
  # The retry message the player receive after they fail one of their tries.
  retry: Captcha &efailed, &rplease try again. ({CURRENT}/{MAX})
  
  # The fail message the user receive after they fail the captcha.
  fail: Captcha &cfailed!
```
