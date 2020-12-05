var datejs = require("datejs");
var dateformat = "MMMM dS, yyyy";
var ddate = "2025-12-01";
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
var remaining = function(name, dateStr, today, totalMonths) {
    var remDiff = Date.parse(dateStr) - today;
    var remDays = remDiff/1000/60/60/24;
    var remMonths = round(remDays/30.4, 1);
    var bwDate = new Date(today);
    var totalMonthsStr = (totalMonths > 0) ? "/" + totalMonths : "";
    var ratioStr = (totalMonths > 0) ? " " + ((1 - remMonths/totalMonths) * 100).toFixed(2) + "%" : "";
    bwDate.addDays(-remDays);
    console.log(Math.floor(remDays) +
                " days (" + remMonths + totalMonthsStr +
                " months" + ratioStr + ") until " + name + ", backwards: " +
                bwDate.toString(dateformat));
}

dates.forEach(function(d) {
    remaining(d.name, d.date, startDate, d.totalMonths || 0);
});
console.log("");
console.log("Same as from:");
refdates.forEach(function(d) {
    period(d);
});
