var datejs = require("datejs");
var dateformat = "MMMM dS, yyyy";
var ddate = "2025-12-01"
var startDate = Date.today();
var ddiff = Date.parse(ddate) - startDate;
var ddays = ddiff/1000/60/60/24;
var refdates = [
    "2000-01-01"
];
var dates = [
    {name: "2025", date: "2025-01-01"}
];
var round = function(value, precision) {
    var multiplier = Math.pow(10, precision || 0);
    return Math.round(value * multiplier) / multiplier;
}
var period = function(dateStr) {
    var date = Date.parse(dateStr);
    console.log(date.toString(dateformat) + " --> " +
                date.addDays(ddays).toString(dateformat));
}
var remaining = function(name, dateStr, today) {
    var remdiff = Date.parse(dateStr) - today;
    var remdays = remdiff/1000/60/60/24;
    var bwdate = new Date(today);
    bwdate.addDays(-remdays);
    console.log(Math.floor(remdays) +
                " days (" + round(remdays/30.4, 1) +
                " months) until " + name + ", backwards: " +
                bwdate.toString(dateformat));
}

dates.forEach(function(d) {
    remaining(d.name, d.date, startDate);
});
console.log("");
console.log("Same as from:");
refdates.forEach(function(d) {
    period(d);
});
