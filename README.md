# Pokemon Java

A program to simulate Pokemon battles<sup id="a1">[1](#f1)</sup>

```
                                   ."-,.__
                                   `.     `.  ,
                                .--'  .._,'"-' `.
                               .    .'         `'
                               `.   /          ,'
                                 `  '--.   ,-"'
                                  `"`   |  \
                                     -. \, |
                                      `--Y.'      ___.
                                           \     L._, \
                                 _.,        `.   <  <\                _
                               ,' '           `, `.   | \            ( `
                            ../, `.            `  |    .\`.           \ \_
                           ,' ,..  .           _.,'    ||\l            )  '".
                          , ,'   \           ,'.-.`-._,'  |           .  _._`.
                        ,' /      \ \        `' ' `--/   | \          / /   ..\
                      .'  /        \ .         |\__ - _ ,'` `        / /     `.`.
                      |  '          ..         `-...-"  |  `-'      / /        . `.
                      | /           |L__           |    |          / /          `. `.
                     , /            .   .          |    |         / /             ` `
                    / /          ,. ,`._ `-_       |    |  _   ,-' /               ` \
                   / .           \"`_/. `-_ \_,.  ,'    +-' `-'  _,        ..,-.    \`.
                  .  '         .-f    ,'   `    '.       \__.---'     _   .'   '     \ \
                  ' /          `.'    l     .' /          \..      ,_|/   `.  ,'`     L`
                  |'      _.-""` `.    \ _,'  `            \ `.___`.'"`-.  , |   |    | \
                  ||    ,'      `. `.   '       _,...._        `  |    `/ '  |   '     .|
                  ||  ,'          `. ;.,.---' ,'       `.   `.. `-'  .-' /_ .'    ;_   ||
                  || '              V      / /           `   | `   ,'   ,' '.    !  `. ||
                  ||/            _,-------7 '              . |  `-'    l         /    `||
                  . |          ,' .-   ,' ||               | .-.        `.      .'     ||
                   `'        ,'    `".'    |               |    `.        '. -.'       `'
                            /      ,'      |               |,'    \-.._,.'/'
                            .     /        .               .       \    .''
                          .`.    |         `.             /         :_,'.'
                            \ `...\   _     ,'-.        .'         /_.-'
                             `-.__ `,  `'   .  _.>----''.  _  __  /
                                  .'        /"'          |  "'   '_
                                 /_|.-'\ ,".             '.'`__'-( \
                                   / ,"'"\,'               `/  `-.|" mh
```

## Instructions

1 vs 1 Pokemon battle
```
javac *.java && java -ea PokemonBattle
```

1 vs 1 Pokemon battle with pre-selected pokemon
```
javac *.java && java -ea PokemonBattle charizard venusaur
```

1 vs 1 Pokemon battle simluated
```
javac *.java && java -ea PokemonBattle charizard venusaur skip
```

Trainer Battle
```
javac *.java && java -ea TrainerBattle
```

Elite Four Battle
```
javac *.java && java -ea EliteFour
```

## Extras

* When prompted to select a Pokemon, use "custom" to be prompted to enter custom stats and attacks
* Use an underscore "\_" in front of a Pokemon's name to maximize its stats.
	* IVs will all be set to 31 (highest possible)
	* EVs will be allotted depending on base stats. A Pokemon has 510 EVs to allocate between its 6 stats, but no single stat can receive more than 252.
		* If base `SPEED` is above a threshold, then 252 EVs will go to the higher of the two attack stats, 252 EVs will be given to `SPEED`, and the remaining 6 EVs will be given to `HP`.
		* If base `SPEED` is below a threshold, then 252 EVs will go to the higher of the two attack stats, and 252 are distributed to `HP`, `DEFENCE`, & `SPECIAL_DEFENCE` depending on the proportion that would best balance between them e.g. more EVs go to `DEFENCE` if it is much lower than `HP` and `SPECIAL_DEFENCE`. We technically could allocate 258 EVs between the three defensive stats, but to prevent one of the defensive stats from getting more than 252 EVs (which would happen in a severe imbalance), 6 EVs are given to `SPEED`.
	* Nature (which affects which stat has a 1.1 multiplier and which has a 0.9 multiplier) will be set depending on base stats. As a general rule, HP cannot be affected by nature.
		* If base `SPEED` is above a threshold, attenuate the lower of the two attack stats and accentuate `SPEED`.
		* If base `SPEED` is below a threshold, attenuate the lower of the two attack stats and accentuate the higher of the two attack stats.

## Notes

* If a Pokemon has multiple attacks, it will automatically select the "best" attack against a given opponent.
* The difficulty for the TrainerAI is based on simulated battles that it runs between all the Pokemon. Pokemon that win more frequently appear when higher difficulty is chosen. If you select a difficulty above the maximum, The Pokemon Master will challenge you - this trainer always chooses the Pokemon that wins most frequently against your Pokemon.

## Reference
<b id="f1">1)</b> ASCII art by [Maija Haavisto](https://www.fiikus.net/?pokedex) [↩](#a1)

