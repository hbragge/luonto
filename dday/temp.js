var request = require('request').defaults({strictSSL: false});

//const fs = require('fs');
//let result = JSON.parse(fs.readFileSync('cfsr_world_t2_day.json'));

let now = 0.0;
let mean = 0.0;
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
                data_now = result[i-cmp_offset]["data"]
                data_now.every(function (val, j) {
                    if (val === null) {
                        numday = j-1;
                        now = data_now[numday];
                        return false;
                    }
                    return true;
                });
                mean = row["data"][numday];
            }
        });
        console.log("now: " + now.toString() + ", mean: " + mean.toString() + ", diff: " + (now - mean).toString());
    }
});