Spring Boot
---
1. [Creating Your First Spring Boot Application](#creating-your-first-spring-boot-application)
2. [Spring Boot - Efficient Development, Configuration and Deployment](#spring-boot---efficient-development-configuration-and-deployment)
3. [Spring Big Picture](#spring-big-picture)

----------------------

## Creating Your First Spring Boot Application
* `parent` should be `org.springframework.boot.spring-boot-starter-parent`
* dependency to `spring-boot-starter-web`


## Spring Boot - Efficient Development, Configuration and Deployment
* Enabling the Auto Configuration Report
  * cmd line arg `--debug`
  * VM arg `-Ddebug`
  * Environment var `export DEBUG=true`
  * _application.properties_ `debug=true` or `logging.level.=debug`
  * many more

* Resolving Configuration in Spring Boot (cascading resolution - from top to bottom)
  * command line args, start with double dash. e.g. `--server.port=8080`
  * Inline JSON in special-named environment variable
  * Standard Servlet Environment
    * ServletConfig
    * ServletContext
    * JNDI
    * System.getProperties
    * Systen Environment Variables
  * Random Value Property Source    
  * `application.properties` / YAML + variants
    * profile-spedific config 1st
      * application-{profile}.yml   
    * generic config 2nd
      * application.yml
    * check locations
      * $CMD/config AND $CMD
      * classpath:/config AND classpath:
  * @PropertySource
    * `@PropertySource("/some/path/foo.properties`
    * only `*.properties` supported, not YAML
  * Default Properties on Spring application
    * `SpringApplication.setDefaultProperties(...)`

* Monitoring your apps in the cloud
  * Spring Boot Actuator
  * builtin production ready endpoints
    * `/autoconfig /beans /configprops /dump /health`

* Deploy Spring Boot apps to the cloud using Docker
  * Docker + Spring Boot
  * [docker-maven-plugin](https://github.com/spotify/docker-maven-plugin)
    * add plugin in `pom.xml`
    * run cmd `./mvnw docker:build`


## Spring Big Picture

#### Spring Framework
* Six key areas
  * Core
    * is a dependency injection container
    * is a clue
  * Web
    * handling web requext
    * Spring Web MVC
      * servlet _(A servlet is an obejct that receives a request and generates a response based on that request --Wikipedia)_
    * Spring Webflux
      * reactive programming
      * Webflux handles web requests differently
        * asynchronous execution
        * doesn't block(wait)
          * better resource utilization
  * AOP 
    * aspect-orentied programming
  * Data Access
  * Integration
    * RMI
    * Messaging
    * Web services
      * `RestController` - expose REST web service
      * `RestTemplate` - invoke REST web service
  * Testing












