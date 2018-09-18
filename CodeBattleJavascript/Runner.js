/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2016 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
var log = function(string) {
    console.log(string);
    if (!!printBoardOnTextArea) {
        printLogOnTextArea(string);
    }
};

var printArray = function (array) {
   var result = [];
   for (var index in array) {
       var element = array[index];
       result.push(element.toString());
   }
   return "[" + result + "]";
};
var util = require('util');

// to use for local server
//var hostIP = '192.168.1.1';
var hostIP = '127.0.0.1';

// to use for codenjoy.com server
// var hostIP = 'tetrisj.jvmhost.net';

// this is your email
var userName = 'test@mail.ru';
// you can get this code after registration on the server with your email
// http://server-ip:8080/codenjoy-contest/board/player/your@email.com?code=12345678901234567890
var code = '1351323692637632922';

var protocol = 'WS';

var processBoard = function(boardString) {
    var board = new Board(boardString);
        if (!!printBoardOnTextArea) {
        printBoardOnTextArea(board.boardAsString());
    }

    var logMessage = board + "\n\n";
        console.log("board === ");
        console.log(board+"");
  console.log("board ===| ");
    var answer = new DirectionSolver(board).get().toString();
	logMessage += "Answer: " + answer + "\n";
    logMessage += "-----------------------------------\n";
	
	log(logMessage);

    return answer;
};

if (protocol == 'HTTP') {
    // unsupported
} else {
    var port = 8080;
    if (hostIP == 'tetrisj.jvmhost.net') {
        port = 12270;
    }
    var server = 'ws://' + hostIP + ':' + port + '/codenjoy-contest/ws';
    var WSocket = require('ws');
    var ws = new WSocket(server + '?user=' + userName + '&code=' + code);

    ws.on('open', function() {
        log('Opened');
    });

    ws.on('close', function() {
        log('Closed');
    });

    ws.on('message', function(message) {
        var pattern = new RegExp(/^board=(.*)$/);
        var parameters = message.match(pattern);
        var boardString = parameters[1];
        var answer = processBoard(boardString);
        ws.send(answer);
    });

    log('Web socket client running at ' + server);
}

var Element = {

  CONSTRUCTION : '╬',

  CONSTRUCTION_DESTROYED_DOWN : '╩',
  CONSTRUCTION_DESTROYED_UP : '╦',
  CONSTRUCTION_DESTROYED_LEFT : '╠',
  CONSTRUCTION_DESTROYED_RIGHT : '╣',

  CONSTRUCTION_DESTROYED_DOWN_TWICE : '╨',
  CONSTRUCTION_DESTROYED_UP_TWICE : '╥',
  CONSTRUCTION_DESTROYED_LEFT_TWICE : '╞',
  CONSTRUCTION_DESTROYED_RIGHT_TWICE : '╡',

  CONSTRUCTION_DESTROYED_LEFT_RIGHT : '│',
  CONSTRUCTION_DESTROYED_UP_DOWN : '─',

  CONSTRUCTION_DESTROYED_UP_LEFT : '┌',
  CONSTRUCTION_DESTROYED_UP_RIGHT : '┐',
  CONSTRUCTION_DESTROYED_DOWN_LEFT : '└',
  CONSTRUCTION_DESTROYED_DOWN_RIGHT : '┘',

  BULLET : '•',

  AI_TANK_UP : '?',
  AI_TANK_RIGHT : '»',
  AI_TANK_DOWN : '¿',
  AI_TANK_LEFT : '«',

  OTHER_TANK_UP : '˄',
  OTHER_TANK_RIGHT : '˃',
  OTHER_TANK_DOWN : '˅',
  OTHER_TANK_LEFT : '˂',

  TANK_UP : '▲',
  TANK_RIGHT : '►',
  TANK_DOWN : '▼',
  TANK_LEFT : '◄',

  WORM_HOLE : 'ʘ',

  BOG : '@',
  SAND : '□',
  MOAT_HORIZONTAL : '=',
  MOAT_VERTICAL : '‖',
  HEDGEHOG : 'ͱ',
  BONUS_AMMO : '◊',
  MEDICINE : '☺',

  BATTLE_WALL : '☼',
  BANG : 'Ѡ',
  NONE : ' '
};

var D = function(index, dx, dy, name){

    var changeX = function(x) {
        return x + dx;
    };

    var changeY = function(y) {
        return y - dy;
    };

    var inverted = function() {
        switch (this) {
            case Direction.UP : return Direction.DOWN;
            case Direction.DOWN : return Direction.UP;
            case Direction.LEFT : return Direction.RIGHT;
            case Direction.RIGHT : return Direction.LEFT;
            default : return Direction.STOP;
        }
    };

    var toString = function() {
        return name;
    };

    return {
        changeX : changeX,

        changeY : changeY,

        inverted : inverted,

        toString : toString,

        getIndex : function() {
            return index;
        }
    };
};

var Direction = {
    UP : D(2, 0, 1, 'up'),                 // you can move
    DOWN : D(3, 0, -1, 'down'),
    LEFT : D(0, -1, 0, 'left'),
    RIGHT : D(1, 1, 0, 'right'),
    ACT : D(4, 0, 0, 'act'),
    STOP : D(5, 0, 0, '')                   // stay
};

Direction.values = function() {
   return [Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.ACT, Direction.STOP];
};

Direction.valueOf = function(index) {
    var directions = Direction.values();
    for (var i in directions) {
        var direction = directions[i];
        if (direction.getIndex() == index) {
             return direction;
        }
    }
    return Direction.STOP;
};

var Point = function (x, y) {
    return {
        equals : function (o) {
            return o.getX() == x && o.getY() == y;
        },

        toString : function() {
            return '[' + x + ',' + y + ']';
        },

        isOutOf : function(boardSize) {
            return x >= boardSize || y >= boardSize || x < 0 || y < 0;
        },

        getX : function() {
            return x;
        },

        getY : function() {
            return y;
        }
    }
};

var pt = function(x, y) {
    return new Point(x, y);
};

var LengthToXY = function(boardSize) {
    function inversionY(y) {
        return boardSize - 1 - y;
    }

    function inversionX(x) {
        return x;
    }

    return {
        getXY : function(length) {
            if (length == -1) {
                return null;
            }
            var x = inversionX(length % boardSize);
            var y = inversionY(Math.ceil(length / boardSize));
            return new Point(x, y);
        },

        getLength : function(x, y) {
            var xx = inversionX(x);
            var yy = inversionY(y);
            return yy*boardSize + xx;
        }
    };
};

var Board = function(board){
    var contains  = function(a, obj) {
        var i = a.length;
        while (i--) {
           if (a[i].equals(obj)) {
               return true;
           }
        }
        return false;
    };

    var removeDuplicates = function(all) {
        var result = [];
        for (var index in all) {
            var point = all[index];
            if (!contains(result, point)) {
                result.push(point);
            }
        }
        return result;
    };

    var boardSize = function() {
        return Math.sqrt(board.length);
    };

    var size = boardSize();
    var xyl = new LengthToXY(size);

  var getPlayerTank = function () {
    var result = [];
    result = result.concat(findAll(Element.TANK_UP));
    result = result.concat(findAll(Element.TANK_DOWN));
    result = result.concat(findAll(Element.TANK_LEFT));
    result = result.concat(findAll(Element.TANK_DOWN));
    return result[0];
  }

  var getOtherPlayersTanks = function () {
    var result = [];
    result = result.concat(findAll(Element.OTHER_TANK_UP));
    result = result.concat(findAll(Element.OTHER_TANK_LEFT));
    result = result.concat(findAll(Element.OTHER_TANK_RIGHT));
    result = result.concat(findAll(Element.OTHER_TANK_DOWN));
    return result;
  }

  var getBotsTanks = function () {
    var result = [];
    result = result.concat(findAll(Element.AI_TANK_UP));
    result = result.concat(findAll(Element.AI_TANK_DOWN));
    result = result.concat(findAll(Element.AI_TANK_LEFT));
    result = result.concat(findAll(Element.AI_TANK_RIGHT));
    return result;
  }

  var getBullets = function () {
    var result = [];
    return result.concat(findAll(Element.BULLET));
  }

  var getWormholes = function () {
    var result = [];
    return result.concat(findAll(Element.WORM_HOLE));
  }

  var getConstructions = function () {
    var result = [];
    return result.concat(findAll(Element.CONSTRUCTION));
  }

  var getDestroyedConstructions = function () {
    var result = [];
    result = result.concat(findAll(Element.CONSTRUCTION_DESTROYED_DOWN));
    result = result.concat(findAll(Element.CONSTRUCTION_DESTROYED_UP));
    result = result.concat(findAll(Element.CONSTRUCTION_DESTROYED_LEFT));
    result = result.concat(findAll(Element.CONSTRUCTION_DESTROYED_RIGHT));
    result = result.concat(findAll(Element.CONSTRUCTION_DESTROYED_LEFT));
    result = result.concat(findAll(Element.CONSTRUCTION_DESTROYED_DOWN_TWICE));
    result = result.concat(findAll(Element.CONSTRUCTION_DESTROYED_UP_TWICE));
    result = result.concat(findAll(Element.CONSTRUCTION_DESTROYED_LEFT_TWICE));
    result = result.concat(findAll(Element.CONSTRUCTION_DESTROYED_RIGHT_TWICE));
    result = result.concat(findAll(Element.CONSTRUCTION_DESTROYED_DOWN_LEFT));
    result = result.concat(findAll(Element.CONSTRUCTION_DESTROYED_DOWN_RIGHT));
    result = result.concat(findAll(Element.CONSTRUCTION_DESTROYED_UP_LEFT));
    result = result.concat(findAll(Element.CONSTRUCTION_DESTROYED_UP_RIGHT));
    result = result.concat(findAll(Element.CONSTRUCTION_DESTROYED_LEFT_RIGHT));
    result = result.concat(findAll(Element.CONSTRUCTION_DESTROYED_UP_DOWN));
    return result;
  }

  var getBogs = function () {
    return findAll(Element.BOG);
  }

  var getSands = function () {
    return findAll(Element.SAND);
  }

  var getMoats = function () {
    var result = [];
    result = result.concat(findAll(Element.MOAT_HORIZONTAL));
    result = result.concat(findAll(Element.MOAT_VERTICAL));
    return result;
  }

  var getHedgehogs = function () {
    return findAll(Element.HEDGEHOG);
  }

  var getWalls = function () {
      return findAll(Element.BATTLE_WALL);
  }

  var getBonusAmmo = function () {
      return findAll(Element.BONUS_AMMO);
  }

  var getMedKits = function () {
      return findAll(Element.MEDICINE);
  }

  var getBarriers = function () {
    var all = getConstructions();
    all = all.concat(getWalls());
    all = all.concat(getConstructions());
    all = all.concat(getDestroyedConstructions());
    all = all.concat(getOtherPlayersTanks());
    all = all.concat(getBotsTanks());
    all = all.concat(getHedgehogs());
    return all;
  }

    var isAt = function(x, y, element) {
       if (pt(x, y).isOutOf(size)) {
           return false;
       }
       return getAt(x, y) == element;
    };

    var getAt = function(x, y) {
		if (pt(x, y).isOutOf(size)) {
           return Element.WALL;
        }
        return board.charAt(xyl.getLength(x, y));
    };

    var boardAsString = function() {
        var result = "";
        for (var i = 0; i < size; i++) {
            result += board.substring(i * size, (i + 1) * size);
            result += "\n";
        }
        return result;
    };

    var toString = function () {

      return util.format("%s\n"+
          "Tank at: %s\n"+
          "Other Tanks at: %s\n"+
          "Bot tanks at: %s\n"+
          "Hedgehogs at: %s\n"+
          "Moats at: %s\n"+
          "Bogs at: %s\n"+
          "Sands at: %s\n",
          boardAsString(),
          getPlayerTank(),
          printArray(getOtherPlayersTanks()),
          printArray(getBotsTanks()),
          printArray(getHedgehogs()),
          printArray(getMoats()),
          printArray(getBogs()),
          printArray(getSands()));
    }


    var findAll = function(element) {
       var result = [];
       for (var i = 0; i < size*size; i++) {
           var point = xyl.getXY(i);
           if (isAt(point.getX(), point.getY(), element)) {
               result.push(point);
           }
       }
       return result;
   };

   var isAnyOfAt = function(x, y, elements) {
       for (var index in elements) {
           var element = elements[index];
           if (isAt(x, y,element)) {
               return true;
           }
       }
       return false;
   };

   var isNear = function(x, y, element) {
       if (pt(x, y).isOutOf(size)) {
           return false;
       }
       return isAt(x + 1, y, element) || // TODO to remove duplicate
			  isAt(x - 1, y, element) || 
			  isAt(x, y + 1, element) || 
			  isAt(x, y - 1, element);
   };

   var isBarrierAt = function(x, y) {
       return contains(getBarriers(), pt(x, y));
   };

   var countNear = function(x, y, element) {
       if (pt(x, y).isOutOf(size)) {
           return 0;
       }
       var count = 0;
       if (isAt(x - 1, y    , element)) count ++; // TODO to remove duplicate
       if (isAt(x + 1, y    , element)) count ++;
       if (isAt(x    , y - 1, element)) count ++;
       if (isAt(x    , y + 1, element)) count ++;
       return count;
   };

   return {
        size : boardSize,
        getPlayerTank : getPlayerTank,
        getOtherPlayersTanks : getOtherPlayersTanks,
        getBotsTanks : getBotsTanks,
        getBullets : getBullets,
        getWormholes : getWormholes,
        getConstructions : getConstructions,
        getDestroyedConstructions : getDestroyedConstructions,
        getBogs : getBogs,
        getSands : getSands,
        getMoats :  getMoats,
        getHedgehogs : getHedgehogs,
        getWalls : getWalls,
        getBonusAmmo : getBonusAmmo,
        getMedKits : getMedKits,
        isAt : isAt,
        boardAsString : boardAsString,
        toString : toString,
        findAll : findAll,
        isAnyOfAt : isAnyOfAt,
        isNear : isNear,
        isBarrierAt : isBarrierAt,
        countNear : countNear,
        getAt : getAt
   };
};

var random = function(n){
    return Math.floor(Math.random()*n);
};

var direction;

var DirectionSolver = function(board){

    return {
        /**
         * @return next hero action
         */
        get : function() {
			      return "("+Direction.DOWN+","+Direction.ACT+")";
        }
    };
};

