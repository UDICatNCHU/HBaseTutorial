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
export CLASSPATH=.:$HADOOP_HOME/share/hadoop/common/hadoop-common-2.10.1.jar:$HADOOP_HOME/share/hadoop/mapreduce/hadoop-mapreduce-client-core-2.10.1.jar:$HADOOP_HOME/share/hadoop/common/lib/commons-cli-1.2.jar:$CLASSPATH

```
