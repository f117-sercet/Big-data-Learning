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
![img.png](img/img.png)  
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


#### 序列化  
#### 什么是序列化  
   序列化就是把内存中的对象，转换成字节序列或其它数据传输协议，以便存储到磁盘和网络传输。  
#### Hadoop序列化特点  
1) 紧凑：高效使用存储空间
2) 快速：读写数据的额外开销小
3) 互操作：支持多语言的交互  
###### bean对象实现序列化的步骤  
1) 实现Writable接口  
2) 反序列化时，需要反射调用空参构造函数，所以必须有空参构造。
```java
public void FlowBean(){
    super()
        }
```
3) 重写序列化方法
```java
@Override
public void write(DataOutput out) {
        out.writeLong(upFlow);

        out.writeLong(downFlow);

        out.writeLong(sumFlow);

        }
```      
4) 重写序列化方法
```java
@Override
public void readFiles(DataOutput out)

        upFlow = in.readLong();

        downFlow= in.readLong();

        sumFlow= in.readLong();



```  
（5）注意反序列化的顺序和序列化的顺序完全一致

（6）要想把结果显示在文件中，需要重写toString()，可用"\t"分开，方便后续用。

（7）如果需要将自定义的bean放在key中传输，则还需要实现Comparable接口  
#### MapReduce框架原理  
![img_2.png](img/img_2.png)  
##### InputFormat 数据输入
###### 切片与MapTask 并行度决定机制  
MapTask的并行度决定Map阶段的任务处理并发度，进而影响到整个Job的处理速度。  
1) MapTask并行度决定机制  
数据块：Blocks是物理上把数据分成一块儿一块儿，数据块是HDFS存储数据单位。  
数据切片：数据切片只是在逻辑上对输入进行分片，并不会在磁盘将其分片存储。数据切片是Mapreduce程序计算输入数据的单位，一个切片会对应启动一个MapTask。  
![img_3.png](img/img_3.png)    
#### Job提交示例流程  
![img_4.png](img/img_4.png)  
##### FileInputFormat切片源碼分析  
1) 程序先找到你数据存储的目录。
2) 开始遍历处理目录下的每一个文件
3) 遍历第一个文件.txt   
   1) 获取文件大小fs.sizeOf(ss.text)
   2) 计算切片大小
   3) 默认情况下，切片大小=blocksize
   4) 开始切，形成切片(每次切片时，都要判断切完剩下的部分是否大于块的1.1倍，不大于1.1就划分一块切片) 
   5) 将切片信息写到一个切片规划文件中
   6) 整个切片的核心过程在getSplit()方法中完成。
   7) InputSplit 只记录了切片的元数据信息，比如起始位置，长度以及所在节点列表等。
4) 提交切片到Yarn上，YARN的MrAppMaster就可以根据切片规划文件计算开启MapTask个数。
#### FileInputFormat切片机制  
######  切片机制  
1)  简单地寻找文件的长度进行切片。 
2) 切片大小，默认等于block大小。  
3) 切片时不考虑数据集整体，而是逐个针对每一个单独文件切片。   
![img_5.png](img/img_5.png)  
##### 切片大小设置
maxsize(切片最大值):参数如果调得比blockSize小，则会让切片变小，而且就等于配置的这个参数的值。  
minsize(切片最小值):参数调的比blockSize大,则可以让切片变得比blockSize还大。  
##### 获取切片信息API  
```java 
public class test {
    String name = inputSplit.getPath().getName();
    // 根据文件类型获取切片信息
    FileSplit inputSplit = (FileSplit) context.getInputSplit();
}
```    

### TextInputFormat  
#### FileInputFormat实现类  
![img_6.png](img/img_6.png)     
FileInput 常见的接口实现类包括：TextInputFormat、keyValueTextInputformat等。  
TextInputFormat是默认的FileInputFormat实现类，按行读取每条记录。键是存储该行在整个文件中的起始字节偏移量，LongWritable类型，值是这行的内容，不包括任何行终止符。  
### CombineTextInputFormat切片机制  
框架默认的TextInputforMat切片机制是对任务按文件规划切片，不管文件多小，都会是一个单独的切片，都会交给一个MapTask，如果有大量小文件的，就会产生大量的MapTask,处理效率低下。
1) 应用场景：  
  CombineTextInputFormat 用于小文件过多的场景，它可以将多个小文件从逻辑上规划到一个切片中，这样，多个小文件就可以交给一个MapTask处理。
2) 虚拟存储器切片最大值设置
   CombineTextInputFormat.setMaxInputSplitSize(job, 4194304) // 4MB

##### MapReduce详细工作流程  
![img_7.png](img/img_7.png)  
![img_8.png](img/img_8.png)  
具体Shuffle过程详解：  
1) MapTask 收集我们的map方法输出的kv对，放到内存缓冲区。
2) 从内存缓冲区不断溢出本地磁盘文件，可能会溢出多个
3) 多个溢出文件会被合并成大的溢出文件
4) 在溢出过程及合并过程中，都要调用Partitioner进行分区和针对key进行排序
5) ReduceTask 根据自己的分区号,去各个MapTask机器上取相应的结果
6) ReduceTask会抓取到同一个分区的来自不同MapTask的结果文件，ReduceTask会将这些文件在进行合并(归并排序)
7) 合并称为大文件后,Shuffle的过程也就结束了，后面进入ReduceTask的逻辑运算过程。
###### 注意：  
1) Shuffle中的缓冲区大小会影响到MapReduce程序的执行效率，原则上说，缓冲区越大，磁盘io的次数越少，执行速度就越快。  
2) 缓冲区的大小可以通过参数调整。参数：mapreduce.task.io.sort.mb 默认 100M。
#### Shuffle机制  
#### 1.1 机制  
Map方法之后，Reduce方法之前的数据处理过程称之为Shuffle。
![img_9.png](img/img_9.png)
#### 1.2 partition 分区  
![img_10.png](img/img_10.png)  
1. 默认分区是根据key的hashCode对ReduceTask个数取模得到的。用户没法控制哪个key存储到哪个分区。
2. 自定义partitioner步骤
   1. 自定义继承Partitioner,重写getPartition()方法
   2. 在Job中，设置自定义Partitioner
   3. 自定义Partition后，要根据自定义Partitioner的逻辑设置相应数量的ReduceTask。
3. partition总结 
   1. 如果ReduceTask的数量>getPartition的结果数，则会多产生几个空的输出文件part-r-00xx;
   2. 如果1< ReduceTask的数量<getPartition的结果数,则有一部分分区数据无处安放,会Exception。
   3. 如果等于一，则不管MapTask端输出多少个分区文件，最终结果都交给这一个ReduceTask,最终也就只会产生一个结果文件part-r-00000;
   4. 分区号必须从零开始，逐一增加。
### WritiableComparable 排序  
##### 1.排序概述  
MapTask和ReduceTask均会对数据按照key进行排序。该操作属于Hadoop的默认行为。任何程序中的数据均会被排序，而不管逻辑上否需要。
默认排序是按照字段顺序排序，且实现该排序的方法是快速排序。对于MapTask。它会处理的结果暂时放到环形缓冲区中,当环形缓冲区使用率达到一定阈值后，在对缓冲区中的数据进行一次快速排序，并将质这些有序数据溢写到磁盘上，而当发数据处理完毕后，它会对磁盘上所有文件进行归并排序。  
对于ReduceTask,它会从每个MapTask上远程拷贝相应的数据文件，如果大文件大小超过一定阈值，则溢写磁盘上，否则存储在内存中，如果磁盘文上文件数目达到一定阈值，则进行一次归并排序以生成一个更大的文件；如果内存中文件大小或数目超过一定阈值，则进行一次合并后将数据溢写到磁盘上。
当所有数据拷贝完毕后，ReduceTask同意对内存和磁盘上的所有数据进行一次归并排序。  
##### 2.排序分类  
 1) 部分排序  
    MapReduce根据输入记录的键对数据集排序。保证输出的每个文件内部有序。
2)  全排序
    最终输出结果只有一个文件，且文件内部有序。实现方式是只设置一个ReduceTask。但该方法i在处理大型文件时效率低下，完全丧失了MapReduce所提供的并行架构。
3) 辅助排序
   在Reduce端对key进行分组。应用于：在接收的key为value对象时，想让一个或几个字段相同的key进入到同一个reduce方法时，可以采用分组排序。
4) 二次排序
   在自定义排序过程中，如果compareTo的判断条件为两个，即为二次排序。
### Combiner合并  
#### Combine合并 
1) Combiner是MR程序中Mapper和Reducer之外的一种组件。
2) Combiner组件的父类就是Reducer。
3) Combiner和Reducer的区别在于运行的位置。
   1. Combiner是在每一个MapTask所在的节点运行。
   2. Reducer是接收全局所有Mapper的输出结果。
4) Combiner的意义就是对每一个MapTask的输出进行局部汇总，以减小网络传输量。
5) Combiner能够应用的前提是不能影响最终的业务逻辑,而且，Combiner的输出KV应该跟Reducer的输入kv类型要对应起来。  
### OutputFormat数据输出  
#### OutputFormat接口实现类  
outputFormat是MapReduce输出的基类,所有实现MapReduce输出都实现了OutPutFormat接口。

### MapTask工作机制  
![img_11.png](img/img_11.png)  
1) Read阶段: MapTask通过InputFormathu获得的RecordReader,从输入InputSplit中解析出一个个key/value
2) Map阶段：该节点主要是将解析出的key/value 交给用户编写map()函数处理，并产生一系列新的key/value.
3) Collect收集阶段：在用户编写map()函数中,当数据处理完后,一般会调用OutPutCollector.collect()输出结果。在函数内部,他将生成的key/value分区，并写入一个环形内的缓冲区中间。
4) Split阶段:溢写阶段，当环形缓冲区满后，MapReduce会将数据写到本地磁盘上，生成一个临时文件。需要注意的是，将数据写入本地磁盘之前，先要对数据进行一次本地排序，并在必要时对数据进行合并，压缩等操作。  
###### 溢写阶段
   1. 利用快速排序算法对缓存区内的数据进行排序，排序方式是：先按照分区编号Partition进行排序，然后按照key进行排序。这样经过排序后，数据以分区为单位聚集在一起，且同一个分区内所有数据按照key有序。
   2. 按照分区数据的元信息写到内存索引数据结构SpillRecord中，其中每个分区的元信息包括在临时文件中的偏移量，压缩前数据大小和压缩后数据大小。如果当前内存索引大小超过1MB,则将内存索引写到文件outPut/spillN.out.index中。
###### Merge 阶段  
当所有数据处理完成后,MapTask对所有临时文件进行一次合并，以确保最终只会生成一个数据文件。 当所有数据处理完成后，MapTask会将所有临时文件合并成一个大文件，并保存到文件output/file.out中，同时生成相应的索引文件output/file.out.index。 在进行文件合并过程中，MapTask以分区为单位进行合并。对某个分区，它将采用多轮递归合并的方式。每轮合并mapreduce.task.io.sort.factor(默认10)个文件，并将产生的文件重新加入待合并列表中，对文件排序后，重复上述过程，直到最终得到一个大文件。  
让每个MapTask最终只生成一个数据文件，可避免同时打开大量文件和同时读取大量小文件产生的随机读取带来的开销。  

### ReduceTask工作机制  
![img.png](img/reduceTask工作机制.png)  
1) Copy阶段:ReduceTask从各个MapTask上远程拷贝一片数据，并针对某一片数据，如果其大小超过一定阈值，则写到磁盘上，否则直接放到内存中。
2) Sort阶段：在远程拷贝数据的同时，ReduceTask启动了两个后台线程对内存和磁盘上的文件进行合并，以防止内存使用过多或磁盘上文件过多。按照MapReduce语义，用户编写reduce()函数输入数据是按key进行聚集的一组数据。为了将key相同的数据聚在一起，Hadoop采用了基于排序的策略。由于各个MapTask已经实现对自己的处理结果进行了局部排序，因此，ReduceTask只需对所有数据进行一次归并排序即可。
3) Reduce阶段:reduce() 函数将计算结果写道HDFS。  
### ReduceTask并行度决定机制  
1) 设置ReduceTask 的并行度 同样影响 整个Job的执行并发度和执行效率，但与MapTask的并发数由切片数决定不同，ReduceTask数量的决定是可以直接手动设置。
```java
// 默认是1,手动设置为4
job.setNumReduceTasks(4);
```
#### 注意事项  
1. ReduceTask=0，表示没有Reduce阶段，输出文件个数和Map个数一致。
2. ReduceTask 默认值就是1，所以输出文件个数为一个。
3. 如果数据分布不均匀,就可能在Reduce阶段数据倾斜。
4. ReduceTask数量并不是任意设置，还要考虑业务逻辑需求，有些情况下，需要计算全局汇总结果，就只能有一个ReduceTask。  
### Join应用  
#### Reduce Join 
Map 端的主要工作：为来自不同表或者文件的key/value，打标签以区别不同来源的记录。然后用连接字段作为key,其余部分和新加的标志作为value，最后输出。
#### 总结
缺点：  
  这种方式中,合并的操作是在Reduce阶段完成，Reduce的处理压力太大，Map节点的运算负载低，资源利用率不高，且在Reduce阶段极易产生数据倾斜。  
解决方案：  
Map实现数据合并。
#### MapJoin  
1) 使用场景  
   MapJoin适用于一张表十分小，一张表很大的场景。  
2) 优点  
   Map端缓存多张表，提前处理业务逻辑，这样增加Map端业务,减少Reduce端数据的压力,尽可能的减少数据倾斜。
3) 具体办法
   1. 在Mapper的setup阶段,将文件读取到缓存合集中
   2. 在Driver驱动类中加载缓存。
```java
// 缓存普通文件到Task运行节点  
job.addCacheFile(new URI("file:///e:/cache/pd.txt"))
//如果是集群运行，需要设置HDFS路径  
job.addCacheFile(new URI("hdfs:// hadoop102：8020/cache/pd.txt"));
```
##### Map端合并案例分析  
![img.png](img/Map端合并.png)


















