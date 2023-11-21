# spark运行框架   
#### 运行架构  
Spark框架的核心是一个计算引擎，整体来说，它采用了标准master-slaver的结构。Driver表示master，负责整个集群中的作业调度。Executor则是slave。负责执行任务。
![img.png](img.png)  
#### 核心组件  
Spark 驱动器节点，用于执行Spark任务中的main方法，负责实际代码的执行工作。Driver在Spark作业执行时主要负责： 
1. 将用户程序转化为作业(job)
2. 在Executor之间调度任务
3. 跟踪Executor的执行情况
4. 通过UI展示查询运行情况。
#### Executor 
1. 负责运行组成spark 应用的任务，并将结果返回给驱动进程。
2. 通过自身的块管理器为用户程序中要哦求缓存的RDD提供内存式存储。RDD是直接缓存在Executor进程内的，因此任务可以在运行时充分利用缓存数据加速运算。  
#### Master&Worker 
spark集群的独立部署环境中，不需要依赖其他的资源调度框架，自身就实现了资源调度的功能，所以环境中还有其他两个核心组件，Master和Worker，这里的Master是一个进程，主要负责资源的调度和分配，并进行集权的监控等职责，类似于Yarn环境中的RM，一个Worker运行在集群中的一台服务器上，由Master分配资源对数据进行并行的处理和计算，类似于Yarn环境中的NM.
#### ApplicationMaster  
Hadoop用户向Yarn集群提交应用程序时，提交程序中应该包含ApplicationMaster，用于向资源调度器申请执行任务的资源容器Container，运行用户自己的程序任务Job,加农整个任务的执行，跟踪整个任务的状态，处理任务失败等异常情况。
## 核心概念
### Executor与Core
  Spark Executor是集群中运行在工作节点(Worker)中的一个JVM进程，是整个集群中的专门用与计算的节点。在提交应用中，可以提供餐无数指定计算节点的个数，以及对应的资源。这里的资源一般指的是工作节点Executor的内存大小和使用的虚拟CPU和数量。
  ![img_1.png](img_1.png)  
### 并行度    
在分布式计算框架中一般都是指多个任务同时执行，由于任务分布在不同的计算节点进行计算，所以能够真正的实现多任务并行执行。
### 有向无环图(DAG)
![img_2.png](img_2.png)  
由spark程序直接映射成的数据流的高级抽象模型，简单理解就是将整个程序计算的执行过程用图形表示出来，这样更直观，更便于立=理解，可以用于表示程序的拓扑结构。
DAG有向无环图是由点和线组成的拓扑图形，该图形具有方向，不会闭环。
### 提交流程
所谓的提交流程，其实就是我们开发人员根据需求写的应用程序通过spark客户端提交给spark运行环境执行计算的流程。
![img_3.png](img_3.png)  
Spark 应用程序提交到Yarn 环境中执行的时候，一般会有两种部署执行的方式：Client 和Cluster。两种模式主要区别在于：Driver 程序的运行节点位置。  
### YarnClient模式  
Client模式用于监控和调度的Driver模块在客户端执行，而不是在Yarn中，所以一般用于测试。  
1. Driver 在任务提交的本地机器上运行。
2. Driver 启动后会和ResourceManager 通讯申请启动ApplicationMaster
3. ResourceManager 分配 container，在合适的NodeManager 上启动ApplicationMaster，负 责向ResourceManager 申请 Executor 内存。
4. ResourceManager 接到ApplicationMaster 的资源申请后会分配 container，然后 ApplicationMaster 在资源分配指定的NodeManager 上启动 Executor 进程。
5. Executor 进程启动后会向Driver 反向注册，Executor 全部注册完成后Driver 开始执行 main 函数。
6. 之后执行到Action 算子时，触发一个 Job，并根据宽依赖开始划分 stage，每个 stage 生 成对应的TaskSet，之后将 task 分发到各个 Executor 上执行。
### YarnCluster 模式  
Cluster 模式将用于监控和调度的Driver 模块启动在Yarn 集群资源中执行。一般应用于 实际生产环境。
1. 在Yarn Cluster模式下，任务提交后会和ResourceManager通讯申请启动ApplicationMaster。
2. 随后ResourceManager分配 container，在合适的NodeManager 上启动ApplicationMaster， 此时的ApplicationMaster 就是Driver。
3. Driver 启动后向ResourceManager 申请 Executor 内存，ResourceManager 接到 ApplicationMaster 的资源申请后会分配 container，然后在合适的NodeManager 上启动
   Executor 进程。
4. Executor 进程启动后会向Driver 反向注册，Executor 全部注册完成后Driver 开始执行 main 函数。
5. 后执行到Action 算子时，触发一个 Job，并根据宽依赖开始划分 stage，每个 stage 生 成对应的 TaskSet，之后将 task 分发到各个 Executor 上执行

## 核心编程  
 spark 计算框架为了能够进行高并发和高吞吐的额数据处理，提供了了三种不同的数据结构，用于处理不同的应用场景，分别为：
 1. RDD：弹性数据分析
 2. 累加器：分布式共享只写变量
 3. 广播变量。  
### RDD 
RDD（Resilient Distributed Dataset）叫做弹性分布式数据集，是 Spark 中最基本的数据 处理模型。代码中是一个抽象类，它代表一个弹性的、不可变、可分区、里面的元素可并行
计算的集合。  
1. 弹性
   1. 存储的弹性:内存与磁盘的自动切换；
   2. 容错的弹性：数据丢失可以自动恢复；
   3. 计算的弹性：计算出错重试机制;
   4. 分片的弹性:可根据需要重新分片。
2. 分布式：数据存储在大数据集群不同节点上。
3. 数据集:RDD封装了计算逻辑，并不保存数据。
4. 数据抽象：RDD是一个抽象类，需要子类实现。
5. 不可变：RDD封装了计算逻辑，是不可以改变的,想要改变，只能添加新的RDD,在里面封装逻辑。 
6. 可分区、并行计算。
