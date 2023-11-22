package wc

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/20 10:15
 */
object wordCount {

  def main(args: Array[String]): Unit = {
    
    // 创建spark运行对象
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("wordCount")
  
    // 创建spark 上下文环境对象
    val sc : SparkContext = new SparkContext(sparkConf)

      // 读取文件
      val fileRDD: RDD[String] = sc.textFile("input/word.txt")

    // 将文件进行分词
    val wordRDD:RDD[String] =  fileRDD.flatMap(_.split(""))

    // 转换数据结构 word =》(word,1)
    val word2OneRDD: RDD[(String, Int)] = wordRDD.map((_,1))

    // 将数据分组聚合

    val word2ContRDD:RDD[(String,Int)] = word2OneRDD.reduceByKey(_+_)

    // 将数据 聚合结果采集到内存
    val word2Count:Array[(String,Int)] = word2ContRDD.collect()

    // 打印结果
    word2Count.foreach(println)

    // 关闭Spark连接
    sc.stop()
  }

}
