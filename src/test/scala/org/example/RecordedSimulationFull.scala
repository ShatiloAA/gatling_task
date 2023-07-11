package org.example

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.core.session
import io.gatling.core.session.Session
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulationFull extends Simulation {

  private val httpProtocol = http
    .baseUrl("http://localhost:1080")

  private val scn = scenario("RecordedSimulationFull")

		/*		.exec(
			http("request_26")
				.get("/cgi-bin/welcome.pl?signOff=1")
				.headers(headers_9)
				.resources(
					http("request_27")
						.get("/WebTours/home.html")
						.headers(headers_1),
					http("request_28")
						.get("/cgi-bin/nav.pl?in=home")
						.check(css("input", "value").saveAs("asd"))
				.headers(headers_9),
					http("request_29")
						.get("/WebTours/images/mer_login.gif")
						.headers(headers_2)
				)
		)
		.exec(addCookie(Cookie("MSO","SID&" +  + "&MSO_ServerErrorsProb&50&MSO_ServerLoadProb&50")))
		.pause(1)
    .exec(
      http("request_0")
        .get("/WebTours")
				.exec()
        .resources(
          http("request_1")
            .get("/WebTours/header.html"),
          http("request_2")
            .get("/cgi-bin/welcome.pl?signOff=true"),
					http("request_6")
						.get("/cgi-bin/nav.pl?in=home")
						.check(regex("""value=\"(.*?)\"""").exists.saveAs("asd")),
					http("request_5")
						.get("/WebTours/home.html")
        )
    )
		//exec(getCookieValue(CookieKey("mso")))
		.exec { session =>
			println(getCookieValue(CookieKey("MSO")))
			session
		}
		.exec {
		session =>
			println(session("asd").as[String])
			session
	}

    .pause(1)
    .exec(
      http("request_8")
        .post("/cgi-bin/login.pl")

        //.formParam("userSession", "136779.413284731HADffQVpAHAiDDDDtccVDpftztcf")
				.formParam("userSession", "#{asd}")
        .formParam("username", "asd")
        .formParam("password", "123")
        .formParam("login.x", "51")
        .formParam("login.y", "16")
        .formParam("JSFormSubmit", "off")
        .resources(
          http("request_9")
            .get("/cgi-bin/login.pl?intro=true")
            .headers(headers_9),
          http("request_10")
            .get("/cgi-bin/nav.pl?page=menu&in=home")
            .headers(headers_9),
          http("request_11")
            .get("/WebTours/images/flights.gif")
            .headers(headers_2),
          http("request_12")
            .get("/WebTours/images/itinerary.gif")
            .headers(headers_2),
          http("request_13")
            .get("/WebTours/images/signoff.gif")
            .headers(headers_2),
          http("request_14")
            .get("/WebTours/images/in_home.gif")
            .headers(headers_2)
        )
    )
    .pause(1)
    .exec(
      http("request_15")
        .get("/cgi-bin/welcome.pl?page=search")
        .headers(headers_9)
        .resources(
          http("request_16")
            .get("/cgi-bin/reservations.pl?page=welcome")
            .headers(headers_9),
          http("request_17")
            .get("/WebTours/images/button_next.gif")
            .headers(headers_2),
          http("request_18")
            .get("/cgi-bin/nav.pl?page=menu&in=flights")
            .headers(headers_9),
          http("request_19")
            .get("/WebTours/images/in_flights.gif")
            .headers(headers_2),
          http("request_20")
            .get("/WebTours/images/itinerary.gif")
            .headers(headers_2),
          http("request_21")
            .get("/WebTours/images/home.gif")
            .headers(headers_2),
          http("request_22")
            .get("/WebTours/images/signoff.gif")
            .headers(headers_2)
        )
    )
    .pause(1)
    .exec(
      http("request_23")
        .post("/cgi-bin/reservations.pl")
        .headers(headers_8)
        .formParam("advanceDiscount", "0")
        .formParam("depart", "Denver")
        .formParam("departDate", "07/07/2023")
        .formParam("arrive", "Frankfurt")
        .formParam("returnDate", "07/08/2023")
        .formParam("numPassengers", "1")
        .formParam("seatPref", "None")
        .formParam("seatType", "Coach")
        .formParam("findFlights.x", "53")
        .formParam("findFlights.y", "5")
        .formParam(".cgifields", "roundtrip")
        .formParam(".cgifields", "seatType")
        .formParam(".cgifields", "seatPref")
    )
    .pause(1)
    .exec(
      http("request_24")
        .post("/cgi-bin/reservations.pl")
        .headers(headers_8)
        .formParam("outboundFlight", "010;386;07/07/2023")
        .formParam("numPassengers", "1")
        .formParam("advanceDiscount", "0")
        .formParam("seatType", "Coach")
        .formParam("seatPref", "None")
        .formParam("reserveFlights.x", "52")
        .formParam("reserveFlights.y", "12")
    )
    .pause(1)
    .exec(
      http("request_25")
        .post("/cgi-bin/reservations.pl")
        .headers(headers_8)
        .formParam("firstName", "first")
        .formParam("lastName", "last")
        .formParam("address1", "asd st., 143")
        .formParam("address2", "city, state, 123456")
        .formParam("pass1", "first last")
        .formParam("creditCard", "1234123412341234")
        .formParam("expDate", "07/25")
        .formParam("oldCCOption", "")
        .formParam("numPassengers", "1")
        .formParam("seatType", "Coach")
        .formParam("seatPref", "None")
        .formParam("outboundFlight", "010;386;07/07/2023")
        .formParam("advanceDiscount", "0")
        .formParam("returnFlight", "")
        .formParam("JSFormSubmit", "off")
        .formParam("buyFlights.x", "36")
        .formParam("buyFlights.y", "5")
        .formParam(".cgifields", "saveCC")
    )
    .pause(1)
    .exec(
      http("request_26")
        .get("/cgi-bin/welcome.pl?signOff=1")
        .headers(headers_9)
        .resources(
          http("request_27")
            .get("/WebTours/home.html")
            .headers(headers_1),
          http("request_28")
            .get("/cgi-bin/nav.pl?in=home")
            .headers(headers_9),
          http("request_29")
            .get("/WebTours/images/mer_login.gif")
            .headers(headers_2)
        )
    )*/

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
