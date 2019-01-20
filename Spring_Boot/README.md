Spring Boot
---
1. [Spring Big Picture](#spring-big-picture)
1. [Java Micro-services Spring Cloud Developing Services](#java-microservices-spring-cloud-developing-services)
1. [Creating Your First Spring Boot Application](#creating-your-first-spring-boot-application)
1. [Spring Boot - Efficient Development, Configuration and Deployment](#spring-boot---efficient-development-configuration-and-deployment)
1. [Springboot - Microservice](#spring-boot-micro-services-architecture)

----------------------

# Spring Big Picture

## Spring Framework
* Six key areas
  1. Core
      * is a dependency injection container
      * is a clue
  2. Web
      * handling web request
      * spring web mvc
        * servlet _(a servlet is an obejct that receives a request and generates a response based on that request --wikipedia)_
      * spring webflux
        * reactive programming
        * webflux handles web requests differently
          * asynchronous execution
          * doesn't block(wait)
            * better resource utilization
  3. AOP 
      * aspect-oriented programming
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
Tools for developers to quickly build some common patterns in **distributed systems** (e.g. config management, service discovery, circuit breakers, intelligent routing, cluster state...)
Based on Spring Boot
Micro-service

## Spring Security
* Authentication (verifying the identify of a user) 
* Authorization (checking an authenticated user whether the user has the rights)



# Java Micro-services Spring Cloud Developing Services

## Intro to Micro-services, Spring Boot and Spring Cloud

### Micro-services
#### core characteristics
* components exposed as services
* tied to a specific domain (micro in micro-service, small scope of the service)
* loosely coupled
  * deploy separately
* built to tolerate failure
* delivered continuously via automation
* built and run by independent teams

#### Why are micro-services architectures popular?
  * desire for faster changes
  * need for greater availability
  * motivation for fine-grained scaling
  * compatible with a DevOps mindset

### Spring Boot
* Convention, not configuration
* Opinions can be overridden
* Simple dependency management
* Embeds app server in executable JAR
* Build in endpoints for health metrics

## Simplifying Env Management with Centralized Configuration
### Spring Config
* Configurations are loaded based on `app name, spring profile, label`

#### Config Server
1. add `spring-cloud-config-server` and `spring-boot-starter-actuator` in `pom.xml`
2. add `@EnableConfigServer` annotaton to class
3. create application properties 

#### Config Client
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

### Centralized Configuration using Git
* **TODO**
  * Auth
  * Encryption and Decryption
  * Refreshing configuration

## Offloading Async Activities with Lightweight, Shot-lived Tasks
#### Asynchronous processing in Micro-services
  * reduce dependencies between services
    * messaging is a key component
  * support low latency, high throughput
  * Facilitate event-driven computing
    * no waiting

#### Problems with Status Quo (synchronous communication)
  * Waste resources - consuming resources even when services aren't in use
  * Heavy - services baked into monolithic deployments (all in one)
  * Inflexible - challenges scaling services on demand
  * Hard debugging - Difficulty tracing services calls

#### What Exactly Is "Server-less" Computing
  * Deploy "function" instead of "application"
  * Run code without know of infrastructure
  * Automatic horizontal scaling
  * start fast, run short

#### Spring Cloud Task
**Short-lived, asynchronous micro-services**
* Spring (Boot) with access to beans
* Task is stateless
* Bootstrap logic with Runner
* Can subscribe to life-cycle events

#### OAuth2.0
* OAuth is a protocol for authorization
* provides authorization flow for various clients
  * gets a toke after authentication
  * check token with session on authorization server
* Obtain limited access to use accounts
* Separates idea of user and client
* Access token carries more than identity
  * token-based
* NOT a authentication scheme

* Roles
  * resource owner _(e.g. a facebook user)_
  * client application _(application using by user)_
  * resource server _(e.g. user's friends list in facebook)_
  * authorization server _(facebook's authorization server)_
    * resource server may be together with authorization server

* **TODO: performance tuning**


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
    * System Environment Variables
  * Random Value Property Source    
  * `application.properties` / YAML + variants
    * profile-specific config 1st
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
  * built-in production ready endpoints
    * `/autoconfig /beans /configprops /dump /health`

* Deploy Spring Boot apps to the cloud using Docker
  * Docker + Spring Boot
  * [docker-maven-plugin](https://github.com/spotify/docker-maven-plugin)
    * add plugin in `pom.xml`
    * run cmd `./mvnw docker:build`



# Spring Boot (Micro-services Architecture)
From iQiYi.

### Servlet
* two ways to registe servlet
  1. annotation
  ```Java
  @WebServlet(name="myServlet", urlPatterns="/myserv")
  class MyServletClass extends HttpServlet {}

  @ServletComponentScan
  public class App { }
  ```
  2. method
  ```Java
  @Bean
  public  ServletRegistrationBean getxxxx() { }
  ```
  
### Exception in SpringBoot
1. Customized error page
  * `/error` page is the default page when exception happens in Springboot
  * `BasicExceptionController` 
2. `@ExceptionHandler` 
  * Cons: each controller has its own exception handlers, exception handlers cannot work across controllers
  ```Java
  // In @Controller class
  @ExceptionHandler(value={java.lang.ArithmeticException.class})
  public ModelAndView anyFunName(Exception e) {
      ModelAndView mv = new ModelAndView();
      mv.setAttribute("key", e.toString());
      mv.setView("redirect page");
      return mv;
  }
  ```
3. `@ControllerAdvice` + `@ExceptionHandler`
  * Exception handlers are defined in **shared** exception handler class, not in specific controllers
4. `SimpleMappingExceptionResolver`
  * Mapping specific exceptions with views
  * Cannot transport exception info
5. Customized `HandlerExceptionResolver`
  ```Java
  public ModelAndView resolverException() { }
  ```

### Test
```Java
// junit + spring
@RunWith(SpringUnit4ClassRunner.class)
// 1. springboot test class;  2. springboot startup classes
@SpringBootText(classes={App.class})
public MyTest {
  @AutoWired
  private MyDaoImpl myDaoImpl;
  @Test
  public void testCase() {
    myDaoImpl.saveUsers();
  }
}

```

### Springboot Hot Deploy
1. SpringLoader
  * Hot deploy
  * only works to server side Java code, but not frontend pages
  1. a maven plug-in
    * works when run with `Maven build (spring-boot:run)`
    * Plugin works as a backend process, needs to be ended manually by killing process
  2. springloader jar file under `lib` dir
    * Run Config -> VM arguments " javaagent ..."
    * does not need to kill process manually

2. DevTools
  * Re-deploy, not hot deploy
  * works for both java code and html page

### JPA
* application.properties
```sh
spring.datasource.driverClassName=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/databasename
spring.datasource.username=admin

spring.datasource.type=com.alibaba.druid.pool.DruidDataSource # pool

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

* Dao interface
```Java
class my class extends JpaRepository<User, Integer> {
  // Users: entity class
  // Integer: type of key
}

// class Repository<T, ID>
```
* CrudRepository
* PagingAndSortingRepository
* JpaRepository
* JPASepecificationExecutor
* `@OneToMany` and `@ManyToMany`


* HQL v.s. SQL
`@Query(<HQL/SQL>)`
1. HQL is object(instance) based; SQL is database based
2. HQL: `from <ClassName> where <condition based on class' properties>`
3. SQL: `from <table name in database> where <sql condition>`  
4. HQL does NOT need key words `insert, update, delete`, it uses methods `save(), update(), delete()`  

* Notes
  * When using `@Test` and `@Transactional` together, transaction rollback automatically
  * `@Rollback(false)` can resove above issue 


### Quartz
* Job scheduling framework
* `QuartzConfig`
  * Job
  * Trigger
  * Scheduler 
* Cron
 ```Java
  // Sechonds Minutes Hours Day Month Week [Year] 
  @Scheduled(cron="0/2 * * * * ?") // every 2 seconds
  @Scheduled(cron="3 * * * * ?") // every minutes' 3rd seconds
  ```

### Caching
* Ehcache
  ```Java
  @EnableCaching // for App.class
  @Cacheable(value="customizedCacheName", key="#parameterName")      // for method
  @CacheEvit(value="cacheName", allEntries=true)   // clear cache 
  ```
* Redis
    
























