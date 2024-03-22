# Assignment Readme Guidelines

Overview:

Patrick Fabrin

Since we should comeplete the Hotel assigment this week. and I did it last week i've added the same disciption from last week, just with added links for ease of navigation:

Part I: Testing the Hotel API:

The assignment was a continuation from the previous week's work, located in the week 5 assignment folder. Detailed instructions on where to find specific tasks and implementations are provided below:

Testing of Endpoints:

Testing of original endpoints from last weeks assignment has been completed. Test files can be found in the excerciseWithJavalinAndCrud -> controllers directory. Specifically, look for RestAssurredHotelTest and RestAssuedRoomTests for endpoint testing.

https://github.com/Odiegenx/3sem-assignments-Patrick-cph-pf73/tree/main/test/excerciseWithJavalinAndCrud/controllers

I did not have time to implement tests for the different DAO's.

Which i properly should have done druring this week.. but used my time setting up a new secrurity project.

Part II: Security Exercise:

Part 1: Hashing Passwords (Completed):

Password hashing functionality has been implemented. Passwords are hashed in the prepersist method of the User Entity.

https://github.com/Odiegenx/3sem-assignments-Patrick-cph-pf73/blob/main/week05/excerciseWithJavalinAndCrud/secrurity/User.java

Part 2: Login and Register Endpoints (Completed):

Logic for both the Register and Login endpoints has been implemented. Both returns a valid token when a register or login is succesfull. I've done succesfull "tests" in JavalinAndCrud.http, which just ran the code and printed out "great succes from 'which ever role i was testing'" if the token was autenticated. 

https://github.com/Odiegenx/3sem-assignments-Patrick-cph-pf73/blob/main/week05/excerciseWithJavalinAndCrud/JavalinAndCrud.http


Part 3: Protect Endpoints (Completed):

Endpoint protection has been implemented to restrict access to authorized users. The logic for protected routes can be found in the getProtectedRoutes method within the Routes class. Authentication is enforced as a prerequisite to accessing protected endpoints.

Added protection to each end point and made sure they are all checked before access is allowed. Login and register user is the only end points that can be accesed with out either an user or admin role.

https://github.com/Odiegenx/3sem-assignments-Patrick-cph-pf73/blob/main/week05/excerciseWithJavalinAndCrud/config/Routes.java

hope the above discription will help navigating my code.
