package simulations

import io.gatling.core.Predef.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class TC13_RAMP_UP_STRATEGY extends Simulation {

  // http configuration

  val httpConfig =  http.baseUrl("https://thetestingworldapi.com")

  // scenario configuration

  val scn = scenario("This API is used to retrieve all existing student details from the database")
    .exec(
      http("GET ALL STUDENT DETAILS")
        .get("/api/studentsDetails")
        .check(status.is(200))
    )

  // load setUp configuration

  setUp(
   // scn.inject(atOnceUsers(100))).protocols(httpConfig)
   // scn.inject(nothingFor(10), atOnceUsers(5)).protocols(httpConfig)
   // scn.inject(rampUsers(8).during(16)).protocols(httpConfig)
   //scn.inject(rampUsersPerSec(10).to(20).during(30)).protocols(httpConfig)
  scn.inject(constantUsersPerSec(20).during(15)).protocols(httpConfig)

    //    scn.inject(atOnceUsers(1)).protocols(httpConfig)
    // warm-up period
    // scn.inject(nothingFor(15),atOnceUsers(10)).protocols(httpConfig)
    // scn.inject(nothingFor(20),rampUsers(30).during(5)).protocols(httpConfig)
     // scn.inject(rampUsersPerSec(10).to(20).during(8)).protocols(httpConfig)
    //  scn.inject(constantUsersPerSec(35).during(10)).protocols(httpConfig)
    scn.inject(constantUsersPerSec(14).during(10).randomized).protocols(httpConfig)

  )
}