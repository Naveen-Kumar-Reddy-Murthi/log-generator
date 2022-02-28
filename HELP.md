# Read Me First
The following was discovered as part of building this project:

1. yaml and spring profiles run
2. make sure run job only once when executed from commandline


Use this in intellij in Environmental Variables in Run configurations and set relevant values test, local or prod

Also update application-xxx.properties file. common properties should be in application.properties, only env specific values should be in
different profile property files. Do not repeat properties with same values in different files. will result in run time error
`SPRING_PROFILES_ACTIVE=local/test/prod`


To build the project use below command assuming maven.

`mvn -e -U clean install`


To run the application run below command. Also when selecting profile makesure, you update the properties according to env, otherwise
it will result in error. Also make sure to update below properties as per environment

`app.token.api.url
app.audit.api.url`

If not done, dummy data will be used to generate the log files


Below property is for location for saving the generated files. Make sure to update as per the environments
`app.audit.file.location`


Use below
`java -jar -Dspring.profiles.active=local log-generator-0.0.1-SNAPSHOT.jar`