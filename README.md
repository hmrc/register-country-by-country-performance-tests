# register-country-by-country-performance-tests
Performance test suite for the `register-country-by-country-reporting-frontend`, using 'performance-test-runner' under the hood.


## Running the tests

Prior to executing the tests ensure you have:
* Installed/configured service manager

Run the following command to start the services locally:
```
sm2 --start CBCR_NEW_ALL
```
Using the `--wait 100` argument ensures a health check is run on all the services started as part of the profile. `100` refers to the given number of seconds to wait for services to pass health checks.


## WARNING :warning:
Do **NOT** run a full performance test against staging from your local machine. Please [[implement a new performance test job]](https://github.com/hmrc/register-country-by-country-performance-tests) and execute your job from the dashboard in [[Performance Jenkins]](https://performance.tools.staging.tax.service.gov.uk/job/Country%20By%20Country%20Reporting%20-%20CBCR/job/register-country-by-country-performance-tests/)
#### Smoke test

It might be useful to try the journey with one user to check that everything works fine before running the full performance test
```
sbt -Dperftest.runSmokeTest=true -DrunLocal=true Gatling/test
```

#### Running the performance test
```
sbt -DrunLocal=true Gatling/test
```
### Run the example test against staging environment

#### Smoke test
```
sbt -Dperftest.runSmokeTest=true -DrunLocal=false Gatling/test
```

#### Run the performance test

To run a full performance test against staging environment, implement a job builder and run the test **only** from Jenkins.

### Scalafmt
 This repository uses [Scalafmt](https://scalameta.org/scalafmt/), a code formatter for Scala. The formatting rules configured for this repository are defined within [.scalafmt.conf](.scalafmt.conf).

 To apply formatting to this repository using the configured rules in [.scalafmt.conf](.scalafmt.conf) execute:

 ```
 sbt scalafmtAll
 ```

To format the `*.sbt` and `project/*.scala` files, use:

```bash
sbt scalafmtSbt
```

 To check files have been formatted as expected execute:

 ```
 sbt scalafmtCheckAll scalafmtSbtCheck
 ```