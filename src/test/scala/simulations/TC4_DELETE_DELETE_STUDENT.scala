package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class TC4_DELETE_DELETE_STUDENT extends Simulation {

  // http config

  val httpConfig = http.baseUrl("https://thetestingworldapi.com")

  // scenario config

  val scn = scenario("To delete the student record using DELETE http method")
    .exec(
      http("DELETE STUDENT TEST CASE")
        .delete("/api/studentsDetails/7540806")
        .check(status.is(200))
    )

  // setUp config

    setUp(
      scn.inject(atOnceUsers(1))
    ).protocols(httpConfig)
}