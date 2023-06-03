package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.Predef.Simulation

class TC12_DDT_FEEDERS extends Simulation {

  // http configuration
  val httpConfig = http.baseUrl("https://reqres.in")
  val csvFeeder = csv("src/test/resources/TestData/empDetails.csv").circular

  // scenario configuration
  val scn = scenario("Verify the first_name of all 12 emp ids")
    .repeat(12) {
      feed(csvFeeder)
        .exec(
          http("SINGLE USER-TEST CASE")
            .get("/api/users/${empID}")
            .check(status.is(200))
            .check(jsonPath("$.data.first_name").is("${fName}"))
        )
    }

  // setUp configuration

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConfig)
}