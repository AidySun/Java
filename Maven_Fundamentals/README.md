Maven Fundamentals
----

### Maven is a build tool that
  * is open source and managed by Apache
  * produces one artifact
  * manages dependencies 
  * is a project management tool
  * has versioning and releases
  * describes what your project is doing
  * javadocs and other site info
  * convention over configuration

### Why
  * repeatable builds
  * transitive dependencies
  * contains everything for environment
  * works as a local repo

### Ant was developed to replace Make, (not very) cross platform, built on top of Java and XML.
  * it's not a build tool as much as it is a scripting tool
  * no standard/convention. E.g. one person call a target Clean, another uses Clear, and someone else uses Clear Up...


POM - project object model
* super pom

### Goals
Goals are the plug-ins configured in the maven install. In Super POM.
Golas are tied to a phase.

* clean - delete target dir and generated resources
* compile - compiles code, generates any files, copy to classes dir
* test
* pacakge - run compile first, run any tests, packaging
* install - run `package` command and then installs it in your local repo (~/.m2)
* deploy - run install command and then deploys it to a corporate repo / or shared location, often confused with deploying to a web server
  * deploy doesn't mean deploy to an app server

### Phases
* validate
* compile
* test
* package
* integration-test
* verify
* install
* deploy

### Dependencies
* will be pulled from Maven 
* transitive dependencies - Maven would download required dependencies of dependent libraries 
  * newer version would be used for confliction

### Versions
* SNAPSHOT - latest version (development version), check for update every compile time
* release version doesn't have a naming convention in Maven
* RC - release condidate

### Types
* default is jar
* pom - is referred to as a dependency pom, downloads dependencies from that pom

### Scopes
* for dependencies
* compile - default
* provided - available in all phases, but not included in final artifact
* runtime - need for execution but not for compilation. Avilable for all phases but compilation, not included in final artifact
* test
* system - don't use it
* import - advance topic

### Repositories
* Library Repo
* Plugin Repo
* multiple repositories all allowed
* corporate Repo
  * Nexus - default
  * Artifactory

### Plugin
* compiler plugin
  * default to java 1.5 regardless of what JDK is installed
  * <plugin><configuration><source> and <target> are the two features people overwrite the most to tell it to use a diff target.
  

























