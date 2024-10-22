# HBaseTutorial

nano .bashrc
```
export HBASE_HOME=/usr/local/hbase
export PATH=$PATH:$HBASE_HOME/bin
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java
export PATH=$PATH:$JAVA_HOME/bin
```

# Contents
 -[How to use shell script to communicate with HBASE?](#basic-shell-scripts)
 
 -[How to use Thrift to communicate with HBASE?](https://github.com/UDICatNCHU/HBaseTutorial/edit/main/README.md#%E5%A6%82%E4%BD%95%E4%BD%BF%E7%94%A8thrift-api%E8%88%87hbase%E6%BA%9D%E9%80%9A-how-to-communicate-with-thrif-api)
 
 -[How to use REST api to communicate with HBASE?](https://github.com/UDICatNCHU/HBaseTutorial/edit/main/README.md#%E5%A6%82%E4%BD%95%E4%BD%BF%E7%94%A8restful-api%E8%88%87hbase%E6%BA%9D%E9%80%9A)


## We can use REST or Thrift API to communicate with HBASE
<img width="338" alt="image" src="https://user-images.githubusercontent.com/23067569/202331952-8ff0f35d-5cef-4b12-a945-7bfd55263831.png">



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
scan "employees", {ROWPREFIXFILTER=>"4}
```
### Check Colab for shell scripting with Hbase
https://colab.research.google.com/drive/1yzOCyHEAZM5wNUyHwjH-_MEwbYvwxeLq?usp=sharing

## 如何使用Thrift API與HBase溝通 (How to communicate with Thrif API)？

Thrift is a lightweight, language-independent software stack for point-to-point RPC implementation. Thrift provides clean abstractions and implementations for data transport, data serialization, and application level processing. (for more details, please see [here](https://github.com/apache/thrift))
<img width="757" alt="image" src="https://user-images.githubusercontent.com/23067569/202330891-e9012770-b81b-40b6-b174-8c99571c26c0.png">

簡單一句話, Thrift makes it easy for programs written in different programming languages to share data and call remote procedures.

```
bin/hbase-daemon.sh start thrift
!pip install happybase==1.1.0
```
Batch Input with CSV file (using [this](https://www.dropbox.com/s/p0oylcw2kontdip/employees.csv?dl=0) as example)

In addition, we also need HappyBase (for using python to connect Hbase through thrift)
HappyBase is a developer-friendly Python library to interact with Apache HBase. HappyBase is designed for use in standard HBase setups, and offers application developers a Pythonic API to interact with HBase. Below the surface, HappyBase uses the Python Thrift library to connect to HBase using its Thrift gateway


```
import happybase
connection = happybase.Connection("127.0.0.1",9090,autoconnect=True)
connection.create_table(
    'employees',
    {
     'name': dict(max_versions=10),
     'department': dict(),  # use defaults
    }
)
```
### Check if the table creation is successed.

```
connection.tables()
```

### Insert data into HBase
```
!echo "put 'employees', 'row1', 'name:first','yao-chung'" | ./bin/hbase shell -n
!echo "put 'employees', 'row1', 'name:last','fan'" | ./bin/hbase shell -n
!echo "put 'employees', 'row1', 'department:major','cs'" | ./bin/hbase shell -n
!echo "put 'employees', 'row1', 'department:second','math'" | ./bin/hbase shell -n
```
or using python with happybase connection
```
connection = happybase.Connection("127.0.0.1")
table = connection.table('employees')
table.put("row1",{"name:first":"yao-chung"})
table.put("row1",{"name:last":"yao-chung"})
table.put("row1",{"department:major":"cs"})
table.put("row1",{"department:second":"math"})
```

### Scan data with happybase.

```
connection = happybase.Connection("127.0.0.1")
table = connection.table('employees')
for key,value in table.scan():
  print(key)
  print(value)
```

```
connection = happybase.Connection("127.0.0.1")
table = connection.table('employees')
table.delete("row1", columns=["department:second"])
```

### Check the Colab for Happybase script
https://colab.research.google.com/drive/1ghtH8h4cgNTvsecgEnRJTEbB0fDCFYbL?usp=sharing 

<br><br>


## 如何使用Restful API與HBase溝通?
Assume that our Hbase server hosted on 140.120.13.241 with 33305 port

First, we can enable Restful API as follows.
```
bin/hbase-daemon.sh start rest -p 33305
```
### Test if the Rest API is enabled.

using a Browser
```
http://140.120.13.241:33305/version/cluster
```
or with Python Request library
```
pip install requests
import requests
r = requests.get("http://140.120.13.241:33305/version/cluster")
print(r.text)
```

### Test to get a table's schema
```
http://140.120.13.241:33305/employees/schema
```

```
r = requests.get("http://140.120.13.241:33305/employees/schema", headers={"Accept" : "application/json"})
print(r.text)
```



### Query: to get rows.
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

scanner會回傳一個location, i.e., http://140.12x.xx.xx/employees/scanner/166808046151419036dac. 

We can get the next batch from the scanner. Cell values are byte-encoded. If the scanner has been exhausted, HTTP status 204 is returned.

```
curl -vi -X GET "http://140.120.13.241:33305/employees/scanner/16680802163774805c3d6"
```

### To Insert rows with Rest API

首先，需留意Hbase Rest API互動中，column name 與 column value需編碼為Base64。
可使用下列Method進行編碼：

```
def encode(s):
    return base64.b64encode(s.encode("UTF-8")).decode("UTF-8")
```

For example, 
<img width="282" alt="image" src="https://user-images.githubusercontent.com/23067569/201287488-98b71e6e-3022-4e0c-9f19-1c5862487113.png">

如果我們要透過Rest API來insert資料至Hbase中。以Hbase shell command為例，
```
put "employees", "rowkey99", "departmet:Major", "Computer Science"
```
Rest API對應的步驟為：
1. 將資料包裝成dictionary格式
```
cell= {"key":encode("rowkey99"), 
       "Cell":[{"column":encode("department:Major"),"$":encode("Computer Science")}]}
```
```
{'key': 'cm93a2V5OTk=',
 'Cell': [{'column': 'ZGVwYXJ0bWVudDpNYWpvcg==',
   '$': 'Q29tcHV0ZXIgU2NpZW5jZQ=='}]}
```
2. 使用下列語法，post資料到hbase
```
rows = []
rows.append(cell)
jsonFormat = {"Row":rows}
r = requests.post("http://140.120.13.241:33305/employees/fakerow", json.dumps(jsonFormat), headers={"Content-Type":"application/json", "Accept" : "application/json"})
```

# Colab for using REST api for operating Hbase
https://colab.research.google.com/drive/1VIDO0mHL8X4GIe5bjbKQMQz5B5onwxBP?usp=sharing


[photo creadited](https://blog.cloudera.com/how-to-use-the-hbase-thrift-interface-part-1/)


# hbase install
```
wget https://archive.apache.org/dist/hbase/3.0.0-alpha-1/hbase-3.0.0-alpha-1-bin.tar.gz
tar -xzf hbase-3.0.0-alpha-1-bin.tar.gz
mv hbase-3.0.0-alpha-1 /usr/local/hbase
sudo mv hbase-3.0.0-alpha-1 /usr/local/hbase
```

nano ~/.bashrc
```
export HBASE_HOME=/usr/local/hbase
export PATH=$PATH:$HBASE_HOME/bin
```
source ~/.bashrc

```
cd $HBASE_HOME/conf
readlink -f /usr/bin/java //get javapath

source ~/.bashrc
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
export PATH=$PATH:$JAVA_HOME/bin
nano hbase-env.sh
echo $JAVA_HOME
sudo chmod -R 755 /usr/local/hbase
cd /usr/local/hbase/
bin/start-hbase.sh
```

Logging as hduser
```
hbase-daemon.sh start thrift
start-hbase.sh
```
