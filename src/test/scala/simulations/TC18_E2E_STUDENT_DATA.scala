package Simulations

import io.gatling.http.Predef._
import io.gatling.core.Predef._
import io.gatling.core.Predef.Simulation

class TC8_E2E_STUDENT_DATA extends Simulation {

  val httpConfig = http.baseUrl("https://thetestingworldapi.com")
  http.header("Content-Type", "application/json")
  http.header("Accept", "application/json")

  val scnAllUsers = scenario("To test the life cycle of a student records using GET-POST-PUT-DELETE APIs")
    .exec(
      http("GET ALL USERS DATA-TEST CASE")
        .get("/api/studentsDetails")
        .check(status.is(200))
    )
    .pause(5)  // it will pause for 5 seconds

  val scnCreateUser = scenario("To create a new student using POST http method")
    .exec(
      http("CREATE NEW USER-TEST CASE")
        .post("/api/studentsDetails")
        .body(RawFileBody("src/test/resources/createStudent.json")).asJson
        .check(status.in(200 to 201))
        .check(jsonPath("$.id").saveAs("extID"))
      // CONVERT LOCAL VARIABLE TO GLOBAL VARIABLE
    )
    .pause(1,10)  // it will pause for a random time between 1 to 10 seconds

  // val scnSingleUser = scenario("To confirm if the new student is created using GET http method")
    .exec(
      http("GET SINGLE USER-TEST CASE")
        .get("/api/studentsDetails/${extID}")
        .check(status.is(200))
        .check(bodyString.saveAs("BODY"))
    )
    .exec(session => {
      val response = session("BODY").as[String]
      println(s"Single User API response body is : \n$response")
      session
    })

    .pause(10)

    .exec(
      http("UPDATE USER-TEST CASE")
        .put("/api/studentsDetails/${extID}")
        .body(RawFileBody("src/test/resources/updateStudent.json")).asJson
        .check(status.is(200))
    )

    .pause(5,9)

  // val scnDeleteUser = scenario("TO DELETE STUDENT DETAILS")
    .exec(
      http("DELETE STUDENT DETAILS-TEST CASE")
        .delete("/api/studentsDetails/${extID}")
    )

  setUp(
    scnAllUsers.inject(atOnceUsers(1)).protocols(httpConfig),
    scnCreateUser.inject(atOnceUsers(1)).protocols(httpConfig),
    // scnSingleUser.inject(atOnceUsers(1)).protocols(httpConfig),
    // scnDeleteUser.inject(atOnceUsers(1)).protocols(httpConfig)
  )
}