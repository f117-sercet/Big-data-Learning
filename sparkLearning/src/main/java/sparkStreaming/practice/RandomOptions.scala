package sparkStreaming.practice

import scala.collection.mutable.ListBuffer
import scala.util.Random

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/12/4 9:47
 */
case class RanOpt[T](value: T, weight: Int)
object RandomOptions {

  def apply[T](opts:RanOpt[T]*):RandomOptions[T] = {

    var randomOptions = new RandomOptions[T]();
    for (opt <- opts) {
       randomOptions.totalWeight += opt.weight
      for (i <- 1 to opt.weight) {
         randomOptions.optsBuffer += opt.value
      }
    }
    randomOptions
  }

}
class RandomOptions[T](opts: RanOpt[T]*){

  var totalWeight = 0
  var optsBuffer = new ListBuffer[T]

  def getRandomOpt:T={
    val randomNum = new Random().nextInt(totalWeight)
    randomNum
    optsBuffer(randomNum)
  }

}
