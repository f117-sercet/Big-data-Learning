package framework.controller

import framework.common.TController
import framework.service.WordCountService

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/23 15:19
 */
class WordCountController extends TController{

  private val wordCountService = new WordCountService()
  override def dispatch(): Unit = {

    //TODO:执行业务操作
    val array = wordCountService.dataAnalysis()
    array.foreach(println)
  }
}
