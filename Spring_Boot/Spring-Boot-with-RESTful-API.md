# Spring Boot with RESTful-163



* RESTful (representational state transfer)
  * stateless
  * CRUD to resources
  * for URL, `POST(create), DELETE, PUT(update), GET`
  * e.g. login & logout : `POST/authorization` & `DELETE/autheorization`

* URI
  * /resource
  * /resource/{id}
  * /reousrce/{id}/subresource

* POJO - plain ordinary java object

* DTO - data transfer object
  * it is transferred POJO
* DAO - data access object
* PO  - persistent object
  * it is persistented POJO


* format `Date` type to Jason
  1. `@JsonFormat` annotation on property
     ```Java
     @JsonFormat(timezone="GTM+8", pattern="yyyyMMdd")
     @JsonFormat(shap=JasonFormat.Shape.NUMBER)  
     ```
  2. `application.yml`
    ```yml
    spring:
      jakson:
        data-fromat: yyyy-MM-dd               # using string format
        timezone: GMT+8
        serialazation:
          write-dates-as-timestamps: true     # or long timestamp, higher priority
    ```

### @RestController
```Java
@RestController
@RequestMappting("/tvseries")
class tvController {

  @GetMapping
  public List<TvSeriesDto> allTv() {
  }

  @GetMapping("/{id}")
  public TvSeriesDto getTvById(@PathVariable int id) {
    // http://localhost::8080/tvseries/103
  }

  @PostMapping
  public TvSeriesDto insertOne(@RequestBody TvSeriesDto tv) {

  }
}
```

* SLF4j / Commons-logging config

```yml
# yml
logging:
  file: target/app.log
  level:
    ROOT: WARN
      com.aidy.services: DEBUG
```
<a name="curl-command-line" />
* CURL command line

```sh
curl -H "Content-type:application/json" -X POST --data '{"id":"110","name":"mason"}' http://localhost:8080/test
curl -X DELETE [-v] http://localhost:8080/service

# -H - header info
# -X - post/delete/put
# -v - detail info
```

* Annotation
```Java
@GetMapping("/path/{id}/{all}")
public XXXDto get(@PathVariable("id") id, @PathVariable("all") boolean getAll) {  }
@PostMapping // (value="/tvseries" consumes=MeidaType.APPLICATON_FROM_URLENCODED_VALUE)
public XXXDto insert(@RequestBody XXXDto dto, // RequestBody is the json (or others) posted from client
                     Authentication auth // org.springframework.security.core.Authentication
    ) {  
}
// post is add, put is update
@PutMapping("/{id:\\d+}")  // RegExp
//@DeleteMapping
public XXXDto update(@PathVariable id, 
          HttpServletRequest request,  // no annotation required for HttpServletRequest, Sping boot handlds it automatically
          @RequestParam(value="name", required=false) String name, // e.g. form data in POST, or path var. required=true is default
                                                                  // it also can be get via request.getParameter("name") 
                                                                  // application/x-www-form-urlencoded
          HttpServletResponse response

    ) {  
  // request.getRemoteAddr()   // request ip address
  // curl -X Put --data '"{"id":"101"}' http://local:80/service/101?name=aidy 
}

public ResultJSON editCompany(@RequestHeader("user-agent") String userAgent) {
}
```

* Upload and download
```Java
// upload 
@PostMapping(value="/{id]/photos", consumes="MULTIPART_FORM_DATA_VALUE")  // multipart/form-data
public void addPhoto(@PathVariable int id, @RequestPama("photo") MultipartFile imageFile) { 
	FileOutputStream fos = new FileOutputStream("target/" + imageFile.getOriginalFilename());
	IOUtils.copy(imageFile.getInputStream(), fos);
	fos.close();
}
// curl -F "photo=@myPic.jpg" http://localhost:8080/1008/photos

// download with binary / byte array
@GetMapping(value="/{id}/icon", produces=MediaType.IMAGE_JPEG_VLAUE) // If produces is not defined, by default is JSON
public byte[] getIcon(@PathVariable int id) { 
	String imgPath = "~/Pictures/job.jpg";
	InputStream is = new FileInputStream(imgPath);
	return IOUtils.toByteArray(is);
}
```

* Confituration
```Java
@Value("{com.name.server.config.name}") // config in applications.properties
private String name;
```

### Validation 
* JSR - is a standard
* Hebernite Validation - is am implementation

TYPE|ANNOTATIONS
--------- | :---------:
All | Null, NotNull
String | NotBlank, Pattern, Size, Email, DecimalMax/Min, Digits
Number | Max/Min, DicimalMax/Min, Negative[OrZero], Digits
Container | NotEmpty, Size
Datetime | Future[OrPresent], Past[OrPresent], 
Boolean | AssertTrue, AssertFalse

* Each annotation can have parameters `groups, message, payload`
* `@Valid` vs `@Validated`
  * Validated can only be used on parameters
  * Validated is from spring
  * Valid is from JSR

* These annotations can be used at
  * Field
  * Properties - get/is method, validate the return of the method
  * Class

* For the cases that one object needs different validations in different cases, `@groups` would be the answer.
  * e.g. for one same service, it has different validation rules in diff cases.

* `BindingResult` - When the validation failes
```Java
// 1. return 400 error code to client
public Object doSomething(@Validated @RequestBody MyClass myclss) {  }
// 2. handle the validation failure in server side, instead of returing 400 
public Object doSomething(@Validated @RequestBody MyClass myclass, BindingResult result) {
  if (result.hasErrors()) {
  }
}
```

### Structure of Web Server
* Layers
  1. App, Web client
  1. Web Controller Layer : `@RestController, @Controller`
  1. Business Logic Layer : `@Service`
  1. Data Access Layer    : `@Repository`
  1. Database

Controller --(`@Autowired MyService myservice;`)--> Service ----> Dao
* for the classes may be in two or more layers, e.g. @Service and @Repository using `@Component`

* Structure of package
  * PBF - package by feature
  * PBL - package by layer

### JavaBean
1. has public constructure with no parameters
2. all properites are private, visited by getter (`isXxx` for boolean) and setter
3. implementes `Serializable` interface

* POJO doesn't have logic, JavaBean could have logic

### Data Transfer between Layers

* Client/App ---_DTO/VO_---> Web Controller ---_DTO/VO_---> Business Logic ---_PO/DAO_--> DataAccess ---_SQL_---> DB
  * in **Business Logic Layer**, it needs the conversion between `DTO` and `PO/DAO`
    * `commons-beanutils`, `dozer`

* Simplized ways
  * Using POJO all the way
    ```Java
    @SpringApplication
    @MapperScan("com.yueji.app.dao") // package level   // or @Mapper in POJO/DO class
    public class Application {
    	//... ...
    }
    ```
  * Using JavaBean instead of POJO
    * Problems: circle reference
  * Public fileds in POJO, no `getter`/`setter`
    * it's not POJO and JavaBean anymore
    * difficult for debugging

### Exceptions
* `RuntimeException` - 500
* `ResourceNotFoundException` - 404


### Testing

#### Assert vs assert
* Assert is from `org.junit.Assert`, assert if from Java
* to enable Java's assert, using `-ea` (enableassertion) parameter when starting JVM
* JVM will quite if assert is false

### Database in Spring

#### Transaction

```Java
@Transational()
```
* Isolation
  * serializable > repeatable read > read committed > read uncommitted 
  * it's better to have retry for failed cases when the isolation is serializable and repeatable read


























