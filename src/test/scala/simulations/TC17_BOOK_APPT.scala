package simulations

import io.gatling.http.Predef._
import io.gatling.core.Predef._

class TC17_BOOK_APPT extends Simulation {

  // http configuration

  // https://katalon-demo-cura.herokuapp.com/profile.php#login

  val httpConfig = http.baseUrl("https://katalon-demo-cura.herokuapp.com")

  // scenario configuration

  // https://katalon-demo-cura.herokuapp.com/authenticate.php

  val scn = scenario("LOGIN --> BOOK AN APPOINTMENT --> LOGOUT")
    .exec(
      http("LOGIN TO THE APPLICATION")
        .post("/authenticate.php")
        .formParam("username", "John Doe")
        .formParam("password", "ThisIsNotAPassword")
        .check(status.is(200))
        .check(substring("Tokyo"))
        .check(substring("Hongkong"))
        .check(substring("Seoul"))
    )

    .exec(
      http("BOOK AN APPOINTMENT")
      // https://katalon-demo-cura.herokuapp.com/appointment.php
        .post("/appointment.php")
        .formParam("facility", "Hongkong CURA Healthcare Center")
        .formParam("hospital_readmission", "Yes")
        .formParam("programs", "Medicare")
        .formParam("visit_date", "31/06/2023")
        .formParam("comment", "General health checkup is needed")
        .check(status.is(200))
        .check(substring("Appointment Confirmation"))
    )

    .exec(
      http("LOGOUT THE APPLICATION")
      // https://katalon-demo-cura.herokuapp.com/authenticate.php?logout
        .get("/authenticate.php?logout")
        .check(status.is(200))
        .check(substring("CURA Healthcare Service"))
    )

  // setUp configuration

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConfig)

}