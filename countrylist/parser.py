import openpyxl
import itertools
import sys

M_XSL = "WPP2017_POP_F07_2_POPULATION_BY_AGE_MALE.xlsx"
F_XSL = "WPP2017_POP_F07_3_POPULATION_BY_AGE_FEMALE.xlsx"
#YEAR = 2020
YEAR = 2020
TITLE_COL = 16
#COLNAME1519 = "15-19"
COLNAME2024 = "20-24"
COLNAME2529 = "25-29"
COLNAME3034 = "30-34"
COLNAME3539 = "35-39"
COLNAME4044 = "40-44"
COLNAME4549 = "45-49"
SPECIAL = ["China Taiwan Province of China", "United Kingdom", "Denmark", "Nepal", "Lao People's Democratic Republic", "Myanmar", "Cambodia", "Philippines", "Sri Lanka", "Ecuador", "Morocco", "Peru", "Israel", "New Zealand", "Indonesia", "China Hong Kong SAR", "Armenia", "India", "United States of America", "Malaysia", "Netherlands", "Viet Nam", "France", "Thailand", "Singapore", "Albania", "Germany", "China", "Russian Federation", "China Taiwan Province of China", "Spain"]
#AREA = ["Western Africa", "Central Asia", "LATIN AMERICA AND THE CARIBBEAN", "South-Eastern Asia", "WORLD", "Western Europe", "Southern Europe", "Eastern Europe"]

NAMES = [
"Burundi","Comoros","Djibouti","Eritrea","Ethiopia","Kenya","Madagascar","Malawi","Mauritius","Mayotte","Mozambique","Réunion","Rwanda","Seychelles","Somalia","South Sudan","Uganda","Tanzania","Zambia","Zimbabwe","Angola","Cameroon","Central African Republic","Chad","Congo","D. R. Congo","Equatorial Guinea","Gabon","São Tomé and Príncipe","Algeria","Egypt","Libya","Morocco","Sudan","Tunisia","Botswana","Lesotho","Namibia","South Africa","Swaziland","Benin","Burkina Faso","Cape Verde","Ivory Coast","Gambia","Ghana","Guinea","Guinea","Liberia","Mali","Mauritania","Niger","Nigeria","Saint Helena","Senegal","Sierra Leone","Togo","Anguilla","Antigua and Barbuda","Aruba","Bahamas","Barbados","British Virgin Islands","Cayman Islands","Cuba","Curaçao","Dominica","Dominican Republic","Grenada","Guadeloupe","Haiti","Jamaica","Martinique","Montserrat","Puerto Rico","Saint Kitts and Nevis","Saint Lucia","Saint Vincent and the Grenadines","Trinidad and Tobago","Turks and Caicos Islands","Virgin Islands","Belize","Costa Rica","El Salvador","Guatemala","Honduras","Mexico","Nicaragua","Panama","Bermuda","Canada","Greenland","Saint Pierre and Miquelon","United States","Argentina","Bolivia","Brazil","Chile","Colombia","Ecuador","French Guiana","Guyana","Paraguay","Peru","Suriname","Uruguay","Venezuela","Kazakhstan","Kyrgyzstan","Tajikistan","Turkmenistan","Uzbekistan","China","Hong Kong","Macau","Taiwan","North Korea","Japan","Mongolia","South Korea","Brunei","Cambodia","Indonesia","Laos","Malaysia","Myanmar","Philippines","Singapore","Thailand","Timor","Vietnam","Afghanistan","Bangladesh","Bhutan","India","Iran","Maldives","Nepal","Pakistan","Sri Lanka","Armenia","Azerbaijan","Bahrain","Cyprus","Georgia","Iraq","Central Iraq","Kurdistan Region ","Israel","Jordan","Kuwait","Lebanon","Oman","Qatar","Saudi Arabia","Palestine","Syria","Turkey","United Arab Emirates","Yemen","Belarus","Bulgaria","Czech Republic","Hungary","Poland","Moldova","Romania","Russia","Slovakia","Ukraine","hannel Islands","Denmark","Estonia","Finland","Iceland","Ireland","Isle of Man","Latvia","Lithuania","Norway","Sweden","England and Wales","Northern Ireland","Scotland","United Kingdom","Albania","Andorra","Bosnia and Herzegovina","Croatia","Gibraltar","Greece","Vatican City","Italy","Kosovo","Malta","Montenegro","Portugal","San Marino","Serbia","Slovenia","Spain","North Macedonia","Austria","Belgium","France","Germany","Liechtenstein","Luxembourg","Monaco","Netherlands","Switzerland","Australia","New Zealand","Fiji","New Caledonia","Papua New Guinea","Solomon Islands","Vanuatu","Guam","Kiribati","F.S. Micronesia","Nauru","Palau","American Samoa","Cook Islands","French Polynesia","Niue","Samoa","Tonga","Tuvalu"
]

MRATES = [
6.02,7.70,6.48,8.04,7.56,4.87,7.69,1.73,1.82,5.93,3.40,1.82,2.52,12.74,4.31,13.90,11.52,6.95,5.30,6.67,4.85,4.17,19.76,9.04,9.32,13.55,2.31,8.04,3.36,1.36,2.51,2.50,1.24,5.16,3.05,15.04,41.25,17.14,33.97,17.29,6.18,0.37,11.49,11.63,9.13,1.68,8.82,9.55,3.23,10.90,9.94,4.44,9.85,0.00,7.38,1.71,9.00,27.66,10.33,1.93,28.40,10.91,8.37,8.45,4.99,19.19,8.37,15.18,10.25,8.01,10.04,47.01,2.78,19.88,18.51,34.23,19.27,36.46,30.88,5.93,49.26,37.60,11.90,82.84,27.26,56.52,19.26,7.37,9.67,12.96,1.68,5.31,15.93,5.35,5.94,6.30,29.53,3.46,25.50,5.85,13.15,18.37,9.29,7.67,8.35,7.69,56.33,4.81,4.49,1.61,4.22,3.00,0.62,0.38,0.16,0.82,4.40,0.28,5.66,0.70,0.49,1.84,0.50,7.01,2.11,2.27,11.02,0.32,3.24,3.95,1.52,6.35,2.50,1.13,3.22,2.47,0.75,2.16,4.41,2.55,2.98,2.14,0.52,1.11,0.99,9.85,10.08,2.20,1.36,1.55,1.80,3.99,0.66,0.38,1.50,0.69,2.20,4.31,0.89,6.66,3.58,1.14,0.61,2.07,0.67,3.19,1.25,10.82,1.05,6.34,0.00,0.98,3.19,1.42,0.30,0.80,0.00,3.36,5.25,0.51,1.08,1.22,0.97,1.18,1.20,2.70,0.00,1.28,1.04,3.01,0.75,0.00,0.67,1.60,0.94,4.46,0.64,0.00,1.39,0.48,0.63,1.59,0.66,1.95,1.35,1.18,0.00,0.72,0.00,0.55,0.54,0.94,0.99,2.26,3.23,7.85,3.77,2.13,2.51,7.50,4.67,0.00,3.11,5.40,3.49,0.38,0.00,3.15,0.95,18.65
]

if len(NAMES) != len(MRATES):
    print("ERROR: number of names and mrates does not match")
    exit(1)

def find_column_index(sheet_data, row, string_values):
    row_values = next(itertools.islice(sheet_data, row, row+1))
    for index, value in enumerate(row_values):
        if isinstance(value, str) and value.strip() in string_values:
            return index
    print("Could not find a column with values '{}' in row {}".format(string_values, row))
    sys.exit(-1)

data = {}

for fname in [F_XSL, M_XSL]:
    wb = openpyxl.load_workbook(fname)
    sheet = wb.get_sheet_by_name("MEDIUM VARIANT")
    country_col = find_column_index(sheet.values, TITLE_COL, ["Region, subregion, country or area *"])
    year_col = find_column_index(sheet.values, TITLE_COL, ["Reference date (as of 1 July)"])

    key = "f"

    g1_col = None
    g2_col = None
    g3_col = None
    g4_col = None
    g5_col = None
    g6_col = None

    if fname == M_XSL:
        key = "m"
        g1_col = find_column_index(sheet.values, TITLE_COL, [COLNAME2024])
        g2_col = find_column_index(sheet.values, TITLE_COL, [COLNAME2529])
        g3_col = find_column_index(sheet.values, TITLE_COL, [COLNAME3034])
        g4_col = find_column_index(sheet.values, TITLE_COL, [COLNAME3539])
        g5_col = find_column_index(sheet.values, TITLE_COL, [COLNAME4044])
        g6_col = find_column_index(sheet.values, TITLE_COL, [COLNAME4549])
    else:
        #g1_col = find_column_index(sheet.values, TITLE_COL, [COLNAME1519])
        g2_col = find_column_index(sheet.values, TITLE_COL, [COLNAME2024])
        g3_col = find_column_index(sheet.values, TITLE_COL, [COLNAME2529])
        g4_col = find_column_index(sheet.values, TITLE_COL, [COLNAME3034])

    for row in itertools.islice(sheet.values, TITLE_COL+1, sheet.max_row+1):
        year = row[year_col]
        if year != YEAR:
            continue
        country = row[country_col].replace(",", "")
        #g1 = row[g1_col]
        g1 = 0
        if g1_col:
            g1 = row[g1_col]
        g2 = row[g2_col]
        g3 = row[g3_col]
        g4 = row[g4_col]
        g5 = 0
        g6 = 0
        if g5_col:
            g5 = row[g5_col]
        if g6_col:
            g6 = row[g6_col]
        if country in data:
            m = int(g1+g2+g3+g4+g5+g6)
            f = data[country]["f"]
            data[country] = {"f": f, key: m, "ratio": int((m/f)*100)}
        else:
            data[country] = {key: int(g1+g2+g3+g4+g5+g6)}

print("Country,Ratio,Homicides,IsSpecial")
for key, val in sorted(data.items()):
    mrate = ""
    is_special = key in SPECIAL
    if key in NAMES:
        mrate = "," + str(MRATES[NAMES.index(key)])
    elif is_special:
        mrate = ",1.0"
    else:
        mrate = ",9999.0"
    special = ",0"
    if is_special:
        special = ",1"
    #name = key + ": " + str(val["m"]) + "/" + str(val["f"])
    name = key
    print(name + "," + str(val["ratio"]) + mrate + special)
