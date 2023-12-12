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