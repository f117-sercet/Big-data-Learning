package framework.common

import framework.util.EnvUtil
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/23 15:02
 */
trait TApplication {

  def start(master:String="local[*]",app:String="Application")(op: =>Unit):Unit={

    val sparkConf = new SparkConf().setMaster(master).setAppName(app)
    val sc = new SparkContext(sparkConf)
    EnvUtil.put(sc)

    try {
      op
    }catch {
      case  ex =>println(ex.getMessage)
    }

    //TODO 关闭连接
    sc.stop()
    EnvUtil.clear()
  }

}
