package simulations

import io.gatling.http.Predef._
import io.gatling.core.Predef._

class TC16_LOGIN_LOGOUT extends Simulation  {

  // http configuration

  val httpConfig = http.baseUrl("https://parabank.parasoft.com")

  // scenario configuration

  val scn = scenario("LOGIN & LOGOUT")
    .exec(
      http("Visit Home Page")
      .get("/parabank/index.htm")
      .check(status.is(200))
    )

    .pause(4)

    .exec(
      http("Enter UN & PWD and Click on Login button")
        .post("/parabank/login.htm")
        .formParam("username", "NeelShaan")
        .formParam("password", "NeelShaan")
        .check(status.is(200))
    )

    .pause(1,10)

    .exec(
      http("User redirection to dashboard/accounts page")
        .get("/parabank/overview.htm")
        .check(status.is(200))
    )

    .pause(1,5)

    .exec(
    http("Click on Logout")
      .get("/parabank/logout.htm")
      .check(status.is(200))
    )

  // setUp configuration

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConfig)

}