package example

import Simulation._
import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.http.Predef.http

class LoadTest extends Simulation{

  val config = ConfigFactory.load("app.conf")
  val httpConf = http.baseUrl(config.getString("conf.baseUrl"))

  setUp(
    scn.inject(
      constantUsersPerSec(config.getInt("conf.users")) during config.getInt("conf.during")
    ).protocols(httpConf)
  )


}
