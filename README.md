# HBaseTutorial

### Basic Shell Scripts

Start Script:
```
hbase shell
```

All Data Store shall provide the following functions:
- Create tables
- Insert data
- Retrieve data

Create Table
```
create 'employees', 'name', 'department
```

Insert Data
```
put 'employees', 'row1', 'name', 'Yao-Chung'
```

Retrieve Data
```
scan 'employees'
get 'employees', 'row1'
```

Delete Data
```
Deleteall 'employees', 'row1'
```

Delete Table
```
disable 'table1'
drop 'table1'
```

Batch Input with CSV file
```
create 'employees', 'name', 'department'
hbase org.apache.hadoop.hbase.mapreduce.ImportTsv -Dimporttsv.separator=',' -Dimporttsv.columns=HBASE_ROW_KEY,name,department employees employees.csv
```

Alter Table Setting
```
describe "employees"
alter "employees", {NAME=>'department', VERSIONS=>3}
```
Test Version Setting by Get and Scan
```
put "employees", "row4", "department", "cs1"
put "employees", "row4", "department", "cs2"
put "employees", "row4", "department", "cs3"
get "employees", "row4", {COLUMN=>"department", VERSIONS=>3}
scan "employees", {VERSION=>10}
```

RowPrefixFilter
```
put "employees", "4", "department", "cs4"
put "employees", "44", "department", "cs4"
put "employees", "443", "department", "cs444"
scan "employees", {ROWPREFIXFILTER=>"4
```

