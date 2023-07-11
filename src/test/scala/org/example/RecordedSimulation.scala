package org.example

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation extends Simulation {

  private val httpProtocol = http
    .baseUrl("http://localhost:1080")
    .inferHtmlResources()
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")
  
  private val headers_0 = Map(
  		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
  		"Accept-Encoding" -> "gzip, deflate, br",
  		"Accept-Language" -> "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7",
  		"Sec-Fetch-Dest" -> "frame",
  		"Sec-Fetch-Mode" -> "navigate",
  		"Sec-Fetch-Site" -> "same-origin",
  		"Sec-Fetch-User" -> "?1",
  		"Upgrade-Insecure-Requests" -> "1",
  		"sec-ch-ua" -> """Not.A/Brand";v="8", "Chromium";v="114", "Google Chrome";v="114""",
  		"sec-ch-ua-mobile" -> "?0",
  		"sec-ch-ua-platform" -> "Windows"
  )

  private val headers_3 = Map(
  		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
  		"Accept-Encoding" -> "gzip, deflate, br",
  		"Accept-Language" -> "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7",
  		"Cache-Control" -> "max-age=0",
  		"Origin" -> "http://localhost:1080",
  		"Sec-Fetch-Dest" -> "frame",
  		"Sec-Fetch-Mode" -> "navigate",
  		"Sec-Fetch-Site" -> "same-origin",
  		"Sec-Fetch-User" -> "?1",
  		"Upgrade-Insecure-Requests" -> "1",
  		"sec-ch-ua" -> """Not.A/Brand";v="8", "Chromium";v="114", "Google Chrome";v="114""",
  		"sec-ch-ua-mobile" -> "?0",
  		"sec-ch-ua-platform" -> "Windows"
  )
  
  private val headers_5 = Map(
  		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
  		"Accept-Encoding" -> "gzip, deflate, br",
  		"Accept-Language" -> "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7",
  		"Sec-Fetch-Dest" -> "document",
  		"Sec-Fetch-Mode" -> "navigate",
  		"Sec-Fetch-Site" -> "same-origin",
  		"Sec-Fetch-User" -> "?1",
  		"Upgrade-Insecure-Requests" -> "1",
  		"sec-ch-ua" -> """Not.A/Brand";v="8", "Chromium";v="114", "Google Chrome";v="114""",
  		"sec-ch-ua-mobile" -> "?0",
  		"sec-ch-ua-platform" -> "Windows"
  )

  private val scn = scenario("RecordedSimulation")
    .exec(
      http("enter")
        .get("/cgi-bin/login.pl?username=&password=&getInfo=true")
        .headers(headers_0)
    )
    .pause(1)
    .exec(
      http("register")
        .post("/cgi-bin/login.pl")
        .formParam("username", "asd")
        .formParam("password", "123")
        .formParam("passwordConfirm", "123")
        .formParam("firstName", "first")
        .formParam("lastName", "last")
        .formParam("address1", "asd st., 143")
        .formParam("address2", "city, state, 123456")
        .formParam("register.x", "67")
        .formParam("register.y", "14")

    )
    .pause(1)
    .exec(
      http("get_menu")
        .get("/cgi-bin/welcome.pl?page=menus")
        .headers(headers_5)
    )
    .pause(1)
    .exec(
      http("search")
        .get("/cgi-bin/welcome.pl?page=search")
        .headers(headers_5)

    )
    .pause(1)
    .exec(
      http("booking_1")
        .post("/cgi-bin/reservations.pl")
        .headers(headers_3)
        .formParam("advanceDiscount", "0")
        .formParam("depart", "Denver")
        .formParam("departDate", "07/05/2023")
        .formParam("arrive", "Frankfurt")
        .formParam("returnDate", "07/06/2023")
        .formParam("numPassengers", "1")
        .formParam("seatPref", "Aisle")
        .formParam("seatType", "Coach")
        .formParam("findFlights.x", "66")
        .formParam("findFlights.y", "9")
        .formParam(".cgifields", "roundtrip")
        .formParam(".cgifields", "seatType")
        .formParam(".cgifields", "seatPref")
    )
    .pause(1)
    .exec(
      http("booking_2")
        .post("/cgi-bin/reservations.pl")
        .headers(headers_3)
        .formParam("outboundFlight", "010;386;07/05/2023")
        .formParam("numPassengers", "1")
        .formParam("advanceDiscount", "0")
        .formParam("seatType", "Coach")
        .formParam("seatPref", "Aisle")
        .formParam("reserveFlights.x", "31")
        .formParam("reserveFlights.y", "1")
    )
    .pause(1)
    .exec(
      http("booking_payment")
        .post("/cgi-bin/reservations.pl")
        .headers(headers_3)
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
        .formParam("seatPref", "Aisle")
        .formParam("outboundFlight", "010;386;07/05/2023")
        .formParam("advanceDiscount", "0")
        .formParam("returnFlight", "")
        .formParam("JSFormSubmit", "off")
        .formParam("buyFlights.x", "68")
        .formParam("buyFlights.y", "13")
        .formParam(".cgifields", "saveCC")
    )
		.pause(1)
		.exec(
			http("request_26")
				.get("/cgi-bin/welcome.pl?signOff=1")
				.headers(headers_5)
		)

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
