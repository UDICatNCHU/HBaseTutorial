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
