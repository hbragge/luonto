1) Download "Population by Age Groups" from World Population Prospects website
(https://esa.un.org/unpd/wpp/Download/Standard/), with filenames
WPP2017_POP_F07_2_POPULATION_BY_AGE_MALE.xlsx and WPP2017_POP_F07_3_POPULATION_BY_AGE_FEMALE.xlsx

2) Run parser: python3 parser.py > out20-2.txt

Update mrates:
grep -Eo "^[[:alpha:] .]+" mrates-from-wiki.txt|tr "\n" ","
grep -Eo "[0-9]+\.[0-9]+" mrates-from-wiki.txt|tr "\n" ","
