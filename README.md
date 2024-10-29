# HBaseTutorial

nano .bashrc
```
export HBASE_HOME=/usr/local/hbase
export PATH=$PATH:$HBASE_HOME/bin
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/jre
export PATH=$PATH:$JAVA_HOME/bin
```

# Contents
 -[How to use shell script to communicate with HBASE?](#basic-shell-scripts)
 
 -[How to use Thrift to communicate with HBASE?](https://github.com/UDICatNCHU/HBaseTutorial/edit/main/README.md#%E5%A6%82%E4%BD%95%E4%BD%BF%E7%94%A8thrift-api%E8%88%87hbase%E6%BA%9D%E9%80%9A-how-to-communicate-with-thrif-api)
 

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
create 'employees', 'SalePersonInFo', 'Sales'
```

Insert Data
```
put 'employees', 'row1', 'SalePersonInFo:name', 'Yao-Chung'
put 'employees','001','SalePersonInfo:address','Taichung'
put 'employees','001','Sales:Region','Taiwan'
put 'employees','001','Sales:saleAmount','100'
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
