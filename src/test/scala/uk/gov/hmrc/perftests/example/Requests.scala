/*
 * Copyright 2022 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.perftests.example

import io.gatling.core.Predef._
import io.gatling.core.session.Expression
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object Requests extends ServicesConfiguration {

  val baseUrl: String = baseUrlFor("register-country-by-country-reporting-frontend")
  val route: String   = "/register-country-by-country-reporting-frontend"
  val baseUrlAuth: String = baseUrlFor("auth-frontend")

  def inputSelectorByName(name: String): Expression[String] = s"input[name='$name']"

  val getAuthLoginPage: HttpRequestBuilder = {
    http("Get Auth login page")
      .get(baseUrlAuth + "/auth-login-stub/gg-sign-in")
      .check(status.is(200))
  }

  val postAuthLoginPage: HttpRequestBuilder = {
    http("Enter Auth login credentials")
      .post(baseUrlAuth + "/auth-login-stub/gg-sign-in")
      .formParam("authorityId", "")
      .formParam("credentialStrength", "none")
      .formParam("confidenceLevel", "50")
      .formParam("affinityGroup", "Organisation")
      .formParam("redirectionUrl", baseUrl + route + "/register/have-utr")
      .check(status.is(303))
      .check(header("Location").is(baseUrl + route + "/register/have-utr").saveAs("HaveUTRPage"))
  }

  val getHaveUTRPage: HttpRequestBuilder = {
    http("Get Have UTR Page")
      .get("${HaveUTRPage}")
      .check(status.is(200))
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
  }

  val postHaveUTRPageNo: HttpRequestBuilder = {
    http("post Have UTR Page-No")
      .post("${HaveUTRPage}")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", "false")
      .check(status.is(303))
      .check(header("Location").is(route + "/register/without-id/business-name").saveAs("withoutIdNamePage"))

  }
  val getBusinessNamePage: HttpRequestBuilder = {
    http("Get Business Name Page without Id")
      .get(baseUrl + "${withoutIdNamePage}")
      .check(status.is(200))
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
  }

  val postBusinessNamePage: HttpRequestBuilder = {
    http("post Business name without id")
      .post(baseUrl + "${withoutIdNamePage}")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", "test")
      .check(status.is(303))
      .check(header("Location").is(route + "/register/without-id/have-trading-name").saveAs("HaveTradeName"))
  }

  val getHaveTradeName: HttpRequestBuilder = {
    http("Get Have Trade name page")
      .get(baseUrl + "${HaveTradeName}")
      .check(status.is(200))
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
  }

  val postHaveTradeName: HttpRequestBuilder = {
    http("post Trade name")
      .post(baseUrl + "${HaveTradeName}")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", "true")
      .check(status.is(303))
      .check(header("Location").is(route + "/register/without-id/trading-name").saveAs("TradingName"))
  }


}
