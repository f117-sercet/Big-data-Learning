package sparkStreaming.practice

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/12/4 9:45
 */
/** *
 * 城市信息表 *
 * @param city_id 城市 id *
 * @param city_name
 * 城市名称 * @param area
城市所在大区
 */
case class CityInfo(
                     city_id: Long,
                     city_name: String,
                     area: String
                   )
