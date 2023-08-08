# MapReduce  
## MapReduce概述  
#### 定义  
MapReduce 是一个分布式运算程序的编程框架，是用户开发”基于Hadoop的数据分析应用“的核心框架。Mapreduce核心功能是将用户编写的业务逻辑代码和自带默认组件整合成一个完整的分布式运算程序，并且运行在一个Hadoop集群上。  
## 优缺点  
### 优点  
1) 易于编程
它简单的实现一些接口，就可以完成一个分布式程序，整个分布式程序可以分布到大量廉价的pc机上运行。
2) 良好的扩展性  
当你的计算资源不能得到满足的时候，可以通过简单的增加机器来扩展它的计算能力。
3) 高容错性
4) 适合PB级别以上海量数据的离线处理
### 缺点  
1) 不擅长实时计算
2) 不擅长流式计算
3) 不擅长DAG(有向无环图)计算  
使用后，每个MapReduce作业的输出结果都会写入到磁盘，会造成大量的磁盘IO，导致性能非常低下。 
#### 核心思想  
![img.png](img.png)  
1) 第一阶段的MapTask并发实例，完全并行运行，互不相干。
2) 第二个阶段的ReduceTask并发实例互不相干，但是他们的数据依赖于上一个阶段的所有MapTask并发实例的输出。  
3) MapReduce编程模型只能包含一个Map阶段和一个Reduce阶段，如果用户的业务逻辑非常复杂，那就只能多个MapReduce程序，串行运行。  
##### MapReduce进程  
一个完整的MapReduce程序在分布式运行时有三类实例进程：
1) MrAppMaster:负责整个程序的过程调度及状态协调。  
2) MapTask：负责Map阶段的整个数据处理流程。
3) ReduceTask:负责Reduce阶段的整个数据处理流程。  
##### MapReduce变成规范  
1) Mapper阶段：
   1) 用户自定义的Mapper要继承自己的父类。
   2) Mapper的输入数据是KV的形式(kv的类型可自定义)
   3) Mapper的输出数据是kv对的形式
   4) Mapper中的业务逻辑写在map()方法中
   5) map()方法对每一个<k,v>调用一次。
2) Reducer阶段
   1) 用户自定义的Reducer要继承自己的父类
   2) Reducer的输入数据类型对应Mapper的输出类型，也是KV
   3) Reducer的业务逻辑写在reduce()方法中
   4) ReduceTask进程对每一组相同的k的<k ,v>组调用一次reduce()方法
3) Driver阶段  
    相当于YARN集群的客户端，用于提交我们只能整个程序到YARN集群，提交的是封装了MapReduce程序相关运行参数的JOB对象。
        