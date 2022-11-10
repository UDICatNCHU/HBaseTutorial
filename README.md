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

Batch Input with CSV file (using [this](https://www.dropbox.com/s/p0oylcw2kontdip/employees.csv?dl=0) as example)
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


# 啟用Restful Interface for HBase 
假設我們的Hbase架設於140.120.13.241對外聆聽33305 port
We can enable Restful API as follows.
```
bin/hbase-daemon.sh start rest -p 33305
```
Test and Query 
```
http://140.120.13.241:33305/version/cluster
```

```
http://140.120.13.241:33305/employees/schema
```

```
curl -vi -X GET -H "Accept: application/json"  "http://140.120.13.241:33305/employees/1"
```
or
```
pip install requests
import requests
r = requests.get("http://140.120.13.241:33305/version/cluster")
print(r.text)
r = requests.get("http://140.120.13.241:33305/employees/schema", headers={"Accept" : "application/json"})
print(r.text)
r = requests.get("http://140.120.13.241:33305/employees/1", headers={"Accept" : "application/json"})
print(r.text)
```
