### scala 简介  
scala 是一门以java虚拟机为运行环境并将面向对象和函数式编程的最佳特性结合在一起的静态类型编程语言。   
   1. scala是一门多范式的百编程语言,Scala支持面向对象和函数式编程。
   2. Scala源代码会被编译成java字节码，然后运行于jvm之上,并可以调用现有的java类库，实现两种语言的无缝对接。
   3. 特点：简洁高效。  
#### 环境搭建  
1. 安装JDK8
2. 安装scala环境
3. 配置环境变量
##### class和object说明  
Scala完全面向对象，故Scala去掉了Java中非面向对象的元素，如static关键字，void类型  
 1. static
    1. Scala无static关键字，由object实现类似静态方法的功能（类名.方法名）。
    2. class关键字和Java中的class关键字作用相同，用来定义一个类；
2. void
   1.对于无返回值的函数，Scala定义其返回值类型为Unit类