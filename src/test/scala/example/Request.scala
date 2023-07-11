package example

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.util.Random

object Request {

  val alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

  val username = randStr(3)
  val pass = randStr(3)
  val firstName = randStr(5)
  val lastName = randStr(6)
  val address1 = randStr(8)
  val address2 = randStr(8)

  val csvLoginFeeder = csv("csv/login.csv").eager
  val csvTicketFeeder = csv("csv/ticketsParams.csv").eager
  val csvPaymentFeeder = csv("csv/paymentCreds.csv").eager

      def homepage = {
        exec(
          http("homePage")
            .get("/WebTours")
            .resources(
              http("[homePage]_getWelcome.pl?signOff=true")
                .get("/cgi-bin/welcome.pl?signOff=true")
                .check(status.is(200))
            )
          )
        }

        def userSession = {
          exec(
            http("getUserSession")
              .get("/cgi-bin/nav.pl?in=home")
              .check(css("input[name='userSession']", "value").saveAs("userSession"))
              .check(status is 200)
          )
        }

      def registration = {
        exec(
          http("enter")
            .get("/cgi-bin/login.pl?username=&password=&getInfo=true")
        )
          .pause(1)
          .exec(
            http("register")
              .post("/cgi-bin/login.pl")
              .formParam("username", username)
              .formParam("password", pass)
              .formParam("passwordConfirm", pass)
              .formParam("firstName", firstName)
              .formParam("lastName", lastName)
              .formParam("address1", address1)
              .formParam("address2", address2)
              .formParam("register.x", "67")
              .formParam("register.y", "14")
              .check(status is 200)
          )
      }

      def login = {
        feed(csvLoginFeeder)
          .exec(
            http("login")
              .post("/cgi-bin/login.pl")
              .formParam("userSession", "#{userSession}")
              .formParam("username", "#{username}")
              .formParam("password", "#{password}")
              .formParam("login.x", "#{loginx}")
              .formParam("login.y", "#{loginy}")
              .formParam("JSFormSubmit", "off")
              .resources(
                http("[login]_getLogin.pl?intro=true")
                  .get("/cgi-bin/login.pl?intro=true"),
                http("[login]_getNav.pl?page=menu&in=home")
                  .get("/cgi-bin/nav.pl?page=menu&in=home")
              )
              .check(status is 200)
          )
      }

      def search = {
        exec(
          http("Search")
            .get("/cgi-bin/welcome.pl?page=search")
            .resources(
              http("[Search]_getReservations.pl?page=welcome")
                .get("/cgi-bin/reservations.pl?page=welcome"),
              http("[Search]_getNav.pl?page=menu&in=flights")
                .get("/cgi-bin/nav.pl?page=menu&in=flights"),
            )
            .check(status is 200)
        )
      }

     def choiceTicket = {
       feed(csvTicketFeeder)
         .exec(
           http("choiceTicket")
             .post("/cgi-bin/reservations.pl")
             .formParam("advanceDiscount", "0")
             .formParam("depart", "#{depart}")
             .formParam("departDate", "#{departDate}")
             .formParam("arrive", "#{arrive}")
             .formParam("returnDate", "#{returnDate}")
             .formParam("numPassengers", "#{numPassengers}")
             .formParam("seatPref", "#{seatPref}")
             .formParam("seatType", "#{seatType}")
             .formParam("findFlights.x", "#{findFlightsx}")
             .formParam("findFlights.y", "#{findFlightsy}")
             .formParam(".cgifields", "roundtrip")
             .formParam(".cgifields", "seatType")
             .formParam(".cgifields", "seatPref")
             .check(css("input[name='outboundFlight']", "value").saveAs("outboundFlight"))
             .check(status is 200)
         )
     }

       def bookingTicket = {
         exec(
           http("bookingTicket")
             .post("/cgi-bin/reservations.pl")
             .formParam("outboundFlight", "#{outboundFlight}")
             .formParam("numPassengers", "#{numPassengers}")
             .formParam("advanceDiscount", "0")
             .formParam("seatPref", "#{seatPref}")
             .formParam("seatType", "#{seatType}")
             .formParam("findFlights.x", "#{findFlightsx}")
             .formParam("findFlights.y", "#{findFlightsy}")
             .check(status is 200)
         )
       }

       def payTicket = {
         feed(csvPaymentFeeder)
           .exec(
             http("payTicket")
               .post("/cgi-bin/reservations.pl")
               .formParam("firstName", "#{firstName}")
               .formParam("lastName", "#{lastName}")
               .formParam("address1", "#{address1}")
               .formParam("address2", "#{address2}")
               .formParam("pass1", "#{pass1}")
               .formParam("creditCard", "#{creditCard}")
               .formParam("expDate", "#{expDate}")
               .formParam("oldCCOption", "")
               .formParam("numPassengers", "#{numPassengers}")
               .formParam("seatType", "#{seatType}")
               .formParam("seatPref", "#{seatPref}")
               .formParam("outboundFlight", "#{outboundFlight}")
               .formParam("advanceDiscount", "0")
               .formParam("returnFlight", "")
               .formParam("JSFormSubmit", "off")
               .formParam("buyFlights.x", "#{buyFlightsx}")
               .formParam("buyFlights.y", "#{buyFlightsy}")
               .formParam(".cgifields", "saveCC")
               .check(status is 200)
           )
       }

      def deleteBooking = {
        exec(
          http("bookingPage")
            .get("/cgi-bin/welcome.pl?page=itinerary")
            .resources(
              http("[bookingPage]_/cgi-bin/nav.pl?page=menu&in=itinerary")
                .get("/cgi-bin/nav.pl?page=menu&in=itinerary"),
              http("[bookingPage]_/cgi-bin/itinerary.pl")
                .get("/cgi-bin/itinerary.pl")
                .check(css("input[name='flightID']", "value").saveAs("flightID")),
            )
        )
          .exec(
            http("deleteBooking")
              .post("/cgi-bin/itinerary.pl")
              .formParam("1", "on")
              .formParam("flightID", "#{flightID}")
              .formParam("removeFlights.x", "62")
              .formParam("removeFlights.y", "11")
              .formParam(".cgifields", "1")
              .check(status is 200)
              .check(substring("#{flightID}").notExists)
          )
      }

      def logout = {
        exec(
          http("logOut")
            .get("/cgi-bin/welcome.pl?signOff=1")
            .resources(
              http("[logOut]_getHome.html")
                .get("/WebTours/home.html"),
              http("[logOut]_getNav.pl?in=home")
                .get("/cgi-bin/nav.pl?in=home"),
            )
            .check(status is 200)
      )
  }

  def randStr(n: Int) = (1 to n).map(_ => alpha(Random.nextInt(alpha.length))).mkString

  def printer(param : String) = {
    exec {
      session =>
        println(session(param).as[String])
        session
    }
  }

}
