package sparkStreaming.practice.util

import java.sql.{Connection, PreparedStatement, ResultSet}
import java.util.Properties
import javax.sql.DataSource
import com.alibaba.druid.pool.DruidDataSourceFactory

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/12/5 14:29
 */
object JdbcUtil {


  //初始化连接池
  var dataSource:DataSource = init()
  def init(): DataSource = {

    val properties = new Properties()
    val config = PropertiesUtil.load("config.properties")

    properties.setProperty("driverClassName", "com.mysql.jdbc.Driver")
    properties.setProperty("url", config.getProperty("jdbc.url"))
    properties.setProperty("username", config.getProperty("jdbc.user"))
    properties.setProperty("password", config.getProperty("jdbc.password"))
    properties.setProperty("maxActive",config.getProperty("jdbc.datasource.size"))
    DruidDataSourceFactory.createDataSource(properties)
  }

  // 获取Mysql连接
  def getConnection:Connection={
    dataSource.getConnection
  }
  // 执行SQL语句，单条数据插入
  def executeUpdate(connection: Connection, sql: String, params: Array[Any]): Int = { var rtn = 0
    var pstmt: PreparedStatement = null

    try {
      connection.setAutoCommit(false)
      pstmt = connection.prepareStatement(sql)
      if (params != null && params.length > 0)
      { for (i <- params.indices) { pstmt.setObject(i + 1, params(i))
      }
      }
      rtn = pstmt.executeUpdate()
      connection.commit()
      pstmt.close()
    } catch {
      case e: Exception => e.printStackTrace()
    }
    rtn
  }
  // 执行SQL语句,批量数据插入
  def executeBatchUpdate(connection: Connection, sql: String, paramsList: Iterable[Array[Any]]): Array[Int] ={

    var rtn:Array[Int] = null
    var pstmt: PreparedStatement = null

    try {
      connection.setAutoCommit(false)
      connection.prepareStatement(sql)
      for (params <- paramsList) {
        if (params!=null && params.length >0) {
          for (elem <- params.indices) {
            pstmt.setObject(elem+1,params(elem))
          }
          pstmt.addBatch()
        }
      }
       rtn = pstmt.executeBatch()
      connection.commit()
      pstmt.close()

    }catch {
      case e: Exception =>e.printStackTrace()
    }
    rtn

  }

  // 判断一条数据是否存在
  //判断一条数据是否存在
   def isExist(connection: Connection, sql: String, params: Array[Any]): Boolean =
  {
    var flag: Boolean = false
    var pstmt: PreparedStatement = null
    try {
      pstmt = connection.prepareStatement(sql)
      for (i <- params.indices) {
        pstmt.setObject(i + 1, params(i))
      }
      flag = pstmt.executeQuery().next()
      pstmt.close()
    } catch {
      case e: Exception => e.printStackTrace()
    }
    flag
  }
  //获取 MySQL 的一条数据
  def getDataFromMysql(connection: Connection, sql: String, params: Array[Any]): Long = {
  var result: Long = 0L
    var pstmt: PreparedStatement = null
    try {
    pstmt = connection.prepareStatement(sql)
      for (i <- params.indices) { pstmt.setObject(i + 1, params(i))
    }
     val resultSet: ResultSet = pstmt.executeQuery()
      while (resultSet.next()) { result = resultSet.getLong(1)
    }
    resultSet.close()
      pstmt.close()
    } catch
      { case e: Exception => e.printStackTrace()
  }
      result
}

}
