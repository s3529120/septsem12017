# SEPT Assigment - Booking Site

This project is our work for the course Software Engineering: Processes and Tools, semester 1, 2017.

## Tutorial
- Amir Homayoon Ashrafzadeh
- Friday 12:30 - 2:30 PM

## Peer Assessment

### Thomas Higgins - 25%
#### Lead Developer
- Business hours functionality.
- Tied existing elements to a business.
- Reconfigured functionality to reflect business ties.
- Colour selection functionality.
- Bookings filtering.
- Class diagram.
- Architecture diagram.
- Converted account model creation to factory.
- Customer view/cancel own bookings functionality and base elements


### Declan McDonald - 25%
#### UI Designer, developer
- Wireframes for admin menu, business settings menu and create business
- User stories for admin menu, business settings menu and create business
- Created new color themes and implemented across pages
- Assisted with various layout elements

### Jasmine Ellis - 25%
#### Scrum master, developer
- Facilitated meetings
- Kept Trello up to date
- Kept testing suite up to date with application, ran suite
- MVC diagram
- Layout implementation
- Assisted with debugging several functions
- Video pitch


### Rob Laine-Wyllie - 25%
#### Tester, developer
- Input validation on user registration page
- Input validation on add employee page
- General bug-fixing
- General code abstraction to some methods

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

The required jar files for this project should be contained in the root of the ```src/``` directory. If they aren't, here's how you can get them:

_Jar Files_

* [sqlite-jdbc-3.16.1.jar](https://bitbucket.org/xerial/sqlite-jdbc/downloads/sqlite-jdbc-3.16.1.jar)
* [jUnit](https://github.com/junit-team/junit4/wiki/Download-and-Install) 
    (Requires hamcrest-core)
* [hamcrest-core](http://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar)

_Using Maven_

The required pom file is shipped with this source. Go down to installing for further details.

### Installing
_Using Maven_

Make sure that you have maven installed on your system using ```mvn --version```; It should display something like the following: 
```
Apache Maven 3.5.0 (ff8f5e7444045639af65f6095c62210b5713f426; 2017-04-04T05:39:06+10:00)
Maven home: /usr/local/Cellar/maven/3.5.0/libexec
Java version: 1.8.0_73, vendor: Oracle Corporation
Java home: /Library/Java/JavaVirtualMachines/jdk1.8.0_73.jdk/Contents/Home/jre
Default locale: en_AU, platform encoding: UTF-8
OS name: "mac os x", version: "10.12.4", arch: "x86_64", family: "mac"
``` 

Where details will reflect your system. If maven is not installed, install it before continuing.

Firstly, after downloading the repository as a zip, unzip the repository and navigate to 'Booking Site', just under its root directory.
Once there, from the command line, if using maven to build, run
```
mvn build
```
then,
```
mvn compile
```
and finally,
```
mvn install
```
This should leave you with two nice little .jar files, ready to execute. The first of these files will be suffixed with -seed.jar file. Running this file will set up the database for future uses. This can be exited as soon as the login screen appears, and the app can be restarted using the second .jar file. The second of these files will be used in future uses, and it will keep the database after each usage. 

_Without Maven_
To build the jar, simply use 
``` javac -jar inputfiles outputfiles ```

Both versions of the application can be run by simply double clicking on the desired jar file or by entering the following command into command prompt
```java -jar bookingsiteclear.jar```
Or
```java -jar bookingsitekeep.jar```

## Running the tests
Unit tests for this project are contained in the 

The unit tests cover most of the functionality of the app and can be run using from command line
```
mvn test
```
This will build the project in the test scope and output a .jar file

Each test class is in its own java class file, and they are executed from the TestRunner class. Out put from these tests will be displayed in a neat format.

### Known Issues
A list of the current known issues in the release version of the project can be found on our [issues page](https://github.com/s3529120/septsem12017/issues)

## Built With
* Maven

## Versioning

We aim use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/s3529120/septsem12017/tags). 

## Links

Find useful links such as [trello, slack, and leantesting here.](https://github.com/s3529120/septsem12017/blob/master/URLs.md)

### Pitch Video
[Link to pitch video here](https://vimeo.com/219203264)

## Authors

* Thomas Higgins - s3529120
* Declan McDonald - s3488797
* Jasmine Ellis - s3449107
* Rob Laine-Wyllie - s3433096

See also the list of [contributors](https://github.com/s3529120/septsem12017/graphs/contributors) who participated in this project.

## Acknowledgments

Initial README.md file template: 
* **Billie Thompson** - *Initial work for readme file* - [PurpleBooth](https://github.com/PurpleBooth)