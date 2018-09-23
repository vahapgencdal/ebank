# ebank  

## Online money Transfer between Accounts

 I used below technogologies for project
- Embedded tomcat for deploy
- Jersey Server For JunitTests
- Slf4j for Logging
- Junit for testing
- Jersey Servlet for RestFull Api
- Google Guice for Dependency Injection
- Jackson for Object Mapping.
- Java 8 Scheduled Task Executer for Transactions.
- Google Guava for Some List manipulations like Ordering Arralist
- org.javamoney used for Currencies

### 1. install dependencies

execute `mvn clean install` under ebank directory.  
after `mvn clean install` all test run automatically

### 2.  start application

execute `tomcat7:run` under ebank directory