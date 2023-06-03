package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class TC8_PATCH_UPDATE_EMP_AUTH extends Simulation {

  // http config

  val httpConfig = http.baseUrl("https://gorest.co.in")
  http.header("Connection", "keep-alive")
  // http.header("Authorization", "Bearer a1acf13036e08546446ecbcbeb75b11959fbfcc0795218a185cfc982f6982c29")
  http.header("Accept", "application/json")
  http.header("Content-Type", "application/json")
  
  val sessionHeaders = Map("Authorization" -> "Bearer a1acf13036e08546446ecbcbeb75b11959fbfcc0795218a185cfc982f6982c29",
    "Content-Type" -> "application/json")

  // scenario config

  val scn = scenario("To update an existing employee in the database using PATCH http method")
    .exec(
      http("UPDATE EXISTING EMP-AUTH API")
        .patch("/public/v2/users/1893647")
        .headers(sessionHeaders)
        .body(RawFileBody("src/test/resources/TestData/updateEmployee.json")).asJson
        .check(status.in(200 to 204))
    )
    // setUp config

    setUp (
        scn.inject(atOnceUsers(1))
        ).protocols(httpConfig)
}