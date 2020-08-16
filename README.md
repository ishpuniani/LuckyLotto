# LuckyLotto
#### A submission for the Poppulo tech task for the Software Engineer role

## Problem Statement
To build a simple REST interface for a lottery system. 
### Lottery Rules
You have a series of lines on a ticket with 3 numbers, each of which has a value of 0, 1, or 2. 
For each ticket if the sum of the values on a line is 2, the result for that line is 10. 
Otherwise if they are all the same, the result is 5. 
Otherwise so long as both 2nd and 3rd numbers are different from the 1st, the result is 1. 
Otherwise the result is 0.
### Implementation
Implement a REST interface to generate a ticket with n lines.
Additionally we will need to have the ability to check the status of lines on a ticket.
We would like the lines sorted into outcomes before being returned.
It should be possible for a ticket to be amended with n additional lines.
Once the status of a ticket has been checked it should not be possible to amend.
We would like tested, clean code to be returned.

## Solution
I have used Spring Boot with Java and PostgreSQL database to build the simple API interface.
For tests, I have used JUnit. Maven is used to run the Spring Boot application.  

The create table queries can be found in the file [schema.sql](/src/main/resources/schema.sql).

The [TicketController](src/main/java/com/poppulo/controller/TicketController.java) 
uses a TicketService implementation which can be found at [TicketServiceImpl](src/main/java/com/poppulo/service/TicketServiceImpl.java). 

I have used a DAO pattern to communicate with the database. 
There are three tables and respective DAOs:
* `tickets` : contains the ticket information
* `lines` : contains the line information
* `lines_in_tickets` : contains the mapping information, "lines in tickets".

The tests can be found in this [directory]().

### Architecture


### Running server
To run the server: 
```shell script
mvn spring-boot:run 
``` 
The server can be accessed at port 8080

### Running tests
To run tests:
```shell script

```

### APIs
The API information(request/response format) can be found here: [API Doc](doc/API.md). 