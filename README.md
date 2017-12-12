# This is a simple demo of a nursery checkin/out system written in Java with Maven and Spring Boot.

## Installing

### To start local server:
```
mvn spring-boot:run
```

### To package:
```
mvn package
```

## Operation

### Children details

When started, the system contains demo data so GET requests to see children data:

http://localhost:8080/children/

http://localhost:8080/children/1

http://localhost:8080/children?checkedIn=true

### Checkins and checkouts

Checkins and checkouts use different endpoints and you can check in or out a child by posting here:

http://localhost:8080/children/1/checkins

http://localhost:8080/children/1/checkouts

### Reporting

Both checkins and checkouts can be fetched using an endpoint:

http://localhost:8080/report?start=0&stop=1512837144035

Parameters: start and stop are mandatory and require a timestamp. Currently values 0 skip any filtering (for the demo purposes).

http://localhost:8080/report?start=0&stop=0

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
