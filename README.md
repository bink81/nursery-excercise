This is a simple demo of a nursery checkin/out system written in Java with Spring Boot.

1. Console:
a. To start local server:
mvn spring-boot:run

b. To package:
mvn package

2. Operation
a) When started, the system contains demo data so GET requests to see children:
http://localhost:8080/children/
http://localhost:8080/children/1
http://localhost:8080/children?checkedIn=true

b) Checkins and checkouts use different endpoints and you can check in or out a child by posting here:
http://localhost:8080/children/1/checkins
http://localhost:8080/children/1/checkouts

c) Both checkins and checkouts can be fetched using an endpoint:
http://localhost:8080/report?start=0&stop=0
Parameters: start and stop are mandatory and require a timestamp. Currently values 0 skip any filtering (for the demo purposes).
