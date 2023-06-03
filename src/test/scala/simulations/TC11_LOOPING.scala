package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class TC11_LOOPING extends Simulation {

  // http config

  val httpConfig = http.baseUrl("https://thetestingworldapi.com")

  // scenario config
  val scn = scenario("Executing the APIs in a loop")
    .repeat(3) {
      exec(
        http("GET A STUDENT DATA")
          .get("/api/studentsDetails/7542889")
          .check(status.is(200))
          .check(bodyString.saveAs("responseBody"))
      )
        .pause(5)
    }
    .exec(session => {
    val extractedResponseBody = session("responseBody").as[String]
      println(s"The student record details are >>>>>      \n${extractedResponseBody}")
      session
    }
    )

    .repeat(2) {
      exec(
        http("GET ALL STUDENT DETAILS")
          .get("/api/studentsDetails")
          .check(status.is(200))
      )
        .pause(3)
    }

  // setUp config

    setUp(
      scn.inject(atOnceUsers(1)
      ).protocols(httpConfig))
}