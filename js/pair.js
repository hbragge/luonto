var pair = function(x, y) {
    var data = function(pick) {
        if (pick === 0) {
            return x;
        } else {
            return y;
        }
    }
    return data;
}
var first = function(p) {
    return p(0);
}
var second = function(p) {
    return p(1);
}

var p = pair(1, 2);

console.log("pick first: " + first(p));
console.log("pick second: " + second(p));
