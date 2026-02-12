import sbt._

object Dependencies {

  private val gatlingVersion = "3.4.2"

  val test = Seq(
    "com.typesafe" % "config"                  % "1.3.1" % Test,
    "uk.gov.hmrc" %% "performance-test-runner" % "6.3.0" % Test
  )
}
