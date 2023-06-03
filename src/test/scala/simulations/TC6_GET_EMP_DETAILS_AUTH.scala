package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class TC6_GET_EMP_DETAILS_AUTH extends Simulation {

  // http config

  val httpConfig = http.baseUrl("https://gorest.co.in")
  http.header("Authorization", "Bearer a1acf13036e08546446ecbcbeb75b11959fbfcc0795218a185cfc982f6982c29")
  http.header("Accept", "application/json")
  http.header("User-Agent", "Mozilla/5.0 (Android 13; Mobile; rv:109.0) Gecko/113.0 Firefox/113.0")

  // scenario config

  val scn = scenario("To get all employee details using GET http method")
    .exec(
      http("GET LIST OF USERS-AUTHORIZED API")
        .get("/public/v2/users")
        // .check(status.is(200))
        .check(status.in(200 to 204))
        .check(status.not(400))
        .check(status.not(500))
        .check(header("Content-Type").exists)
        .check(header("Connection").exists)
        .check(header("Server").exists)
        .check(substring("id"))
        .check(substring("name"))
        .check(substring("email"))
        .check(substring("gender"))
        .check(substring("status"))
        .check(responseTimeInMillis.lte(1000))
        .check(header("Content-Type").is("application/json; charset=utf-8"))
        .check(header("Connection").is("keep-alive"))
        .check(header("Server").is("cloudflare"))
        // .check(bodyLength.is(10724))
        .check(jsonPath("$[0].name").is("ShaanIsha"))
        .check(jsonPath("$[0].gender").is("male"))
        .check(jsonPath("$[0].status").is("active"))
    )

  // setUp config

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConfig)
}