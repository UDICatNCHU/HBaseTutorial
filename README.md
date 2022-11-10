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
Test 
```
http://140.120.13.241:33305/version/cluster

pip install requests
import requests
r = requests.get("http://140.120.13.241:33305/version/cluster")
print(r.text)
```

```
http://140.120.13.241:33305/employees/schema
r = requests.get("http://140.120.13.241:33305/employees/schema", headers={"Accept" : "application/json"})
print(r.text)
```



Query: get a row.
```
curl -vi -X GET -H "Accept: application/json"  "http://140.120.13.241:33305/employees/1"
```
or
```
r = requests.get("http://140.120.13.241:33305/employees/1", headers={"Accept" : "application/json"})
print(r.text)
```

Scan:
```
curl -vi -X PUT   -H "Accept: text/xml"   -H "Content-Type: text/xml"   -d '<Scanner batch="2"/>'   "http://140.120.13.241:33305/employees/scanner/"
```
![Screenshot by Dropbox Capture](https://user-images.githubusercontent.com/23067569/201081327-4866826e-dba0-43c8-af74-22b49f802a79.png)

scanner會回傳一個location, i.e., http://140.120.13.241:33305/employees/scanner/166808046151419036dac. 我們可以一直requests，直到HTTP/1.1 204 No Content

```
curl -vi -X GET "http://140.120.13.241:33305/employees/scanner/16680802163774805c3d6"
```

