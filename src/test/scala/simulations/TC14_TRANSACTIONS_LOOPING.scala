package Simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.Predef.Simulation

class TC12_TRANSACTIONS_LOOPING extends Simulation {

  // http configuration

  val httpConfig = http.baseUrl("https://thetestingworldapi.com")

  // scenario configuration

  val scnTrans1 = scenario("To get a specific student details from the database")
    .repeat(10) {
      exec(
        http("GET STUDENTS DATA TEST CASE")
          .get("/api/studentsDetails/7453503")
          .check(status.is(200))
          .check(bodyString.saveAs("BODY"))
      )
        .pause(1)
    }

    .exec(session => {
      val response = session("BODY").as[String]
      println(s"The student records received from the response body: \n$response")
      session
    })

  val scnTrans2 = scenario("To get all the student details from the database")
    .repeat(5) {
      exec(
        http("ALL STUDENTS DATA TEST CASE")
          .get("/api/studentsDetails")
          .check(status.is(200))
      )
        .pause(1)
    }

  // setUp configuration

  setUp(
    scnTrans1.inject(atOnceUsers(1)).protocols(httpConfig),
    scnTrans2.inject(atOnceUsers(1)).protocols(httpConfig))
}