const BASE_RAD = 10;
const ANT_RAD = 5;
const MAX_ROUNDS = 5;
const STEP_SIZE = 10;

function createBase(ctx, x, y, color) {
  var base = new Base(ctx, x, y, color);
  return base;
}

var c = document.getElementById("myCanvas");
var ctx = c.getContext("2d");
var baseRed = createBase(ctx, c.width/8, c.height/2, "blue");
var baseBlue = createBase(ctx, c.width - c.width/8, c.height/2, "red");
var rounds = 0;

function Ant(ctx, base, x, y, color) {
  this.ctx = ctx;
  this.base = base;
  this.x = x;
  this.y = y;
  this.color = color;
}

Ant.prototype.move = function() {
  var step = -STEP_SIZE;
  if (this.color === "blue") {
    step = STEP_SIZE;
  }
  console.log("moving x: " + this.x);
  this.x += step;
}

Ant.prototype.draw = function() {
  var step = -(STEP_SIZE/2);
  if (this.oldx && this.oldy) {
    this.ctx.clearRect(this.oldx+step, this.oldy-ANT_RAD, ANT_RAD*2, ANT_RAD*2);
  }
  this.oldx = this.x;
  this.oldy = this.y;
  ctx.beginPath();
  ctx.arc(this.x, this.y, ANT_RAD, 0, 2*Math.PI);
  //ctx.fillStyle = this.color;
  ctx.fillStyle = "black";
  ctx.fill();
  console.log("drawing x: " + this.x);
}

function Base(ctx, x, y, color) {
  this.ants = [];
  this.ctx = ctx;
  this.x = x;
  this.y = y;
  this.color = color;
  ctx.beginPath();
  ctx.arc(x, y, BASE_RAD, 0, 2*Math.PI);
  ctx.fillStyle = color;
  ctx.fill();
}

Base.prototype.spawn = function() {
    var step = -(STEP_SIZE/2);
    if (this.color === "blue") {
      step = STEP_SIZE/2;
    }
    var ant = new Ant(this.ctx, this, this.x+step, this.y, this.color);
    this.ants.push(ant);
  }

Base.prototype.tick = function() {
  this.ants.forEach(function(ant) {
    ant.move();
    ant.draw();
  });
}

function tick() {
  if (rounds < MAX_ROUNDS) {
    baseRed.tick();
    baseBlue.tick();
    rounds++;
    window.setTimeout(tick, 1000);
  }
}

baseRed.spawn();
baseBlue.spawn();
baseRed.tick();
baseBlue.tick();

window.setTimeout(tick, 1000);
