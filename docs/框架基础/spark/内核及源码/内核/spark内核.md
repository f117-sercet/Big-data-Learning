## spark内核概述  
Spark 内核泛指 Spark 的核心运行机制，包括 Spark 核心组件的运行机制、Spark 任务调 度机制、Spark 内存管理机制、Spark 核心功能的运行原理等，熟练掌握 Spark 内核原理，能 够帮助我们更好地完成 Spark 代码设计，并能够帮助我们准确锁定项目运行过程中出现的问题。  
### Spark核心组件 
#### Driver 
Spark 驱动器节点，用于执行 Spark 任务中的 main 方法，负责实际代码的执行工作。 Driver 在 Spark 作业执行时主要负责：  
1. 将用户程序转化为作业(Job)
2. 在Executor之间调度任务(Task)
3. 跟踪Executor的执行情况
4. 通过UI展示查询运行情况
#### Executor
Spark Executor 对象是负责在 Spark 作业中运行具体任务，任务彼此之间相互独立。Spark 应用启动时，ExecutorBackend 节点被同时启动，并且始终伴随着整个 Spark 应用的生命周 期而存在。如果有 ExecutorBackend 节点发生了故障或崩溃，Spark 应用也可以继续执行，
会将出错节点上的任务调度到其他 Executor 节点上继续运行。  
Executor有两个核心功能:  
1. 负责运行组成Spark应用的任务，并将结果返回给驱动器(Driver)
2. 它们通过自身的块管理器（Block Manager）为用户程序中要求缓存的 RDD 提供内存 式存储。RDD 是直接缓存在 Executor 进程内的，因此任务可以在运行时充分利用缓存
   数据加速运算。

### Spark通用运行流程概述  
![img.png](img.png)  
核心步骤如下:  
1. 任务提交后，都会先启动Driver程序;
2. 随后Driver向集群管理器注册应用程序。
3. 之后管理器根据此任务的配置文件分配Executor并启动程序。
4. Driver开始执行main函数，Spark查询为懒执行，当执行到Action算子时，开始反向推算，根据宽依赖进行stage的划分，随后每一个Stage对应一个TaskSet，TaskSet中有多个Task，查找可用资源Executor进行调度。
5. 根据本地化原则，Task 会被分发到指定的 Executor 去执行，在任务执行的过程中， Executor 也会不断与Driver 进行通信，报告任务运行情况。  
### Spark 部署模式  
Spark 支持多种集群管理器（Cluster Manager），分别为：
1. Standalone：独立模式，Spark 原生的简单集群管理器，自带完整的服务，可单独部署到 一个集群中，无需依赖任何其他资源管理系统，使用 Standalone 可以很方便地搭建一个
集群;
2. Hadoop YARN：统一的资源管理机制，在上面可以运行多套计算框架，如 MR、Storm 等。根据Driver 在集群中的位置不同，分为 yarn client（集群外）和 yarn cluster（集群
   内部）
3. Apache Mesos：一个强大的分布式资源管理框架，它允许多种不同的框架部署在其上， 包括Yarn。
4. K8S : 容器式部署环境。
### YARN模式运行机制
#### YARN Cluster 模式
1. 执行脚本提交任务，实际上是启动一个SparkSubmit的JVM进程。
2. SparkSubmit 类中的main 方法反射调用YarnClusterApplication 的main 方法;
3. YarnClusterApplication 创建 Yarn 客户端，然后向 Yarn 服务器发送执行指令：bin/java ApplicationMaster;
4. Yarn 框架收到指令后会在指定的NM中启动ApplicationMaster;
5. ApplicationMaster 启动Driver 线程，执行用户的作业;
6. AM向RM注册，申请资源;
7. 获取资源后AM向NM发送指令：bin/java YarnCoarseGrainedExecutorBackend;
8. CoarseGrainedExecutorBackend 进程会接收消息，跟 Driver 通信，注册已经启动的 Executor；然后启动计算对象 Executor 等待接收任务。
9. Driver线程继续执行完成作业的调度和任务的执行。
10. Driver分配任务并监控任务的执行。  

 注意：SparkSubmit、ApplicationMaster和CoarseGrainedExecutorBackend是独立的进程；Driver 是独立的线程；Executor 和YarnClusterApplication 是对象。  
 ![img_1.png](img_1.png)  
 #### YARN Client模式  
1. 执行脚本提交任务，实际是提交一个SparkSubmit的Jvm进程。
2. SparkSubmit 类中的main方法 反射调用用户代码的main方法。
3. 启动Dirver线程,执行用户作业，并创建ScheduleBakend;
4. YarnClientSchedulerBackend 向RM发送指令：bin/java ExecutorLauncher;
5. Yarn 框架收到指令后会在指定的 NM 中启动 ExecutorLauncher（实际上还是调用 ApplicationMaster 的main 方法）；  
```scala
object ExecutorLauncher{
   def main (args:Array[String]) :Unit={
      ApplicationMaster.main(args)
   }
}
```  
6. AM向RM注册，申请资源；
7. 获取资源后AM向NM发送指令：bin/java CoarseGrainedExecutorBackend；
8. CoarseGrainedExecutorBackend 进程会接收消息，跟 Driver 通信，注册已经启动的 Executor；然后启动计算对象 Executor 等待接收任务
9. Driver 分配任务并监控任务的执行。    

   注意：SparkSubmit、ApplicationMaster 和YarnCoarseGrainedExecutorBackend 是独立的进 程；Executor 和Driver是对象。  
![img_2.png](img_2.png)  
#### Standalone  
Standalone 集群有 2 个重要组成部分，分别是：  
1) Master(RM)：是一个进程，主要负责资源的调度和分配，并进行集群的监控等职责；
2) Worker(NM)：是一个进程，一个Worker 运行在集群中的一台服务器上，主要负责两个 职责，一个是用自己的内存存储 RDD 的某个或某些 partition；另一个是启动其他进程
   和线程（Executor），对RDD上的 partition 进行并行的处理和计算。  
#### Standalone Cluster 模式 
![img_3.png](img_3.png)  
在 Standalone Cluster 模式下，任务提交后，Master 会找到一个 Worker 启动 Driver。    
Driver 启动后向 Master 注册应用程序，Master 根据 submit 脚本的资源需求找到内部资源至 少可以启动一个 Executor 的所有Worker，然后在这些Worker 之间分配 Executor，Worker 上 的 Executor 启动后会向Driver 反向注册，所有的 Executor 注册完成后，Driver 开始执行main
函数，之后执行到Action 算子时，开始划分 Stage，每个 Stage 生成对应的 taskSet，之后将Task 分发到各个 Executor 上执行。  
#### Standalone Client 模式    
![img_4.png](img_4.png)  
在 Standalone Client 模式下，Driver 在任务提交的本地机器上运行。Driver 启动后向 Master 注册应用程序，Master 根据 submit 脚本的资源需求找到内部资源至少可以启动一个 Executor 的所有Worker，然后在这些Worker 之间分配 Executor，Worker 上的 Executor 启动 后会向Driver 反向注册，所有的 Executor 注册完成后，Driver 开始执行main 函数，之后执 行到 Action 算子时，开始划分 Stage，每个 Stage 生成对应的 TaskSet，之后将 Task 分发到
各个 Executor 上执行。  
### spark通讯架构  
#### Spark架构概述  
spark中通讯架构的发展: 
1. 在 Standalone Client 模式下，Driver 在任务提交的本地机器上运行。Driver 启动后向 Master 注册应用程序，Master 根据 submit 脚本的资源需求找到内部资源至少可以启动一个 Executor 的所有Worker，然后在这些Worker 之间分配 Executor，Worker 上的 Executor 启动 后会向Driver 反向注册，所有的 Executor 注册完成后，Driver 开始执行main 函数，之后执 行到 Action 算子时，开始划分 Stage，每个 Stage 生成对应的 TaskSet，之后将 Task 分发到
   各个 Executor 上执行。
2. Spark1.3 中引入Netty 通信框架，为了解决 Shuffle 的大数据传输问题使用
3. Spark1.6 中Akka 和Netty 可以配置使用。Netty 完全实现了Akka 在 Spark 中的功能。
4. Spark2 系列中，Spark 抛弃Akka，使用Netty。
   Spark2.x 版本使用Netty 通讯框架作为内部通讯组件。Spark 基于Netty 新的 RPC 框架 借鉴了Akka 的中的设计，它是基于Actor 模型，Spark2.x 版本使用Netty 通讯框架作为内部通讯组件。Spark 基于Netty 新的 RPC 框架 借鉴了Akka 的中的设计，它是基于Actor 模型，
![img_5.png](img_5.png)  
Spark 通讯框架中各个组件（Client/Master/Worker）可以认为是一个个独立的实体，各 个实体之间通过消息来进行通信。具体各个组件之间的关系图如下：  
![img_6.png](img_6.png)  
   Endpoint（Client/Master/Worker）有 1 个 InBox 和N个OutBox（N>=1，N取决于当前 Endpoint 与多少其他的Endpoint进行通信，一个与其通讯的其他Endpoint对应一个OutBox），Endpoint 接收到的消息被写入 InBox，发送出去的消息写入OutBox并被发送到其他Endpoint的 InBox
   中。  
### Spark 通讯架构解析 
![img_7.png](img_7.png)  
1. RpcEndPoint:Spark 针对每个节点（Client/Master/Worker）都称之为一 个 RPC 终端，且都实现 RpcEndpoint 接口，内部根据不同端点的需求，设计不同的消 息和不同的业务处理，如果需要发送（询问）则调用 Dispatcher。在 Spark 中，所有的
   终端都存在生命周期。
2. RpcEnv：RPC 上下文环境，每个 RPC 终端运行时依赖的上下文环境称为 RpcEnv；在 把当前 Spark 版本中使用的NettyRpcEnv。
3. Dispatcher：消息调度（分发）器，针对于RPC 终端需要发送远程消息或者从远程RPC 接收到的消息，分发至对应的指令收件箱（发件箱）。如果指令接收方是自己则存入收
   件箱，如果指令接收方不是自己，则放入发件箱；
4. Inbox：指令消息收件箱。一个本地RpcEndpoint 对应一个收件箱，Dispatcher 在每次向 Inbox 存入消息时，都将对应 EndpointData 加入内部ReceiverQueue 中，另外Dispatcher
   创建时会启动一个单独线程进行轮询ReceiverQueue，进行收件箱消息消费；
5. RpcEndpointRef：RpcEndpointRef是对远程 RpcEndpoint 的一个引用。当我们需要向一 个具体的RpcEndpoint 发送消息时，一般我们需要获取到该RpcEndpoint 的引用，然后
   通过该应用发送消息。
6. OutBox：指令消息发件箱。对于当前RpcEndpoint 来说，一个目标RpcEndpoint 对应一 个发件箱，如果向多个目标RpcEndpoint发送信息，则有多个OutBox。当消息放入Outbox 后，紧接着通过TransportClient 将消息发送出去。消息放入发件箱以及发送过程是在同
   一个线程中进行；
7. RpcAddress：表示远程的RpcEndpointRef的地址，Host + Port。
8. TransportClient：Netty通信客户端，一个OutBox对应一个TransportClient，TransportClient 不断轮询OutBox，根据OutBox 消息的 receiver 信息，请求对应的远程 TransportServer；

### 第四章 Spark任务调度器  
 在生产环境下，Spark 集群的部署方式一般为YARN-Cluster 模式，之后的内核分析内容 中我们默认集群的部署方式为 YARN-Cluster 模式。  
Driver 线程 主要是初始化 SparkContext 对象，准备运行所需的上下文，然后一方面保持与 ApplicationMaster 的 RPC 连接，通过 ApplicationMaster 申请资源，另一方面根据用户业务
逻辑开始调度任务，将任务下发到已有的空闲 Executor 上。  
 当ResourceManager向ApplicationMaster返回Container资源时，ApplicationMaster就尝试在对应的Container上启动Executor进程，Executor进程起来后，会向Driver反向注册，注册成功后保持与Driver的心跳，同时等待Driver分发任务，当分发的任务执行完毕后，将任务状态上报给Driver。
#### Spark任务调度概述  
当 Driver 起来后，Driver 则会根据用户程序逻辑准备任务，并根据 Executor 资源情况 逐步分发任务。在详细阐述任务调度前，首先说明下 Spark 里的几个概念。一个 Spark 应用
程序包括 Job、Stage 以及 Task 三个概念：
1. Job 是以Action方法为界，遇到一个Action方法触发一个Job。
2. stage是JOB的子集,以RDD宽依赖为界，遇到Shuffle做一次划分。
3. Task 是 Stage 的子集，以并行度(分区数)来衡量，分区数是多少，则有多少个 task。
   park 的任务调度总体来说分两路进行，一路是 Stage 级的调度，一路是 Task 级的调度，总 体调度流程如下图所示  
![img_8.png](img_8.png)  
4. 

