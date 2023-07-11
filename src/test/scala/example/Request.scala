package example

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Request {
  private val regHeaders = Map(
    "Cache-Control" -> "max-age=0",
    "Sec-Fetch-Dest" -> "document",
    "Sec-Fetch-Mode" -> "navigate",
    "Sec-Fetch-Site" -> "same-origin",
    "Sec-Fetch-User" -> "?1",
    "sec-ch-ua" -> """Not.A/Brand";v="8", "Chromium";v="114", "Google Chrome";v="114""",
    "sec-ch-ua-mobile" -> "?0",
    "sec-ch-ua-platform" -> "Windows"
  )

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

      /*    .exec(
            http("request_0")
              .get("/WebTours/home.html")
              .resources(
                http("request_1")
                  .get("/WebTours/header.html"),
                http("request_4")
                  .get("/cgi-bin/welcome.pl?signOff=true"),
              )
          )*/
      /*		.exec {
            session =>
              println(session("userSession").as[String])
              session
          }*/

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

      def logout = {exec(
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

  def printer(param : String) = {
    exec {
      session =>
        println(session(param).as[String])
        session
    }
  }


}
