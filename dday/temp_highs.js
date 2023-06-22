const fs = require('fs');
let result = JSON.parse(fs.readFileSync('cfsr_world_t2_day.json'));

let tmax = 0.0;
let dmax = 0;

let tmax_all = 0.0;
let dmax_all = 0;
let ymax_all = null;

console.log("Today:");
result.forEach(function (row, i) {
    let name = row["name"];
    if (name === "2022" || name === "2023" || name === "1979-2000 mean" || name === "plus 2σ") {
        console.log(name + ": " + row["data"][170]);
    }
    if (name === "1979-2000 mean") {
        row["data"].forEach(function (val, i) {
            if (val > tmax) {
                tmax = val;
                dmax = i;
            }
        });
    }
    if (name !== "1979-2000 mean" && name !== "plus 2σ" && name !== "minus 2σ") {
        row["data"].forEach(function (val, i) {
            if (val > tmax_all) {
                tmax_all = val;
                dmax_all = i;
                ymax_all = name;
            }
        });
    }
});

console.log("\nMax mean temp on day: " + dmax + ": " + tmax);
console.log("Max temp overall on day: " + dmax_all + " of " + ymax_all + ": " + tmax_all);
