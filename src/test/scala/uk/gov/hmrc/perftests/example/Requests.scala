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
  val baseUrlAuth: String = baseUrlFor("auth-frontend")
  val route: String   = "/register-to-send-a-country-by-country-report"

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
      .formParam("gatewayToken", "")
      .formParam("redirectionUrl", baseUrl + route + "/register/registered-address-in-uk")
      .formParam("credentialStrength", "strong")
      .formParam("confidenceLevel", "50")
      .formParam("affinityGroup", "Organisation")
      .check(status.is(303))
      .check(header("Location").is(baseUrl + route+ "/register/registered-address-in-uk").saveAs("AddressPage"))
  }

    val getRegisteredAddressPage: HttpRequestBuilder = {
      http("Get Registered Address Page")
        .get("${AddressPage}" : String)
        .check(status.is(200))
        .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
    }

  val postRegisteredAddressPage: HttpRequestBuilder = {
    http("Post Registered Address Page")
      .post("${AddressPage}" : String)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", "false")
      .check(status.is(303))
      .check(header("Location").is(route+ "/register/have-utr").saveAs("HaveUtrPage"))
  }

  val getWithoutIdBusinessPage: HttpRequestBuilder = {
    http("Get Without Business ID Page")
      .get(baseUrl + "${HaveUtrPage}" : String)
      .check(status.is(200))
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
  }

  val postWithoutIdBusinessPage: HttpRequestBuilder = {
    http("Post Without Business ID Page")
      .post("${HaveUtrPage}": String)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", "CFATCHA company")
      .check(status.is(303))
      .check(header("Location").is(route + "/register/without-id/business-name").saveAs("BusinessWithoutId"))
  }

  val getWithoutIdHaveTradeNamePage: HttpRequestBuilder = {
    http("Get Without ID Have Trade Name Page")
      .get(baseUrl + "${BusinessWithoutId}": String)
      .check(status.is(200))
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
  }


//  register/without-id/business-name
//  val getHaveUTRPage: HttpRequestBuilder = {
//    http("Get Have UTR Page")
//      .get("${HaveUTRPage}")
//      .check(status.is(200))
//      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
//  }

//  val postHaveUTRPageNo: HttpRequestBuilder = {
//    http("post Have UTR Page-No")
//      .post("${HaveUTRPage}")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("value", "false")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/without-id/business-name").saveAs("withoutIdNamePage"))
//
//  }
//  val getBusinessNamePage: HttpRequestBuilder = {
//    http("Get Business Name Page without Id")
//      .get(baseUrl + "${withoutIdNamePage}")
//      .check(status.is(200))
//      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
//  }
//
//  val postBusinessNamePage: HttpRequestBuilder = {
//    http("post Business name without id")
//      .post(baseUrl + "${withoutIdNamePage}")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("value", "cbc company")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/without-id/have-trading-name").saveAs("HaveTradeName"))
//  }
//
//  val getHaveTradeName: HttpRequestBuilder = {
//    http("Get Have Trade name page")
//      .get(baseUrl + "${HaveTradeName}")
//      .check(status.is(200))
//      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
//  }
//
//  val postHaveTradeName: HttpRequestBuilder = {
//    http("post Trade name")
//      .post(baseUrl + "${HaveTradeName}")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("value", "true")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/without-id/trading-name").saveAs("TradingName"))
//  }
//
//  val getTradeName: HttpRequestBuilder = {
//    http("Get Trade name page")
//      .get(baseUrl + "${TradingName}")
//      .check(status.is(200))
//      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
//  }
//
//  val postTradeName: HttpRequestBuilder = {
//    http("post Trading name")
//      .post(baseUrl + "${TradingName}")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("value", "Test company")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/without-id/address").saveAs("Address"))
//  }
//
//  val getBusinessAddress: HttpRequestBuilder = {
//    http("Get Business Address page")
//      .get(baseUrl + "${Address}")
//      .check(status.is(200))
//      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
//  }
//
//  val postBusinessAddress: HttpRequestBuilder = {
//    http("post Business Address")
//      .post(baseUrl + "${Address}")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("addressLine1", "1 Test")
//      .formParam("addressLine2", "ABC")
//      .formParam("addressLine3", "TestCity")
//      .formParam("addressLine4", "TestRegion")
//      .formParam("postCode", "AB12 6XX")
//      .formParam("country", "PL")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/your-contact-details").saveAs("YourContactDetails"))
//  }
//
//  val getYourContactDetails: HttpRequestBuilder = {
//    http("Get your contact details page")
//      .get(baseUrl + "${YourContactDetails}")
//      .check(status.is(200))
//  }
//
//  val getContactName: HttpRequestBuilder = {
//    http("Get your contact name page")
//      .get(baseUrl + route + "/register/contact-name")
//      .check(status.is(200))
//      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
//  }
//
//  val postContactName: HttpRequestBuilder = {
//    http("post Contact name")
//      .post(baseUrl + route + "/register/contact-name")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("value", "Test Team1")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/email").saveAs("Email"))
//  }
//
//  val getEmail: HttpRequestBuilder = {
//    http("Get email")
//      .get(baseUrl + "${Email}")
//      .check(status.is(200))
//      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
//  }
//
//  val postEmail: HttpRequestBuilder = {
//    http("post email")
//      .post(baseUrl + "${Email}")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("value", "test@chocolate.com")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/have-phone").saveAs("Have-Phone"))
//  }
//
//  val getHavePhone: HttpRequestBuilder = {
//    http("Get have-phone")
//      .get(baseUrl + "${Have-Phone}")
//      .check(status.is(200))
//      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
//  }
//
//  val postHavePhone: HttpRequestBuilder = {
//    http("post have-phone")
//      .post(baseUrl + "${Have-Phone}")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("value", "true")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/phone").saveAs("Phone"))
//  }
//
//  val getPhone: HttpRequestBuilder = {
//    http("Get phone")
//      .get(baseUrl + "${Phone}")
//      .check(status.is(200))
//      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
//  }
//
//  val postPhone: HttpRequestBuilder = {
//    http("post phone")
//      .post(baseUrl + "${Phone}")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("value", "1234567890")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/have-second-contact").saveAs("SecondContact"))
//  }
//
//  val getSecondContact: HttpRequestBuilder = {
//    http("Get Second Contact")
//      .get(baseUrl + "${SecondContact}")
//      .check(status.is(200))
//      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
//  }
//
//  val postSecondContact: HttpRequestBuilder = {
//    http("post Second Contact")
//      .post(baseUrl + "${SecondContact}")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("value", "true")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/second-contact-name").saveAs("SecondContactName"))
//  }
//
//  val getSecondContactName: HttpRequestBuilder = {
//    http("Get Second Contact Name")
//      .get(baseUrl + "${SecondContactName}")
//      .check(status.is(200))
//      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
//  }
//
//  val postSecondContactName: HttpRequestBuilder = {
//    http("post Second Contact Name")
//      .post(baseUrl + "${SecondContactName}")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("value", "Test Second Contact")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/second-contact-email").saveAs("SecondContactEmail"))
//  }
//
//  val getSecondContactEmail: HttpRequestBuilder = {
//    http("Get Second Contact Email")
//      .get(baseUrl + "${SecondContactEmail}")
//      .check(status.is(200))
//      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
//  }
//
//  val postSecondContactEmail: HttpRequestBuilder = {
//    http("post Second Contact Email")
//      .post(baseUrl + "${SecondContactEmail}")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("value", "test2@test.com")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/second-contact-have-phone").saveAs("SecondContactHavePhone"))
//  }
//
//  val getSecondContactHavePhone: HttpRequestBuilder = {
//    http("Get Second Contact Have Phone")
//      .get(baseUrl + "${SecondContactHavePhone}")
//      .check(status.is(200))
//      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
//  }
//
//  val postSecondContactHavePhone: HttpRequestBuilder = {
//    http("post Second Contact Have Phone")
//      .post(baseUrl + "${SecondContactHavePhone}")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("value", "true")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/second-contact-phone").saveAs("SecondContactPhone"))
//  }
//
//  val getSecondContactPhone: HttpRequestBuilder = {
//    http("Get Second Contact Phone")
//      .get(baseUrl + "${SecondContactPhone}")
//      .check(status.is(200))
//      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
//  }
//
//  val postSecondContactPhone: HttpRequestBuilder = {
//    http("post Second Contact Phone")
//      .post(baseUrl + "${SecondContactPhone}")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("value", "1234567999")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/check-your-answers").saveAs("CheckYourAnswers"))
//  }
//
//  val getCheckYourAnswers: HttpRequestBuilder = {
//    http("Get Check Your Answers")
//      .get(baseUrl + "${CheckYourAnswers}")
//      .check(status.is(200))
//      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
//  }
//
//  val postCheckYourAnswers: HttpRequestBuilder = {
//    http("post Check Your Answers")
//      .post(baseUrl + "${CheckYourAnswers}")
//      .formParam("csrfToken", "${csrfToken}")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/confirm-registration").saveAs("ConfirmRegistrationPage"))
//  }
//
//  val getConfirmationRegistrationPage: HttpRequestBuilder = {
//    http("Get Confirmation Registration Page")
//      .get(baseUrl + "${ConfirmRegistrationPage}")
//      .check(status.is(200))
//  }
//
//  val postHaveUTRPageYes: HttpRequestBuilder = {
//    http("post Have UTR Page-Yes")
//      .post("${HaveUTRPage}")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("value", "true")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/business-type").saveAs("BusinessTypePage"))
//
//  }
//
//  val getBusinessTypePage: HttpRequestBuilder = {
//    http("Get Business Type Page")
//      .get(baseUrl + "${BusinessTypePage}")
//      .check(status.is(200))
//      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
//  }
//
//  val postBusinessTypeLimitedCompany: HttpRequestBuilder = {
//    http("post Business Type Limited Company")
//      .post(baseUrl + "${BusinessTypePage}")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("value", "limited")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/utr").saveAs("UTRPage"))
//  }
//
//  val getUTRPage: HttpRequestBuilder = {
//    http("Get UTR Page")
//      .get(baseUrl + "${UTRPage}")
//      .check(status.is(200))
//      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
//  }
//
//  val postUTRPage: HttpRequestBuilder = {
//    http("post Business Type Limited Company")
//      .post(baseUrl + "${UTRPage}")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("value", "1234567890")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/business-name").saveAs("BusinessNamePage"))
//  }
//
//  val getBusinessNamePageWithId: HttpRequestBuilder = {
//    http("Get Business Name Page with Id")
//      .get(baseUrl + "${BusinessNamePage}")
//      .check(status.is(200))
//      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
//  }
//
//  val postBusinessNamePageWithId: HttpRequestBuilder = {
//    http("post Business name with id")
//      .post(baseUrl + "${BusinessNamePage}")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("value", "cbc company")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/is-this-your-business").saveAs("YourBusinessPage"))
//  }
//
//  val getYourBusinessPage: HttpRequestBuilder = {
//    http("Get Your Business Page")
//      .get(baseUrl + "${YourBusinessPage}")
//      .check(status.is(200))
//      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
//  }
//
//  val postYourBusinessPage: HttpRequestBuilder = {
//    http("post Your Business Page")
//      .post(baseUrl + "${YourBusinessPage}")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("value", "true")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/your-contact-details").saveAs("YourContactDetails"))
//  }
//
//  val postBusinessTypePartnership: HttpRequestBuilder = {
//    http("post Business Type Partnership")
//      .post(baseUrl + "${BusinessTypePage}")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("value", "partnership")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/utr").saveAs("UTRPage"))
//  }
//
//  val postBusinessTypeLimitedPartnership: HttpRequestBuilder = {
//    http("post Business Type Limited Partnership")
//      .post(baseUrl + "${BusinessTypePage}")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("value", "limitedPartnership")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/utr").saveAs("UTRPage"))
//  }
//
//  val postBusinessTypeUnincorporatedAssociation: HttpRequestBuilder = {
//    http("post Business Type Unincorporated Association")
//      .post(baseUrl + "${BusinessTypePage}")
//      .formParam("csrfToken", "${csrfToken}")
//      .formParam("value", "unincorporatedAssociation")
//      .check(status.is(303))
//      .check(header("Location").is(route + "/register/utr").saveAs("UTRPage"))
//  }
//
//



}
