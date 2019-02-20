# hadoop-opentsdb-metrics2-sink

### hadoop-metrics2.properties

*.period=10

*.sink.opentsdb.class=ru.hh.hadoop.metrics2.sink.OpenTSDBSink
*.sink.opentsdb.opentsdb_url=http://opentsdb:4242

datanode.sink.opentsdb.metrics_prefix=hadoop.datanode
namenode.sink.opentsdb.metrics_prefix=hadoop.namenode
journalnode.sink.opentsdb.metrics_prefix=hadoop.journalnode
resourcemanager.sink.opentsdb.metrics_prefix=hadoop.resourcemanager
nodemanager.sink.opentsdb.metrics_prefix=hadoop.nodemanager
jobhistoryserver.sink.opentsdb.metrics_prefix=hadoop.jobhistoryserver
applicationhistoryserver.sink.opentsdb.metrics_prefix=hadoop.applicationhistoryserver
timelineserver.sink.opentsdb.metrics_prefix=hadoop.timelineserver
maptask.sink.opentsdb.metrics_prefix=hadoop.maptask
reducetask.sink.opentsdb.metrics_prefix=hadoop.reducetask
mrappmaster.sink.opentsdb.metrics_prefix=hadoop.mrappmaster
