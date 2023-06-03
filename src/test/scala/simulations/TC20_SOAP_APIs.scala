package simulations

import io.gatling.http.Predef._
import io.gatling.core.Predef._

class TC11_SOAP_APIs extends Simulation {

  // http configuration

  val httpConfig = http.baseUrl("http://www.dneonline.com")
  http.header("Content-Type", "text/xml; charset=utf-8")
  http.header("Accept", "text/xml; charset=utf-8")
  http.header("SOAPAction", "http://tempuri.org/Add")

  // scenario configuration

  val scn = scenario("SOAP APIs")
    .exec(
  http("ADDITION-SOAP API")
    .post("/calculator.asmx")
    .header("Content-Type", "text/xml; charset=utf-8")
    .header("Accept", "text/xml; charset=utf-8")
    .body(RawFileBody("src/test/resources/TestData/addTestData.xml")).asXml
    .check(status.is(200))
    .check(bodyString.saveAs("responseBody"))
    )

    .exec { session =>
      println("The addition API result is >>>>>\n")
      println(session("responseBody").as[String])
      session
    }

  .exec(
    http("SUBTRACTION-SOAP API")
      .post("/calculator.asmx")
      .header("Content-Type", "text/xml; charset=utf-8")
      .header("Accept", "text/xml; charset=utf-8")
      .body(RawFileBody("src/test/resources/TestData/subtractTestData.xml")).asXml
      .check(status.is(200))
      .check(bodyString.saveAs("responseBody"))
  )

    .exec { session =>
      println("The subtraction API result is >>>>>\n")
      println(session("responseBody").as[String])
      session
    }

    .exec(
      http("MULTIPLICATION-SOAP API")
        .post("/calculator.asmx")
        .header("Content-Type", "text/xml; charset=utf-8")
        .header("Accept", "text/xml; charset=utf-8")
        .body(RawFileBody("src/test/resources/TestData/multiplyTestData.xml")).asXml
        .check(status.is(200))
        .check(bodyString.saveAs("responseBody"))
    )

    .exec { session =>
      println("The multiplication API result is >>>>>\n")
      println(session("responseBody").as[String])
      session
    }

    .exec(
      http("DIVISION-SOAP API")
        .post("/calculator.asmx")
        .header("Content-Type", "text/xml; charset=utf-8")
        .header("Accept", "text/xml; charset=utf-8")
        .body(RawFileBody("src/test/resources/TestData/divideTestData.xml")).asXml
        .check(status.is(200))
        .check(bodyString.saveAs("responseBody"))
    )

    .exec { session =>
      println("The division API result is >>>>>\n")
      println(session("responseBody").as[String])
      session
    }

  // setUp configuration

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConfig)

}