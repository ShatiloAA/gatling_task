package example

import io.gatling.core.Predef._
import Request._
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration._

object Simulation {

  val scn : ScenarioBuilder = scenario("tickets scenario")
    .exec(homepage)
    .pause(1)
    .exec(userSession)
    .pause(1)
    .exec(login)
    .pause(1)
    .exec(search)
    .pause(1)
    .exec(choiceTicket)
    .pause(1)
    .exec(bookingTicket)
    .pause(1)
    .exec(payTicket)
    .pause(1)
    .exec(logout)
}
