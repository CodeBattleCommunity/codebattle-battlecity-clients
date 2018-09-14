# Welcome to CodeBattle: BattleCity
## Как это работает?
Необходимо подключиться из кода бота к серверу через вебсокет по адресу:
`ws://${server_host}:${server_port}/codenjoy-contest/ws?user=${email}&code=${auth_code}`
- `email` - email, указанынй при регистрации
- `code` - автоматически сгенерированный код. Виден в query parameters в адресной строке браузера *(после регистрации)*

После подключения, клиент через вебсокет будет регулярно (каждую секунду) получать строку символов с закодированным состоянием игрового поля. 
Само игровое поле извлекается из ответа сервера регулярным выражением:
```
^board=(.*)$
```

Более подробно с правилами игры можно ознакомиться на [странице](Rules.md)

#### Примеры клиентов (ботов)
- Java - https://github.com/IzhevskCodeBattle/codebattle-battlecity-clients/tree/master/CodeBattleJava 
- JavaScript - https://github.com/IzhevskCodeBattle/codebattle-battlecity-clients/tree/master/CodeBattleJs
- C# - https://github.com/IzhevskCodeBattle/codebattle-battlecity-clients/tree/master/CodeBattleNet
- Python (3.0+) - https://github.com/IzhevskCodeBattle/codebattle-battlecity-clients/tree/master/CodeBattlePython
- Kotlin - https://github.com/IzhevskCodeBattle/codebattle-battlecity-clients/tree/master/CodeBattleKotlin
- C++ - https://github.com/IzhevskCodeBattle/codebattle-battlecity-clients/tree/master/CodeBattleCpp
