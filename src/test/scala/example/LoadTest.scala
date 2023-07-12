package example

import Simulation._
import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.http.Predef.http

class LoadTest extends Simulation{

  val config = ConfigFactory.load("app.conf")
  val httpConf = http.baseUrl(config.getString("conf.baseUrl"))

  setUp(
    //Эти сценарии работают параллельно
    loginLogoutScenario.inject(
      constantUsersPerSec(config.getInt("conf.users")) during config.getInt("conf.during")
    ).protocols(httpConf),

    searchTicketWithoutPayment.inject(
      constantUsersPerSec(config.getInt("conf.users")) during config.getInt("conf.during")
    ).protocols(httpConf),

    viewingTheItinerary.inject(
      constantUsersPerSec(config.getInt("conf.users")) during config.getInt("conf.during")
    ).protocols(httpConf),

    randomUserRegScenario.inject(
      constantUsersPerSec(config.getInt("conf.users")) during config.getInt("conf.during")
    ).protocols(httpConf),

    //эти сценарии работаю последовательно, так как если не будет купленного билета, то и нечего удалять будет
    buyScenario.inject(
      constantUsersPerSec(config.getInt("conf.users")) during config.getInt("conf.during")
    ).protocols(httpConf)
      .andThen(deleteBookingScenario.inject(
        constantUsersPerSec(config.getInt("conf.users")) during config.getInt("conf.during")
      ).protocols(httpConf))
  )

}
