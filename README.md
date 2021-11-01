# HBaseTutorial


登入Hbase
```
ssh hbuser@140.120.182.143 -p 1100
```

設定編輯路徑
nano ~/.bashrc
```
export HADOOP_HOME=/usr/local/hadoop
export PATH=$PATH:$HADOOP_HOME/bin
export CLASSPATH=.:$HBase_Home/lib/*:$HADOOP_HOME/share/hadoop/common/hadoop-common-2.10.1.jar:$HADOOP_HOME/share/hadoop/mapreduce/hadoop-mapreduce-client-core-2.10.1.jar:$HADOOP_HOME/share/hadoop/common/lib/commons-cli-1.2.jar:$CLASSPATH
export CLASSPATH=$CLASSPATH:/usr/local/hbase/conf:/usr/lib/jvm/java-8-openjdk-amd64/lib/tools.jar:/usr/local/hbase:/usr/local/hbase/lib/shaded-clients/hbase-shaded-client-byo-hadoop-2.4.6.jar:/usr/local/hbase/lib/client-facing-thirdparty/audience-annotations-0.5.0.jar:/usr/local/hbase/lib/client-facing-thirdparty/commons-logging-1.2.jar:/usr/local/hbase/lib/client-facing-thirdparty/htrace-core4-4.2.0-incubating.jar:/usr/local/hbase/lib/client-facing-thirdparty/log4j-1.2.17.jar:/usr/local/hbase/lib/client-facing-thirdparty/slf4j-api-1.7.30.jar:/usr/local/hadoop/etc/hadoop:/usr/local/hadoop/share/hadoop/common/lib/*:/usr/local/hadoop/share/hadoop/common/*:/usr/local/hadoop/share/hadoop/hdfs:/usr/local/hadoop/share/hadoop/hdfs/lib/*:/usr/local/hadoop/share/hadoop/hdfs/*:/usr/local/hadoop/share/hadoop/yarn:/usr/local/hadoop/share/hadoop/yarn/lib/*:/usr/local/hadoop/share/hadoop/yarn/*:/usr/local/hadoop/share/hadoop/mapreduce/lib/*:/usr/local/hadoop/share/hadoop/mapreduce/*:/usr/local/hadoop/contrib/capacity-scheduler/*.jar

```



### Some Basic Shell Script 

```
hbase shell
```
