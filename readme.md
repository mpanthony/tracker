# interview tracker

Simple coding challenge, to create a service that tracked job candidates, scheduled interviews, and captured 
feedback from the interview.

Bootstrap:

1 - Run the create-sql.sql script against your local database.  This creates the schema and populates 
it with test data

2 - Run the tracker service:
mvn spring-boot:run

By default, the service runs on port 8080

3 - Use swagger UI to exercise the API:

http://localhost:8080/swagger-ui.html#/

