package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import concurrent.duration.DurationInt

class TC5_ALL_HTTP_METHODS extends Simulation {

  // http config

  val httpConfig = http.baseUrl("https://thetestingworldapi.com")
  http.header("Content-Type", "application/json")
  http.header("Accept", "application/json")
  http.header(
    "User-Agent",
    "Mozilla/5.0 (Linux; Android 13) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.5359.128 Mobile Safari/537.36"
  ) // to simulate real browser behavior

  // scenario config

  val scn = scenario("To test the creation of student record using POST http method")
    .exec(
      http("CREATE STUDENT TEST CASE")
        .post("/api/studentsDetails")
        .body(RawFileBody("src/test/resources/TestData/createStudent.json")).asJson
        .check(status.is(201))
    )
    .pause(10)  // to simulate real user behavior

    .exec(
      http("UPDATE STUDENT TEST CASE")
        .put("/api/studentsDetails/7540807")
        .body(RawFileBody("src/test/resources/TestData/updateStudent.json")).asJson
        .check(status.is(200))
        .check(status.in(200, 299))
    )

    .pause(1,10)  // to simulate real user behavior

    .exec(
      http("DELETE STUDENT TEST CASE")
        .delete("/api/studentsDetails/7540807")
        .check(status.is(200))
    )

    .pause(4000.milliseconds) // to simulate real user behavior

    .exec(
      http("GET ALL STUDENT DETAILS")
        .get("/api/studentsDetails")
        .check(status.is(200))
    )
    .pause(10)  // to simulate real user behavior

  // setUp config

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConfig)

}
