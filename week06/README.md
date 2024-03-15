# Assignment Readme Guidelines

## Overview

- Patrick Fabrin

The assignment was a continuation from the previous week's work, located in the week 5 assignment folder.
Detailed instructions on where to find specific tasks and implementations are provided below:

Testing of Endpoints:

Testing of original endpoints from last weeks assignment has been completed.
Test files can be found in the excerciseWithJavalinAndCrud -> controllers directory.
Specifically, look for RestAssurredHotelTest and RestAssuedRoomTests for endpoint testing.

Part 1: Hashing Passwords (Completed):

Password hashing functionality has been implemented.
Passwords are hashed in the prepersist method of the User Entity.

Part 2: Login and Register Endpoints (Completed):

Logic for both the Register and Login endpoints has been implemented.
Both returns a valid token when a register or login is succesfull. I've done succesfull "tests" in JavalinAndCrud.http, 
which just ran the code and printed out "great succes from 'which ever role i was testing'" if the token was autenticated. 
I did not have time to set up real tests with the help of Rest Assured.
Check Routes / UserController / ApplicationConfig for the relevant code.


Part 3: Protect Endpoints (Completed):

Endpoint protection has been implemented to restrict access to authorized users.
The logic for protected routes can be found in the getProtectedRoutes method within the Routes class.
Authentication is enforced as a prerequisite to accessing protected endpoints.

hope the above discription will help navigating my code. 
