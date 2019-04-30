const csv = require('csv-parser'),
      fs = require('fs'),
      express = require("express");
var app = express(),
    data = {};

fs.createReadStream('data.txt')
    .pipe(csv())
    .on('data', (row) => {
        data[row.Country] = {ratio: parseInt(row.Ratio), homicides: parseFloat(row.Homicides), isSpecial: (row.IsSpecial === '1')};
    });

app.get("/", (req, res) => {
    res.send(data);
});

app.listen(process.env.PORT, process.env.IP, () => {
    console.log("Listening to port " + process.env.PORT);
});
