package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class TC3_PUT_UPDATE_STUDENT extends Simulation {

  // http config

  val httpConfig =  http.baseUrl("https://thetestingworldapi.com")
                    http.header("Content-Type", "application/json")
                    http.header("Accept", "application/json")

  // scenario config

  val scn = scenario("To update an existing student record using PUT http method")
    .exec(
      http("UPDATE STUDENT TEST CASE")
        .put("/api/studentsDetails/7542524")
        .body(RawFileBody("src/test/resources/TestData/updateStudent.json")).asJson
        .check(status.is(200))
        .check(status.in(200, 299))
    )
    .exec(session => {
      val myResponseBody = session("BODY").as[String]
      println(s"The response body received from the server is   : \n$myResponseBody")
      session
    }
    )

  // setUp config

    setUp(
      scn.inject(atOnceUsers(1))
    ).protocols(httpConfig)
}