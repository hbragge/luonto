const csv = require('csv-parser'),
      fs = require('fs'),
      bodyParser = require("body-parser"),
      express = require("express");
var app = express(),
    countries = [];

app.set("view engine", "ejs");
app.use(express.static("public"));
app.use(bodyParser.urlencoded({extended: true}));

fs.createReadStream('data.txt')
    .pipe(csv())
    .on('data', (row) => {
        countries.push({name: row.Country, ratio: parseInt(row.Ratio), homicides: parseFloat(row.Homicides), isSpecial: (row.IsSpecial === '1')});
    });

app.get("/", (req, res) => {
    res.render("index", {countries: countries});
});

app.listen(process.env.PORT, process.env.IP, () => {
    console.log("Listening to port " + process.env.PORT);
});
