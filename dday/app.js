var datejs = require("datejs");
var dday = Date.parse("2025-06-01");
var today = Date.today();
var diff = dday - today;
var days = diff/1000/60/60/24;
var bwdate = today.addDays(-days);

console.log(days + " days remaining");
console.log("Counting backwards was: " + bwdate.toString("MMMM dS, yyyy"));
