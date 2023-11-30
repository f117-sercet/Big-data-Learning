package framework.service

import framework.common.TService
import framework.dao.WordCountDao

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/23 15:08
 */
class WordCountService extends TService{

  private val wordCountDao = new WordCountDao()
  override def dataAnalysis() = {

    val array:Array[(String,Int)] = wordCountDao.
      readFile("C:\\Users\\LENOVO\\Desktop\\word.txt")
      .flatMap(_.split(""))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      .collect()
    array

  }
}
