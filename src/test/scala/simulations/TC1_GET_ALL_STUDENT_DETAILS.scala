package simulations

import io.gatling.core.Predef.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class TC1_GET_ALL_STUDENT_DETAILS extends Simulation {

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
            //.check(bodyLength.is(10724))
           // .check(jsonPath("$[0].first_name").is("Priyanka"))
           .check(bodyString.saveAs("BODY"))

        )

      .exec(session => {
      val response = session("BODY").as[String]
      println("\n")
      println(s"Response body: \n$response")
      session
    })

  // load setUp configuration

    setUp(
      scn.inject(atOnceUsers(1))
    ).protocols(httpConfig)
}