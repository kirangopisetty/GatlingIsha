package simulations

import io.gatling.http.Predef._
import io.gatling.core.Predef._

class TC15_REGISTER_LOGOUT extends Simulation {

  // http configuration

  val httpConfig = http.baseUrl("https://parabank.parasoft.com")

  // scenario configuration

  val scn = scenario("SIGN-UP AND LOGOUT PROCESS")
    .exec(
         http("Visit Home Page ")
        .get("/parabank/index.htm")
        .check(status.is(200))
    )

    .pause(5)

    .exec(
      http("Click on Register button ")
        .get("/parabank/register.htm")
        .check(status.is(200))
    )

    .pause(5)

    .exec(
      http("Fill all the fields & click on Register button")
        .post("/parabank/register.htm")
        .formParam("customer.firstName", "Neel")
        .formParam("customer.lastName", "Shaan")
        .formParam("customer.address.street", "HillNo3")
        .formParam("customer.address.city", "Hyderabad")
        .formParam("customer.address.state", "Telangana")
        .formParam("customer.address.zipCode", "123456")
        .formParam("customer.phoneNumber", "9988776644")
        .formParam("customer.ssn", "1231234568")
        .formParam("customer.username", "NeelShaan")
        .formParam("customer.password", "NeelShaan")
        .formParam("repeatedPassword", "NeelShaan")
    )

    .pause(5)

    .exec(
      http("Click on Logout button")
        .get("/parabank/logout.htm")
        .check(status.is(200))
    )

  // setUp configuration

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConfig)

}