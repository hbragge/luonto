var request = require('request');
var url = 'https://api.exchangeratesapi.io';
var base = '?base=USD'
var start = url + '/2020-01-31' + base;
var end = url + '/latest' + base;

request.get({
    url: end,
    json: true,
    headers: {'User-Agent': 'request'}
  }, (err, res, ratesEnd) => {

    if (err) {
      console.log('Error:', err);
    } else if (res.statusCode !== 200) {
      console.log('Status:', res.statusCode);
    } else {

        request.get({
            url: start,
            json: true,
            headers: {'User-Agent': 'request'}
        }, (err2, res2, ratesStart) => {
            if (err2) {
                console.log('Error:', err2);
            } else if (res2.statusCode !== 200) {
                console.log('Status:', res2.statusCode);
            } else {
                //console.log("Start:");
                //console.log(ratesStart.rates);
                //console.log("\nEnd:");
                //console.log(ratesEnd.rates);
                //console.log("\nChange:");
                for (var curr in ratesStart.rates) {
                    if (ratesStart.rates.hasOwnProperty(curr)) {
                        var s = ratesStart.rates[curr];
                        var e = ratesEnd.rates[curr];
                        var perc = ((e/s) - 1.0) * 100;
                        console.log(perc.toFixed(1) + ", " + "(" + s.toFixed(2) + " -> " + e.toFixed(2) + "), " + curr);
                    }
                }
            }
        });
    }
});
