package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class TC9_DELETE_EMP_AUTH extends Simulation {

  // http config

  val httpConfig = http.baseUrl("https://gorest.co.in")
  //http.header("Authorization", "Bearer a1acf13036e08546446ecbcbeb75b11959fbfcc0795218a185cfc982f6982c29")
  // http.authorizationHeader("Bearer a1acf13036e08546446ecbcbeb75b11959fbfcc0795218a185cfc982f6982c29")
  http.acceptCharsetHeader("UTF-8")

  val sessionHeaders = Map("Authorization" -> "Bearer a1acf13036e08546446ecbcbeb75b11959fbfcc0795218a185cfc982f6982c29",
    "Content-Type" -> "application/json")

  // scenario config

  val scn = scenario("To delete an existing employee using DELETE http method")
    .exec(
      http("DELETE EMP-AUTH API")
        .delete("/public/v2/users/1891133")
        .headers(sessionHeaders)
        .check(status.is(204))
    )

  // setUp config

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConfig)
}