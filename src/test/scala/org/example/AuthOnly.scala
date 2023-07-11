
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class AuthOnly extends Simulation {

  private val httpProtocol = http
    .baseUrl("http://localhost:1080")
    .inferHtmlResources()
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")
  
  private val headers_0 = Map(
  		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
  		"Accept-Encoding" -> "gzip, deflate, br",
  		"Accept-Language" -> "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7",
  		"Cache-Control" -> "max-age=0",
  		"If-Modified-Since" -> "Mon, 27 May 2013 11:20:22 GMT",
  		"If-None-Match" -> "200000005bd81-16e-4ddb1559ac980",
  		"Sec-Fetch-Dest" -> "document",
  		"Sec-Fetch-Mode" -> "navigate",
  		"Sec-Fetch-Site" -> "none",
  		"Sec-Fetch-User" -> "?1",
  		"Upgrade-Insecure-Requests" -> "1",
  		"sec-ch-ua" -> """Not.A/Brand";v="8", "Chromium";v="114", "Google Chrome";v="114""",
  		"sec-ch-ua-mobile" -> "?0",
  		"sec-ch-ua-platform" -> "Windows"
  )
  
  private val headers_1 = Map(
  		"Upgrade-Insecure-Requests" -> "1",
  		"sec-ch-ua" -> """Not.A/Brand";v="8", "Chromium";v="114", "Google Chrome";v="114""",
  		"sec-ch-ua-mobile" -> "?0",
  		"sec-ch-ua-platform" -> "Windows"
  )
  
  private val headers_2 = Map(
  		"sec-ch-ua" -> """Not.A/Brand";v="8", "Chromium";v="114", "Google Chrome";v="114""",
  		"sec-ch-ua-mobile" -> "?0",
  		"sec-ch-ua-platform" -> "Windows"
  )
  
  private val headers_4 = Map(
  		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
  		"Accept-Encoding" -> "gzip, deflate, br",
  		"Accept-Language" -> "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7",
  		"Sec-Fetch-Dest" -> "frame",
  		"Sec-Fetch-Mode" -> "navigate",
  		"Sec-Fetch-Site" -> "same-origin",
  		"Upgrade-Insecure-Requests" -> "1",
  		"sec-ch-ua" -> """Not.A/Brand";v="8", "Chromium";v="114", "Google Chrome";v="114""",
  		"sec-ch-ua-mobile" -> "?0",
  		"sec-ch-ua-platform" -> "Windows"
  )
  
  private val headers_8 = Map(
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
  
  private val headers_9 = Map(
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


  private val scn = scenario("AuthOnly")
    .exec(
      http("request_0")
        .get("/WebTours/")
        .headers(headers_0)
        .resources(
          http("request_1")
            .get("/WebTours/header.html")
            .headers(headers_1),
          http("request_2")
            .get("/WebTours/images/hp_logo.png")
            .headers(headers_2),
          http("request_3")
            .get("/WebTours/images/webtours.png")
            .headers(headers_2),
          http("request_4")
            .get("/cgi-bin/welcome.pl?signOff=true")
            .headers(headers_4),
          http("request_5")
            .get("/WebTours/home.html")
            .headers(headers_1),
          http("request_6")
            .get("/cgi-bin/nav.pl?in=home")
            .headers(headers_4)
						.check(css("input","value").saveAs("asd")),
						http("request_7")
            .get("/WebTours/images/mer_login.gif")
            .headers(headers_2)
        )
    )
    .pause(6)
    .exec(
      http("request_8")
        .post("/cgi-bin/login.pl")
        .headers(headers_8)
        .formParam("userSession", "#{asd}")
        .formParam("username", "asd")
        .formParam("password", "123")
        .formParam("login.x", "59")
        .formParam("login.y", "13")
        .formParam("JSFormSubmit", "off")
        .resources(
          http("request_9")
            .get("/cgi-bin/nav.pl?page=menu&in=home")
            .headers(headers_9),
          http("request_10")
            .get("/WebTours/images/flights.gif")
            .headers(headers_2),
          http("request_11")
            .get("/WebTours/images/itinerary.gif")
            .headers(headers_2),
          http("request_12")
            .get("/WebTours/images/in_home.gif")
            .headers(headers_2),
          http("request_13")
            .get("/WebTours/images/signoff.gif")
            .headers(headers_2),
          http("request_14")
            .get("/cgi-bin/login.pl?intro=true")
            .headers(headers_9)
        )
    )
		.pause(1)
		.exec {
			session => println(session("asd").as[String])
				session
		}

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
