package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class TC7_POST_CREATE_EMP_AUTH extends Simulation {

  // http config

  val httpConfig = http.baseUrl("https://gorest.co.in")
  http.header("Connection", "keep-alive")
  // http.header("Authorization", "Bearer a1acf13036e08546446ecbcbeb75b11959fbfcc0795218a185cfc982f6982c29")
  http.header("Accept", "application/json")
  http.header("Content-Type", "application/json")

  val sessionHeaders = Map("Authorization" -> "Bearer a1acf13036e08546446ecbcbeb75b11959fbfcc0795218a185cfc982f6982c29",
    "Content-Type" -> "application/json")

  // scenario config

  val scn = scenario("To create new employee in the database using POST http method")
    .exec(
      http("CREATE NEW EMP-AUTH API")
        .post("/public/v2/users")
        .headers(sessionHeaders)
        .body(RawFileBody("src/test/resources/TestData/createEmployee.json")).asJson
        .check(status.is(201))
        .check(jsonPath("$.id").ofType[Int])
        .check(jsonPath("$.name").ofType[String])
    )

  // setUp config

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConfig)
}