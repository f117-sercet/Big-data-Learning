## sparkStreaming  
### 概述  
Spark Streaming 用于流式数据的处理。Spark Streaming 支持的数据输入源很多，例如：Kafka、 Flume、Twitter、ZeroMQ 和简单的 TCP 套接字等等。数据输入后可以用 Spark 的高度抽象原语
如：map、reduce、join、window 等进行运算。而结果也能保存在很多地方，如HDFS，数据库等。
![img.png](img.png)  
和 Spark 基于 RDD 的概念很相似，Spark Streaming 使用离散化流(discretized stream)作为抽 象表示，叫作DStream。DStream 是随时间推移而收到的数据的序列。在内部，每个时间区间收 到的数据都作为 RDD 存在，而DStream是由这些RDD所组成的序列(因此得名“离散化”)。所以
简单来将，DStream 就是对 RDD在实时数据处理场景的一种封装。  
### 特点  
1. 易用
2. 容错 
3. 易整合到spark  
### 架构  
![img.png](img1.png)  
![img_1.png](img_1.png)  
### 背压机制  
Spark 1.5 以前版本，用户如果要限制Receiver 的数据接收速率，可以通过设置静态配制参 数“spark.streaming.receiver.maxRate”的值来实现，此举虽然可以通过限制接收速率，来适配当前 的处理能力，防止内存溢出，但也会引入其它问题。比如：producer 数据生产高于maxRate，当 前集群处理能力也高于maxRate，这就会造成资源利用率下降等问题。 为了更好的协调数据接收速率与资源处理能力，1.5 版本开始 Spark Streaming 可以动态控制
数据接收速率来适配集群数据处理能力。背压机制（即 Spark Streaming Backpressure）: 根据 JobScheduler 反馈作业的执行信息来动态调整Receiver 数据接收率。 通过属性“spark.streaming.backpressure.enabled”来控制是否启用 backpressure 机制，默认值
false，即不启用。  

