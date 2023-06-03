package simulations

import io.gatling.core.Predef.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class TC10_CORRELATION extends Simulation {

  // http configuration

  val httpConfig =  http.baseUrl("https://thetestingworldapi.com")  // mandatory
  http.header("Accept", "application/json") // optional header
  http.header("Connection", "keep-alive") // optional header

  // scenario configuration

  val scn = scenario("This API is used to retrieve all existing student details from the database")
    .exec(
      http("GET ALL STUDENT DETAILS")
        .get("/api/studentsDetails")
        .check(status.is(200))
        .check(jsonPath("$[0].id").saveAs("extractedID"))
    )
    .pause(10)

    .exec(
      http("DELETE STUDENT TEST CASE")
        .delete("/api/studentsDetails/${extractedID}")
        .check(status.is(200))
    )

  // load setUp configuration

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConfig)
}