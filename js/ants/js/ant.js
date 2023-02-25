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

    function getClientWidth(){
        return window.innerWidth;
    }

    function getClientHeight(){
        return window.innerHeight;
    }

    // constructor
    return function(inputConfig) {
        const MAX_BOARD_COLS = 150,
            MAX_BOARD_ROWS = 150,
            BLOCK_WIDTH = 12,
            BLOCK_HEIGHT = 12,
            NUM_ANTS = 8,
            NUM_FOODS = 6;
        var me = this,
            myId = instanceNumber++,
            config = inputConfig || {},
            foods = [],
            ants = [],
            myKeyListener,
            elmContainer, elmPlayingField;

        me.grid = [];

        function createBoardElements() {
            elmPlayingField = document.createElement("div");
            elmPlayingField.setAttribute("id", "playingField");
            elmPlayingField.className = "ant-playing-field";

            ANT.addEventListener(elmPlayingField, "click", function() {
                elmContainer.focus();
            }, false);

            elmLengthPanel = document.createElement("div");
            elmLengthPanel.className = "ant-panel-component";
            elmLengthPanel.innerHTML = "Length: 1";

            ANT.addEventListener( elmContainer, "keyup", function(evt) {
                if (!evt) var evt = window.event;
                evt.cancelBubble = true;
                if (evt.stopPropagation) { evt.stopPropagation(); }
                if (evt.preventDefault) { evt.preventDefault(); }
                return false;
            }, false);

            elmContainer.className = "ant-game-container";
            elmContainer.appendChild(elmPlayingField);

            for (var i = 0; i < NUM_ANTS; i++) {
                ants[i] = new ANT.Ant({board: me, startRow: 1 + (i * 4), startCol: 1 + (i * 4)});
            }
            for (var i = 0; i < NUM_FOODS; i++) {
                foods[i] = new ANT.Food({board: me});
            }
        }

        function maxBoardWidth() {
            return MAX_BOARD_COLS * me.getBlockWidth();
        }

        function maxBoardHeight() {
            return MAX_BOARD_ROWS * me.getBlockHeight();
        }

        // public
        me.getFoodCoord = function(col, row) {
            var shortest = null;
            var shortestDist;
            for (var i = 0; i < foods.length; i++) {
                dist = Math.abs(col - foods[i].getCol()) + Math.abs(row - foods[i].getRow())
                if (shortest === null || dist < shortestDist) {
                    shortest = i;
                    shortestDist = dist;
                }
            }
            return [foods[shortest].getCol(), foods[shortest].getRow()];
        };

        me.addFood = function(col, row) {
            for (var i = 0; i < foods.length; i++) {
                if (foods[i].getCol() === col && foods[i].getRow() === row) {
                    foods[i].addFood();
                    break;
                }
            }
        }

        me.setBoardContainer = function(myContainer) {
            if (typeof myContainer === "string") {
                myContainer = document.getElementById(myContainer);
            }
            if (myContainer === elmContainer) { return; }

            elmContainer = myContainer;
            elmPlayingField = null;

            me.setupPlayingField();
        };

        me.getBoardContainer = function() {
            return elmContainer;
        };

        me.getBlockWidth = function() {
            return BLOCK_WIDTH;
        };

        me.getBlockHeight = function() {
            return BLOCK_HEIGHT;
        };

        me.setupPlayingField = function () {
            if (!elmPlayingField) { createBoardElements(); }

            var cTop = config.top;
            var cLeft = config.left;
            var cWidth = config.width;
            var cHeight = config.height;

            var wEdgeSpace = me.getBlockWidth() * 2 + (cWidth % me.getBlockWidth());
            var fWidth = Math.min(maxBoardWidth() - wEdgeSpace, cWidth-wEdgeSpace);
            var hEdgeSpace = me.getBlockHeight() * 3 + (cHeight % me.getBlockHeight());
            var fHeight = Math.min(maxBoardHeight() - hEdgeSpace, cHeight-hEdgeSpace);

            elmContainer.style.left = cLeft + "px";
            elmContainer.style.top = cTop + "px";
            elmContainer.style.width = cWidth + "px";
            elmContainer.style.height = cHeight + "px";
            elmPlayingField.style.left = me.getBlockWidth() + "px";
            elmPlayingField.style.top  = me.getBlockHeight() + "px";
            elmPlayingField.style.width = fWidth + "px";
            elmPlayingField.style.height = fHeight + "px";

            var bottomPanelHeight = hEdgeSpace - me.getBlockHeight();
            var pLabelTop = me.getBlockHeight() + fHeight + Math.round((bottomPanelHeight - 30)/2) + "px";

            me.grid = [];
            var numBoardCols = fWidth / me.getBlockWidth() + 2;
            var numBoardRows = fHeight / me.getBlockHeight() + 2;

            for (var row = 0; row < numBoardRows; row++) {
                me.grid[row] = [];
                for (var col = 0; col < numBoardCols; col++) {
                    if (col === 0 || row === 0 || col === (numBoardCols-1) || row === (numBoardRows-1)) {
                        me.grid[row][col] = 1; // edge
                    } else {
                        me.grid[row][col] = 0; // empty
                    }
                }
            }

            for (var i = 0; i < foods.length; i++) {
                foods[i].addFood();
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
        this.elm = null;
        this.elmStyle = null;
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
        const DIR_DOWN = 0,
              DIR_RIGHT = 1,
              DIR_UP = 2,
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
            antSpeed = 1;

            function setModeListener (mode, speed) {
                document.getElementById(mode).addEventListener('click', function () { antSpeed = speed; });
            }

        me.antBody = {};
        me.antBody["b0"] = new AntBlock();
        me.antBody["b0"].row = config.startRow || 1;
        me.antBody["b0"].col = config.startCol || 1;
        me.antBody["b0"].xPos = me.antBody["b0"].row * board.getBlockWidth();
        me.antBody["b0"].yPos = me.antBody["b0"].col * board.getBlockHeight();
        me.antBody["b0"].elm = createAntElement();
        me.antBody["b0"].elmStyle = me.antBody["b0"].elm.style;
        board.getBoardContainer().appendChild(me.antBody["b0"].elm);
        me.antBody["b0"].elm.style.left = me.antBody["b0"].xPos + "px";
        me.antBody["b0"].elm.style.top = me.antBody["b0"].yPos + "px";
        me.antBody["b0"].next = me.antBody["b0"];
        me.antBody["b0"].prev = me.antBody["b0"];

        me.antPos = me.antBody["b0"];
        me.antPos.elm.id = "ant-anthead-alive";
        me.antPos.elm.className += " ant-antbody-alive";
        setTimeout(function(){ me.go(); }, antSpeed);

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
            tempBlock.elm = tempNode;
            board.getBoardContainer().appendChild(tempBlock.elm);
        }

        me.go = function() {
            var oldCol = me.antPos.col
            var oldRow = me.antPos.row
            var foodCoord = board.getFoodCoord(oldCol, oldRow);
            if (oldCol == foodCoord[0] && oldRow == foodCoord[1]) {
                me.eatFood(foodCoord[0], foodCoord[1]);
            }

            if (oldRow > foodCoord[1]) {
                currDir = DIR_DOWN;
            } else if (oldRow < foodCoord[1]) {
                currDir = DIR_UP;
            } else if (oldCol > foodCoord[0]) {
                currDir = DIR_LEFT;
            } else if (oldCol < foodCoord[0]) {
                currDir = DIR_RIGHT;
            }

            while (true) {
                newCol = oldCol + colShift[currDir];
                newRow = oldRow + rowShift[currDir];
                newxPos = me.antPos.xPos + xPosShift[currDir];
                newyPos = me.antPos.yPos + yPosShift[currDir];

                // check new pos
                if (board.grid[newRow][newCol] > 0) {
                    currDir = (currDir + 1) % 4;
                } else {
                    me.antPos.col = newCol;
                    me.antPos.row = newRow;
                    me.antPos.xPos = newxPos;
                    me.antPos.yPos = newyPos;
                    me.antPos.elmStyle.left = me.antPos.xPos + "px";
                    me.antPos.elmStyle.top = me.antPos.yPos + "px";
                    break;
                }
            }
            
            setTimeout(function(){ me.go(); }, antSpeed);
        };

        me.eatFood = function(col, row) {
            board.addFood(col, row);
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

    function getRandPos(x, y){
        return Math.floor(Math.random() * (y + 1 - x)) + x;
    }

    // constructor
    return function(config) {
        if (!config||!config.board) { return; }

        var me = this;
        var board = config.board;
        var col, row;
        var myId = instanceNumber++;
        var GRID_FOOD_VALUE = -1;

        var elmFood = document.createElement("div");
        elmFood.setAttribute("id", "ant-food-"+myId);
        elmFood.className = "ant-food-block";
        elmFood.style.width = board.getBlockWidth() + "px";
        elmFood.style.height = board.getBlockHeight() + "px";
        elmFood.style.left = "-1000px";
        elmFood.style.top = "-1000px";
        board.getBoardContainer().appendChild(elmFood);

        // public
        me.getCol = function() {
            return col;
        };

        me.getRow = function() {
            return row;
        };

        me.addFood = function() {
            // clear first
            if (board.grid[row] && board.grid[row][col] === GRID_FOOD_VALUE){
                board.grid[row][col] = 0;
            }

            var newCol = 0, newRow = 0;
            var maxRows = board.grid.length - 1;
            var maxCols = board.grid[0].length - 1;

            while (board.grid[newRow][newCol] !== 0){
                newRow = getRandPos(1, maxRows);
                newCol = getRandPos(1, maxCols);
            }

            col = newCol;
            row = newRow;
            board.grid[row][col] = GRID_FOOD_VALUE;
            elmFood.style.top = row * board.getBlockHeight() + "px";
            elmFood.style.left = col * board.getBlockWidth() + "px";
        };
    };
})(); // ANT.Food
