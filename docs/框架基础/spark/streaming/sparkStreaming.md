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
##  DStream创建  
测试过程中，可以通过使用 ssc.queueStream(queueOfRDDs)来创建DStream，每一个推送到 这个队列中的RDD，都会作为一个DStream处理。  
#### 自定义数据源
需要继承Receiver，并实现 onStart、onStop 方法来自定义数据源采集。
#### DStream转换  
DStream上的操作与 RDD的类似，分为 Transformations（转换）和Output Operations（输 出）两种，此外转换操作中还有一些比较特殊的原语，如：updateStateByKey()、transform()以及
各种Window相关的原语。
#### 无状态转化操作  
![img_2.png](img_2.png)  
需要记住的是，尽管这些函数看起来像作用在整个流上一样，但事实上每个DStream在内部 是由许多RDD（批次）组成，且无状态转化操作是分别应用到每个RDD上的。
例如：reduceByKey()会归约每个时间区间中的数据，但不会归约不同区间之间的数据。  
### TransForm
Transform允许DStream 上执行任意的RDD-to-RDD函数。即使这些函数并没有在DStream 的API 中暴露出来，通过该函数可以方便的扩展 Spark API。该函数每一批次调度一次。其实也
就是对DStream 中的RDD应用转换。  
#### join 
两个流之间的 join 需要两个流的批次大小一致，这样才能做到同时触发计算。计算过程就是 对当前批次的两个流中各自的RDD进行 join，与两个RDD的 join 效果相同。  
### 有状态转化操作  
#### updateStateByKey
UpdateStateByKey 原语用于记录历史记录，有时，我们需要在DStream 中跨批次维护状态(例 如流计算中累加wordcount)。针对这种情况，updateStateByKey()为我们提供了对一个状态变量 的访问，用于键值对形式的DStream。给定一个由(键，事件)对构成的 DStream，并传递一个指 定如何根据新的事件更新每个键对应状态的函数，它可以构建出一个新的 DStream，其内部数
据为(键，状态) 对。updateStateByKey() 的结果会是一个新的DStream，其内部的RDD 序列是由每个时间区间对 应的(键，状态)对组成的。  
updateStateByKey() 的结果会是一个新的DStream，其内部的RDD 序列是由每个时间区间对 应的(键，状态)对组成的。  
updateStateByKey 操作使得我们可以在用新信息进行更新时保持任意的状态。为使用这个功 能，需要做下面两步：
1. 定义状态，状态可以是一个任意的数据类型 
2. 定义状态更新函数，用此函数阐明如何使用之前的状态和来自输入流的新值对状态进行更新。  
3. 使用 updateStateByKey 需要对检查点目录进行配置，会使用检查点来保存状态。  
#### WindowOperations  
Window Operations 可以设置窗口的大小和滑动窗口的间隔来动态的获取当前 Steaming 的允许 状态。所有基于窗口的操作都需要两个参数，分别为窗口时长以及滑动步长。  
1. 窗口时长：计算内容的时间范围;
2. 滑动步长：隔多久触发一次计算。
注意：这两者都必须为采集周期大小的整数倍。

