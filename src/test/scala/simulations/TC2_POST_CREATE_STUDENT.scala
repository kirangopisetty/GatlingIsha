package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class TC2_POST_CREATE_STUDENT extends Simulation {

  // http config

  val httpConfig =  http.baseUrl("https://thetestingworldapi.com")
                    http.header("Content-Type", "application/json")
                    http.header("Accept", "application/json")
                    http.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36")

  // scenario config

  val scn = scenario("To test the creation of student record using POST http method")
      .exec(
        http("CREATE STUDENT TEST CASE")
          .post("/api/studentsDetails")
          .body(RawFileBody("src/test/resources/TestData/createStudent.json")).asJson
          .check(status.is(201))
          .check(bodyString.saveAs("BODY")))

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