var request = require('request').defaults({strictSSL: false});

//const fs = require('fs');
//let result = JSON.parse(fs.readFileSync('cfsr_world_t2_day.json'));

let thisy = 0.0;
let lasty = 0.0;
let mean = 0.0;
let thisy_y = 0.0;
let lasty_y = 0.0;
let mean_y = 0.0;
let numday = 0;
//const cmp_label = "1979-2000 mean";
//const cmp_offset = 1;
const cmp_label = "plus 2σ";
const cmp_offset = 2;

request.get({
    url: 'https://climatereanalyzer.org/clim/t2_daily/json/cfsr_world_t2_day.json',
    json: true
  }, (err, res, result) => {
    if (err) {
      console.log('Error:', err);
    } else if (res.statusCode !== 200) {
      console.log('Status:', res.statusCode);
    } else {
        result.forEach(function (row, i) {
            if (row["name"] === cmp_label) {
                data_thisy = result[i - cmp_offset]["data"]
                data_thisy.every(function (val, j) {
                    if (val === null) {
                        numday = j - 1;
                        thisy = data_thisy[numday];
                        thisy_y = data_thisy[numday - 1];
                        return false;
                    }
                    return true;
                });
                data_lasty = result[i - cmp_offset - 1]["data"]
                lasty = data_lasty[numday];
                lasty_y = data_lasty[numday - 1];
                mean = row["data"][numday];
                mean_y = row["data"][numday - 1];
            }
        });
        console.log("yesterday: " + thisy_y.toString() + " (2022: " + lasty_y.toString() + "), mean: " + mean_y.toString() + ", diff: " + (thisy_y - mean_y).toString());
        console.log("today:     " + thisy.toString() + " (2022: " + lasty.toString() + "), mean: " + mean.toString() + ", diff: " + (thisy - mean).toString());
    }
});
