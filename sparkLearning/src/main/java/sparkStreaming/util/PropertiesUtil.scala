package sparkStreaming.util

import java.io.InputStreamReader
import java.util.Properties

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/12/4 10:21
 */
object PropertiesUtil {

  def load(propertiesName: String):Properties = {

    val prop = new Properties()
    prop.load(
      new  InputStreamReader(Thread.currentThread().getContextClassLoader.getResourceAsStream(propertiesName),"UTF-8")))

    prop
  }
}
