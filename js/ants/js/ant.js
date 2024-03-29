var ANT = ANT || {};
window.ANT = ANT;

ANT.addEventListener = (function() {
    if (window.addEventListener) {
        return function(obj, event, funct, evtCapturing) {
            obj.addEventListener(event, funct, evtCapturing);
        };
    } else if (window.attachEvent) {
        return function(obj, event, funct) {
            obj.attachEvent("on" + event, funct);
        };
    }
})();

ANT.removeEventListener = (function() {
    if (window.removeEventListener) {
        return function(obj, event, funct, evtCapturing) {
            obj.removeEventListener(event, funct, evtCapturing);
        };
    } else if (window.detachEvent) {
        return function(obj, event, funct) {
            obj.detachEvent("on" + event, funct);
        };
    }
})();

ANT.Board = ANT.Board || (function() {
    var instanceNumber = 0;

    function getClientWidth() {
        return window.innerWidth;
    }

    function getClientHeight() {
        return window.innerHeight;
    }

    // constructor
    return function(inputConfig) {
        const MAX_BOARD_COLS = 1500,
            MAX_BOARD_ROWS = 1500,
            BLOCK_WIDTH = 8,
            BLOCK_HEIGHT = 8,
            NUM_ANTS = 5,
            NUM_FOODS = 50,
            NUM_TEAMS = 2;
        var me = this,
            myId = instanceNumber++,
            config = inputConfig || {},
            foods = [],
            ants = [],
            myKeyListener,
            htmlContainer, htmlBoard;

        me.grid = [];

        function createBoardElements() {
            htmlBoard = document.createElement("div");
            htmlBoard.setAttribute("id", "playingField");
            htmlBoard.className = "ant-playing-field";

            ANT.addEventListener(htmlBoard, "click", function() {
                htmlContainer.focus();
            }, false);

            htmlLengthPanel = document.createElement("div");
            htmlLengthPanel.className = "ant-panel-component";
            htmlLengthPanel.innerHTML = "Length: 1";

            ANT.addEventListener( htmlContainer, "keyup", function(evt) {
                if (!evt) var evt = window.event;
                evt.cancelBubble = true;
                if (evt.stopPropagation) { evt.stopPropagation(); }
                if (evt.preventDefault) { evt.preventDefault(); }
                return false;
            }, false);

            htmlContainer.className = "ant-game-container";
            htmlContainer.appendChild(htmlBoard);
        }

        function maxBoardWidth() {
            return MAX_BOARD_COLS * me.getBlockWidth();
        }

        function maxBoardHeight() {
            return MAX_BOARD_ROWS * me.getBlockHeight();
        }

        // public
        me.getRandPos = function() {
            var maxRows = me.grid.length - 2;
            var maxCols = me.grid[0].length - 2;
            return [Math.floor(Math.random() * maxCols + 1), Math.floor(Math.random() * maxRows + 1)];
        }

        me.getTarget = function(col, row) {
            var shortest = [];
            var shortestDists = [];
            const MAX_DISTS = 5;
            for (var i = 0; i < foods.length; i++) {
                if (foods[i].isTaken()) {
                    continue;
                }
                dist = Math.abs(col - foods[i].getCol()) + Math.abs(row - foods[i].getRow())
                if (shortest.length < MAX_DISTS) {
                    shortest.push(i);
                    shortestDists.push(dist);
                } else {
                    for (var j = 0; j < shortest.length; j++) {
                        if (dist < shortestDists[j]) {
                            shortest[j] = i;
                            shortestDists[j] = dist;
                        }
                    }
                }
            }
            if (shortest.length === 0) {
                return undefined;
            }
            var rand = Math.floor(Math.random() * shortest.length);
            return foods[shortest[rand]];
        };

        me.score = function(team) {
            me.scores[team]++;
        }

        me.setBoardContainer = function(myContainer) {
            if (typeof myContainer === "string") {
                myContainer = document.getElementById(myContainer);
            }
            if (myContainer === htmlContainer) { return; }

            htmlContainer = myContainer;
            htmlBoard = null;

            me.setupPlayingField();
        };

        me.getBoardContainer = function() {
            return htmlContainer;
        };

        me.getBlockWidth = function() {
            return BLOCK_WIDTH;
        };

        me.getBlockHeight = function() {
            return BLOCK_HEIGHT;
        };

        me.setupPlayingField = function () {
            if (!htmlBoard) { createBoardElements(); }

            var cTop = config.top;
            var cLeft = config.left;
            var cWidth = config.width;
            var cHeight = config.height;

            var wEdgeSpace = me.getBlockWidth() * 2 + (cWidth % me.getBlockWidth());
            var fWidth = Math.min(maxBoardWidth() - wEdgeSpace, cWidth - wEdgeSpace);
            var hEdgeSpace = me.getBlockHeight() * 3 + (cHeight % me.getBlockHeight());
            var fHeight = Math.min(maxBoardHeight() - hEdgeSpace, cHeight - hEdgeSpace);

            htmlContainer.style.left = cLeft + "px";
            htmlContainer.style.top = cTop + "px";
            htmlContainer.style.width = cWidth + "px";
            htmlContainer.style.height = cHeight + "px";
            htmlBoard.style.left = me.getBlockWidth() + "px";
            htmlBoard.style.top  = me.getBlockHeight() + "px";
            htmlBoard.style.width = fWidth + "px";
            htmlBoard.style.height = fHeight + "px";

            var bottomPanelHeight = hEdgeSpace - me.getBlockHeight();
            var pLabelTop = me.getBlockHeight() + fHeight + Math.round((bottomPanelHeight - 30)/2) + "px";

            me.grid = [];
            var numBoardCols = fWidth / me.getBlockWidth() + 2;
            var numBoardRows = fHeight / me.getBlockHeight() + 2;

            for (var row = 0; row < numBoardRows; row++) {
                me.grid[row] = [];
                for (var col = 0; col < numBoardCols; col++) {
                    me.grid[row][col] = 0;
                }
            }

            me.scores = []
            for (var i = 0; i < NUM_TEAMS; i++) {
                me.scores[i] = 0;
            }

            for (var i = 0; i < NUM_ANTS; i++) {
                for (var j = 0; j < NUM_TEAMS; j++) {
                    var pos = me.getRandPos();
                    ants[i] = new ANT.Ant({board: me, team: j, startCol: pos[0], startRow: pos[1]});
                }
            }
            for (var i = 0; i < NUM_FOODS; i++) {
                foods[i] = new ANT.Food({board: me});
                foods[i].release();
            }
        };

        // init
        config.top = (typeof config.top === "undefined") ? 0 : config.top;
        config.left = (typeof config.left === "undefined") ? 0 : config.left;
        config.width = (typeof config.width === "undefined") ? 1200 : config.width;
        config.height = (typeof config.height === "undefined") ? 800 : config.height;

        if (config.boardContainer) {
            me.setBoardContainer(config.boardContainer);
        }
    };
})(); // ANT.Board

ANT.Ant = ANT.Ant || (function() {
    var instanceNumber = 0;
    var AntBlock = function() {
        this.html = null;
        this.htmlStyle = null;
        this.row = -1;
        this.col = -1;
        this.xPos = -1000;
        this.yPos = -1000;
        this.next = null;
        this.prev = null;
    };

    // constructor
    return function(config) {
        if (!config || !config.board) { return; }
        const DIR_UP = 0,
              DIR_RIGHT = 1,
              DIR_DOWN = 2,
              DIR_LEFT = 3;
        var me = this,
            board = config.board,
            myId = instanceNumber++,
            growthIncr = 5,
            currDir = DIR_RIGHT,
            colShift = [0, 1, 0, -1],
            rowShift = [-1, 0, 1, 0],
            xPosShift = [],
            yPosShift = [],
            antSpeed = 4,
            targetFood,
            gotFood = false,
            team;

            function setModeListener(mode, speed) {
                document.getElementById(mode).addEventListener('click', function () { antSpeed = speed; });
            }

        me.antBody = {};
        me.antBody["b0"] = new AntBlock();
        me.antBody["b0"].col = config.startCol || 1;
        me.antBody["b0"].row = config.startRow || 1;
        me.antBody["b0"].yPos = me.antBody["b0"].row * board.getBlockHeight();
        me.antBody["b0"].xPos = me.antBody["b0"].col * board.getBlockWidth();
        me.antBody["b0"].html = createAntElement();
        me.antBody["b0"].htmlStyle = me.antBody["b0"].html.style;
        board.getBoardContainer().appendChild(me.antBody["b0"].html);
        me.antBody["b0"].html.style.left = me.antBody["b0"].xPos + "px";
        me.antBody["b0"].html.style.top = me.antBody["b0"].yPos + "px";
        me.antBody["b0"].next = me.antBody["b0"];
        me.antBody["b0"].prev = me.antBody["b0"];
        me.team = config.team || 0;

        me.antPos = me.antBody["b0"];
        me.antPos.html.id = "ant-anthead-alive";
        me.antPos.html.className += " ant-antbody-alive";
        setTimeout(function() { me.go(); }, antSpeed);

        function createAntElement() {
            var tempNode = document.createElement("div");
            tempNode.className = "ant-antbody-block";
            tempNode.style.left = "-1000px";
            tempNode.style.top = "-1000px";
            tempNode.style.width = board.getBlockWidth() + "px";
            tempNode.style.height = board.getBlockHeight() + "px";
            return tempNode;
        }

        function createBlocks() {
            var tempBlock = new AntBlock();
            var tempNode = createAntElement();
            tempBlock.html = tempNode;
            board.getBoardContainer().appendChild(tempBlock.html);
        }

        me.go = function() {
            var oldCol = me.antPos.col
            var oldRow = me.antPos.row
            if (!me.gotFood && (me.targetFood === undefined || me.targetFood.isTaken())) {
                me.targetFood = board.getTarget(oldCol, oldRow);
                if (me.targetFood === undefined) {
                    return;
                }
            }

            var food = me.targetFood;
            var targetCol = food.getCol();
            var targetRow = food.getRow();
            var baseCol = 1;
            var baseRow = 1;
            if (me.team === 1) {
                baseCol = board.grid[0].length - 2;
                baseRow = board.grid.length - 2;
            }
            if (me.gotFood) {
                targetCol = baseCol;
                targetRow = baseRow;
                if (oldCol == targetCol && oldRow == targetRow) {
                    // found base
                    food.release();
                    me.targetFood = undefined;
                    me.gotFood = false
                    board.score(me.team);
                }
            } else {
                if (oldCol == targetCol && oldRow == targetRow) {
                    // found food
                    food.take();
                    me.gotFood = true
                    targetCol = baseCol;
                    targetRow = baseRow;
                }
            }

            var dist_x = Math.abs(oldCol - targetCol);
            var dist_y = Math.abs(oldRow - targetRow);
            if (dist_x > dist_y) {
                if (oldCol > targetCol) {
                    currDir = DIR_LEFT;
                } else if (oldCol < targetCol) {
                    currDir = DIR_RIGHT;
                }
            } else {
                if (oldRow > targetRow) {
                    currDir = DIR_UP;
                } else if (oldRow < targetRow) {
                    currDir = DIR_DOWN;
                }
            }

            while (true) {
                var newCol = oldCol + colShift[currDir];
                var newRow = oldRow + rowShift[currDir];
                var newxPos = me.antPos.xPos + xPosShift[currDir];
                var newyPos = me.antPos.yPos + yPosShift[currDir];

                if (currDir === DIR_DOWN && newRow >= board.grid.length - 1) {
                    currDir = DIR_LEFT;
                } else if (currDir === DIR_LEFT && newCol === 0) {
                    currDir = DIR_UP;
                } else if (currDir === DIR_UP && newRow === 0) {
                    currDir = DIR_RIGHT;
                } else if (currDir === DIR_RIGHT && newCol >= board.grid[0].length - 1) {
                    currDir = DIR_DOWN;
                } else {
                    me.antPos.col = newCol;
                    me.antPos.row = newRow;
                    me.antPos.xPos = newxPos;
                    me.antPos.yPos = newyPos;
                    me.antPos.htmlStyle.left = me.antPos.xPos + "px";
                    me.antPos.htmlStyle.top = me.antPos.yPos + "px";
                    break;
                }
            }
            
            setTimeout(function() { me.go(); }, antSpeed);
        };

        // init
        createBlocks();
        xPosShift[0] = 0;
        xPosShift[1] = board.getBlockWidth();
        xPosShift[2] = 0;
        xPosShift[3] = -1 * board.getBlockWidth();

        yPosShift[0] = -1 * board.getBlockHeight();
        yPosShift[1] = 0;
        yPosShift[2] = board.getBlockHeight();
        yPosShift[3] = 0;
    };
})(); // ANT.Ant

ANT.Food = ANT.Food || (function() {
    var instanceNumber = 0;

    // constructor
    return function(config) {
        if (!config||!config.board) { return; }

        var me = this;
        var board = config.board;
        var col, row;
        var myId = instanceNumber++;
        var GRID_FOOD_VALUE = -1;
        var taken = false;

        var htmlFood = document.createElement("div");
        htmlFood.setAttribute("id", "ant-food-" + myId);
        htmlFood.className = "ant-food-block";
        htmlFood.style.width = board.getBlockWidth() + "px";
        htmlFood.style.height = board.getBlockHeight() + "px";
        htmlFood.style.left = "-1000px";
        htmlFood.style.top = "-1000px";
        board.getBoardContainer().appendChild(htmlFood);

        // public
        me.getCol = function() {
            return col;
        };

        me.getRow = function() {
            return row;
        };

        me.take = function() {
            board.grid[row][col] = 0;
            htmlFood.className = "ant-playing-field";
            col = 0;
            row = 0;
        }

        me.isTaken = function() {
            return col === 0 && row === 0;
        }

        me.release = function() {
            // clear first
            if (board.grid[row] && board.grid[row][col] === GRID_FOOD_VALUE) {
                board.grid[row][col] = 0;
            }

            do {
                newCoord = board.getRandPos();
                newCol = newCoord[0];
                newRow = newCoord[1];
            } while (board.grid[newRow][newCol] !== 0)

            col = newCol;
            row = newRow;
            board.grid[row][col] = GRID_FOOD_VALUE;
            htmlFood.style.top = row * board.getBlockHeight() + "px";
            htmlFood.style.left = col * board.getBlockWidth() + "px";
            htmlFood.className = "ant-food-block";
        };
    };
})(); // ANT.Food
