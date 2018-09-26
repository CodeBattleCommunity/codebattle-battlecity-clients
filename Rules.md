# CodeBattle - BattleCity
## Правила игры

Каждый участник разрабатывает своего бота для танчиков, который обыграет других ботов по очкам. 

За основу для написания бота могут быть использованы примеры реализованных клиентов (описаны ниже).

### Ход
Игра пошаговая, каждую такт (секунду) сервер посылает твоему клиенту (боту) состояние обновленного поля на текущий момент и ожидает ответа команды танку. 
За следующий такт игрок должен успеть дать команду танку. 
Если не успел — танк остается на месте.

### Поле
Поле поделено на клетки. В каждой клетке может находится один объект:
 
- ![](https://github.com/IzhevskCodeBattle/codebattle-game-battlecity/raw/master/games/battlecity/src/main/webapp/resources/sprite/battlecity/tank_left.png)
 Танк игрока (свой)
 
- ![](https://github.com/IzhevskCodeBattle/codebattle-game-battlecity/raw/master/games/battlecity/src/main/webapp/resources/sprite/battlecity/other_tank_left.png)
Вражеский танк

- ![](https://github.com/IzhevskCodeBattle/codebattle-game-battlecity/raw/master/games/battlecity/src/main/webapp/resources/sprite/battlecity/ai_tank_left.png) 
AI танк (если режим игры с AI)
 
- ![](https://github.com/IzhevskCodeBattle/codebattle-game-battlecity/raw/master/games/battlecity/src/main/webapp/resources/sprite/battlecity/battle_wall.png)
Неразрушаемая стена

- ![](https://github.com/IzhevskCodeBattle/codebattle-game-battlecity/raw/master/games/battlecity/src/main/webapp/resources/sprite/battlecity/construction.png) 
Разрушаемая стена 

- ![](https://github.com/IzhevskCodeBattle/codebattle-game-battlecity/raw/master/games/battlecity/src/main/webapp/resources/sprite/battlecity/bullet.png)
Снаряд, выпущенный танком. Снаряд при попадании в танк отнимает 1 жизнь. 
Если запас жизней танка = 0 - танк погибает. Каждый танк начинает игру с **1** жизнью.

- ![](https://github.com/IzhevskCodeBattle/codebattle-game-battlecity/raw/master/games/battlecity/src/main/webapp/resources/sprite/battlecity/hedgehog.png) 
Ёж (hedgehog - неразрушаемое препятствие через которое не может проехать танк, 
но может через него стрелять, а также может быть подбит другим танком)
 
- Препятствия :
  - ![](https://github.com/IzhevskCodeBattle/codebattle-game-battlecity/raw/master/games/battlecity/src/main/webapp/resources/sprite/battlecity/sand.png)
  Песок и ![](https://github.com/IzhevskCodeBattle/codebattle-game-battlecity/raw/master/games/battlecity/src/main/webapp/resources/sprite/battlecity/moat_horizontal.png) 
  Ров - замедляющие ход танка на 1 ход. 
  - ![](https://github.com/IzhevskCodeBattle/codebattle-game-battlecity/raw/master/games/battlecity/src/main/webapp/resources/sprite/battlecity/bog.png)
  Болото - если танк оказался в болоте он больше **не может** двигаться по карте до момента гибели)
- ![](https://github.com/IzhevskCodeBattle/codebattle-game-battlecity/raw/master/games/battlecity/src/main/webapp/resources/sprite/battlecity/bonus_ammo.png)
 Доп. патроны (увеличивающие запас доступных снарядов танка на **5**. 
 Начальное кол-во патронов танка - будет определено на месте проведения мероприятия. 
 После гибели танка - начальный запас боеприпасов восстанавливается)
- ![](https://github.com/IzhevskCodeBattle/codebattle-game-battlecity/raw/master/games/battlecity/src/main/webapp/resources/sprite/battlecity/medkit.png)
Доп. жизнь (увеличивает запас жизней танка на **1**)
- ![](https://github.com/IzhevskCodeBattle/codebattle-game-battlecity/raw/master/games/battlecity/src/main/webapp/resources/sprite/battlecity/worm_hole.png)
Телепорт - при входе в телепорт танк будет перемещен в случайный другой телепорт на карте 
(если выход из телепорт не занят других объектом). 


### Команды 
- *UP*, *DOWN*, *LEFT*, *RIGHT* – приводят к повороту и передвижению танка в заданном направлении на 1 клетку
- *ACT* - выстрел снаряда. Команды движения можно комбинировать с командами выстрела, 
разделяя их через запятую – это значит что за один такт игры будет выстрел, а потом движение (LEFT, ACT) 
или наоборот (ACT, LEFT).

### Порядок начисления очков
- Очки начисляются за уничтожение вражеского танка
- В случае гибели своего танка происходит уменьшение количества очков игрока

Побеждает игрок набравший максимальное количество очков к концу игры. 
Длительность игры и количество начисления очков будут определены во время мероприятия ведущим.

### Особенности механики игры

##### Снаряды и препятствия
- Танк имеет скорость движения - 1 клетка за такт. Снаряд ('•') - имеет скорость движения 2 клетки за такт. 
Команды танка будут обработаны раньше чем пуля совершит движение (в такте), таким образом есть возможность увернуться
от пули летящей в танка при своевременном движении танка.

- Снаряд может пролетать через препятствия на карте (песок, ров, болото, телепорт, еж) - 
при этом на карте будет отображаться препятствие при одновременным нахождением пули и препятствия на одной клетке.
- В случае столкновения двух снарядов происхоит взаимное уничтожение снарядов
- В случае нахождения на одной клетке танка и препятствия - на карте будет отрисован танк.
- В случае нахождения танка в болоте - танк **не может** совершать движение. 
При команде движения танк будет разворачиваться в указанном направлении, но останется в изначальной клетке. 
Болото не влияет на возможность совершения выстрелов.

##### Телепорт
- Танк может находиться на клетке с телепортом
в случае выхода из него. Для того чтобы воспользоваться телепортом нужно находиться на соседней клетке
и совершить движение в сторону телепорта.

#### Выпадение бонусов
- Бонусы доп. патроны и доп. жизни появляются в случайном месте на карте и имеют ограниченное время жизни.
- Через определенное кол-во тактов, если бонус не был взят никем - бонус пропадает.

### Примеры клиентов (ботов)
Исходный код клиентов - https://github.com/IzhevskCodeBattle/codebattle-battlecity-clients

##### Доступные языки: 
- Java 
- JavaScript
- C#
- Python (3+)
- Kotlin
- C++ 

#### Элементы карты
- NONE(' ') - незанятая клетка
- BATTLE_WALL('☼') - неразрушаемая стена
- BANG('Ѡ') - взрыв снаряда
- BONUS_AMMO('◊') - бонусные патроны (+5)
- MEDKIT('☺') - доп. жизнь (+1)
- CONSTRUCTION('╬', 3) - разрушаемая стена (нужно 3 попадания для разрушения)
- CONSTRUCTION_DESTROYED_DOWN('╩', 2) - разрушаемая стена (нужно 2 попадания для разрушения)
- CONSTRUCTION_DESTROYED_UP('╦', 2) - разрушаемая стена (нужно 2 попадания для разрушения)
- CONSTRUCTION_DESTROYED_LEFT('╠', 2) - разрушаемая стена (нужно 2 попадания для разрушения)
- CONSTRUCTION_DESTROYED_RIGHT('╣', 2) - разрушаемая стена (нужно 2 попадания для разрушения)
- CONSTRUCTION_DESTROYED_DOWN_TWICE('╨', 1) - разрушаемая стена (нужно 1 попадание для разрушения)
- CONSTRUCTION_DESTROYED_UP_TWICE('╥', 1) - разрушаемая стена (нужно 1 попадание для разрушения)
- CONSTRUCTION_DESTROYED_LEFT_TWICE('╞', 1) - разрушаемая стена (нужно 1 попадание для разрушения)
- CONSTRUCTION_DESTROYED_RIGHT_TWICE('╡', 1) - разрушаемая стена (нужно 1 попадание для разрушения)
- CONSTRUCTION_DESTROYED_LEFT_RIGHT('│', 1) - разрушаемая стена (нужно 1 попадание для разрушения)
- CONSTRUCTION_DESTROYED_UP_DOWN('─', 1) - разрушаемая стена (нужно 1 попадание для разрушения)
- CONSTRUCTION_DESTROYED_UP_LEFT('┌', 1) - разрушаемая стена (нужно 1 попадание для разрушения)
- CONSTRUCTION_DESTROYED_RIGHT_UP('┐', 1) - разрушаемая стена (нужно 1 попадание для разрушения)
- CONSTRUCTION_DESTROYED_DOWN_LEFT('└', 1) - разрушаемая стена (нужно 1 попадание для разрушения)
- CONSTRUCTION_DESTROYED_DOWN_RIGHT('┘', 1) - разрушаемая стена (нужно 1 попадание для разрушения)
- CONSTRUCTION_DESTROYED(' ', 0) - разрушаемая стена (уничтоженная)
- HEDGEHOG('ͱ') - еж
- BULLET('•') - выпущенные снаряд
- TANK_UP('▲') - танк игрока, повернут наверх 
- TANK_RIGHT('►') - танк игрока, повернут направо 
- TANK_DOWN('▼') - танк игрока, повернут вниз
- TANK_LEFT('◄') - танк игрока, повернут налево
- OTHER_TANK_UP('˄') - танк игрока соперника, повернут наверх 
- OTHER_TANK_RIGHT('˃') - танк игрока соперника, повернут направо 
- OTHER_TANK_DOWN('˅') - танк игрока соперника, повернут вниз
- OTHER_TANK_LEFT('˂') - танк игрока соперника, повернут налево
- AI_TANK_UP('?') - танк AI, повернут вверх
- AI_TANK_RIGHT('»') - танк AI, повернут направо
- AI_TANK_DOWN('¿') - танк AI, повернут вних
- AI_TANK_LEFT('«') - танк AI, повернут налево
- WORM_HOLE('ʘ') - телепорт
- BOG('@') - болото (танк застревает и не может перемещаться по карте)
- SAND('□') - песок (танку нужна 2 хода для пересечения клетки с песком)
- MOAT_HORIZONTAL('=') - ров (танку нужна 2 хода для пересечения клетки со рвом)
- MOAT_VERTICAL('‖') - ров (танку нужна 2 хода для пересечения клетки со рвом)
