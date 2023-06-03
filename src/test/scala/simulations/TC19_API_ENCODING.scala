package Simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.Predef.Simulation

class TC9_API_ENCODING extends Simulation {

  val httpConfig = http.baseUrl("http://httpbin.org")
  http.header("Connection", "keep-alive")
  http.header("Accept", "application/json")
  http.header("Content-Type", "application/json")
  http.header("Accept-Encoding", "gzip, deflate")
  http.header("Authorization", "Basic dXNlcjpwYXNzd2Q=")
//  http.basicAuth("user", "passwd")

  val scn = scenario("To test encoding process of an API")
    .exec(
      http("Basic Authorization API that uses Base64 encoding algorithm")
        .get("/basic-auth/user/passwd")
        .header("Authorization", "Basic dXNlcjpwYXNzd2Q=")
        .check(status.is(200))
        .check(bodyString.saveAs("BODY"))

    )
    .exec(session => {
      val response = session("BODY").as[String]
      println("\n")
      println(s"Response body: \n$response")
      session
    })

  setUp(
    scn.inject(atOnceUsers(1)).protocols(httpConfig)
  )
}