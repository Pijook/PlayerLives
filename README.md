# Player Lives

Plugin for managing player lives on minecraft servers
General version 1.17.1

## Commands

- `/playerlives` - Command for managing and modifying player lives
- `/resurrect` - Command to resurrect and force resurrect other players

## Permissions

- `playerlives.check.self` - Permission to check your own lives
- `playerlives.check.other` - Permission to check other's lives
- `playerlives.add` - Permission to add player lives
- `playerlives.take` - Permission to take player lives
- `playerlives.set` - Permission to set player lives
- `playerlives.reload` - Permission to reload plugin (config.yml and lang.yml)
- `playerlives.resurrect` - Permission to resurrect other players
- `playerlives.forceresurrect` - Permission to resurrect other players bypassing lives limits

## Configuration files

- [General settings for plugin](src/main/resources/config.yml) - General settings for plugin
- [Texts that plugin will use](src/main/resources/lang.yml) - Texts that plugin will use
- [Stored data about players](src/main/resources/players.yml) - Stored data about players

## Placeholders

- %playerlives_currentAmount% - Returns current amount of player lives
