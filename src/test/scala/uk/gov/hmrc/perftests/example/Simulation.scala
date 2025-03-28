/*
 * Copyright 2025 HM Revenue & Customs
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

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.example.Requests._

class Simulation extends PerformanceTestRunner {

  setup("AuthLogin", "logging in via auth") withRequests (
    getAuthLoginPage,
    postAuthLoginPage
  )

  setup("BusinessWithoutId", "Business without Id Journey") withRequests (
    getRegisteredAddressInUkPage,
    postRegisteredAddressInUkPageNo,
    getHaveUTRPage,
    postHaveUTRPageNo,
    getBusinessNamePage,
    postBusinessNamePage,
    getHaveTradeName,
    postHaveTradeName,
    getTradeName,
    postTradeName,
    getBusinessAddress,
    postBusinessAddress,
  )

  setup("ContactDetails", "Contact Details Journey") withRequests (
    getYourContactDetails,
    getContactName,
    postContactName,
    getEmail,
    postEmail,
    getHavePhone,
    postHavePhone,
    getPhone,
    postPhone,
    getSecondContact,
    postSecondContact,
    getSecondContactName,
    postSecondContactName,
    getSecondContactEmail,
    postSecondContactEmail,
    getSecondContactHavePhone,
    postSecondContactHavePhone,
    getSecondContactPhone,
    postSecondContactPhone,
    getCheckYourAnswers,
    postCheckYourAnswers,
    getConfirmationRegistrationPage
  )

  setup("BusinessWithIdLimitedCompany", "Business with ID- select Limited Company") withRequests (
    getRegisteredAddressInUkPage,
    postRegisteredAddressInUkPageNo,
    getHaveUTRPage,
    postHaveUTRPageYes,
    getBusinessTypePage,
    postBusinessTypeLimitedCompany,
    getUTRPage,
    postUTRPage,
    getBusinessNamePageWithId,
    postBusinessNamePageWithId,
    getYourBusinessPage,
    postYourBusinessPage
  )

  setup("BusinessWithIdPartnership", "Business with ID - select Partnership") withRequests (
    getRegisteredAddressInUkPage,
    postRegisteredAddressInUkPageNo,
    getHaveUTRPage,
    postHaveUTRPageYes,
    getBusinessTypePage,
    postBusinessTypePartnership,
    getUTRPage,
    postUTRPage,
    getBusinessNamePageWithId,
    postBusinessNamePageWithId,
    getYourBusinessPage,
    postYourBusinessPage
  )

  setup("BusinessWithIdLimitedPartnership", "Business with ID -  select Limited Partnership") withRequests (
    getRegisteredAddressInUkPage,
    postRegisteredAddressInUkPageNo,
    getHaveUTRPage,
    postHaveUTRPageYes,
    getBusinessTypePage,
    postBusinessTypeLimitedPartnership,
    getUTRPage,
    postUTRPage,
    getBusinessNamePageWithId,
    postBusinessNamePageWithId,
    getYourBusinessPage,
    postYourBusinessPage
  )

  setup(
    "BusinessWithIdUnincorporatedAssociation",
    "Business with ID -  select Unincorporated Association"
  ) withRequests (
    getRegisteredAddressInUkPage,
    postRegisteredAddressInUkPageNo,
    getHaveUTRPage,
    postHaveUTRPageYes,
    getBusinessTypePage,
    postBusinessTypeUnincorporatedAssociation,
    getUTRPage,
    postUTRPage,
    getBusinessNamePageWithId,
    postBusinessNamePageWithId,
    getYourBusinessPage,
    postYourBusinessPage
  )

  runSimulation()
}
