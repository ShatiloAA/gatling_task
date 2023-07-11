package example

import io.gatling.core.Predef._
import Request._
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration._

object Simulation {

  val buyScenario : ScenarioBuilder = scenario("login_buy_logout")
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

  val randomUserRegScenario = scenario("reg_logout")
    .exec(homepage)
    .pause(1)
    .exec(registration)
    .pause(1)
    .exec(logout)


  val deleteBookingScenario = scenario("login_deleteBooking_logout")
    .exec(homepage)
    .pause(1)
    .exec(userSession)
    .pause(1)
    .exec(login)
    .pause(1)
    .exec(deleteBooking)
    .pause(1)
    .exec(logout)

}
