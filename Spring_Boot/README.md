Spring Boot
---
1. [Spring Big Picture](#spring-big-picture)
1. [Java Microservices Spring Cloud Developing Services](#java-microservices-spring-cloud-developing-services)
1. [Creating Your First Spring Boot Application](#creating-your-first-spring-boot-application)
1. [Spring Boot - Efficient Development, Configuration and Deployment](#spring-boot---efficient-development-configuration-and-deployment)
1. 微服务架构-spring boot 轻松上手

----------------------

# Spring Big Picture

## Spring Framework
* Six key areas
  1. Core
      * is a dependency injection container
      * is a clue
  2. Web
      * handling web requext
      * spring web mvc
        * servlet _(a servlet is an obejct that receives a request and generates a response based on that request --wikipedia)_
      * spring webflux
        * reactive programming
        * webflux handles web requests differently
          * asynchronous execution
          * doesn't block(wait)
            * better resource utilization
  3. AOP 
      * aspect-orentied programming
  4. Data Access
      * focuses on relational database, which is different from _Spring Data_
  5. Integration
      * RMI
      * Messaging
      * Web services
        * `RestController` - expose REST web service
        * `RestTemplate` - invoke REST web service
  6. Testing

## Spring Data  
While **Spring Framework Data Access** is focused one particular type of database, particularly relational database.
**Spring Data** adds new ways to access relational databases, as well as support for many different types of databases. 

JPA - Java Persistent API

## Spring Cloud
Tools for developers to quickly build some common patterns in **distributed systems** (e.g. config management, service discovery, curcuit breakers, intelligent rounting, cluster state...)
Based on Spring Boot
Microservice

## Spring Security
* Authentication (verifying the identify of a user) 
* Authorization (checking an authenticated user whether the user has the rights)



# Java Microservices Spring Cloud Developing Services

## Microservices
### core characteristics
* components exposed as services
* tied to a specific domain (micro in microservice, small scope of the service)
* loosely coupled
  * deploy seperately
* built to tolerate failure
* delivered continuously via automation
* built and run by independent teams

### Why are microservices architectures popular?
  * desire for faster changes
  * need for greater availability
  * motivation for fine-grained scaling
  * compatible with a DevOps mindset

## Spring Boot
* Convention, not configuration
* Opinions can be overridden
* Simple dependency management
* Embeds app server in executable JAR
* Build in endpoints for health metrics

## Spring Config
* Configurations are loaded based on `app name, spring profile, label`

### Config Server
1. add `spring-cloud-config-server` and `spring-boot-starter-actuator` in `pom.xml`
2. add `@EnableConfigServer` annotaton to class
3. create application properties 

### Config Client
* specific config would override default config
  * `application.yml/properties`
  * applications 
    * default - `app1.properties`
    * with label - `app1-qa.yml`
  * Label
* Sample application.properties
  * url would be `http://localhost:8080/application-name`
  ```
  # application.properties
  spring.application.name=app2  # app name
  ```
  
  ```
  # bootstrap.properties

  # these settings won't take effect if in application.properties, 
  # because that is too late for application to get correct profile
  spring.profiles.active=qa     # profile
  spring.cloud.config.uri=http://localhost:8080
  ```























# Creating Your First Spring Boot Application
* `parent` should be `org.springframework.boot.spring-boot-starter-parent`
* dependency to `spring-boot-starter-web`


# Spring Boot - Efficient Development, Configuration and Deployment
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

