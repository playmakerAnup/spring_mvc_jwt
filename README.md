# spring_mvc_jwt

This projct includes all the required configurations in the .xml files. Also Filters involved have been explained with comments.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. Also you can fork it and use it as a template and enhance it further as per your convenience.

### Prerequisites

Spring MVC Legacy Project
Local Tomcat Server v7.0
Redis Server

### Installing

Step 1: Install OS specific Redis-server on your respective system from https://redis.io/topics/quickstart 

Step 2: Run redis using the redis-cli command ("redis-server") as explained in the above link

Step 3: Import the project to your workspace on your respective IDEs and do the rituals (Project clean, maven upate)

Step 4: Run the project on your local Tomcat server and voila!

Following page opens up in the browser

```
Hello world!

The time on the server is xyz.
```

## Running the tests

The project consists of three service -> /signIn, /signUp, /resource

Out of the three /signIn and /signUp services do not require a token for request, instead a token will be recieved in response as it is the initial call from the client to the server.
The /resource is a secured endpoint and will require JWT token to be appende in the header as Bearer Token explained in the below scenario.

### /signIn Request - To generate JWT Token
From Postman,
```
GET: localhost:8080/demo/signIn

Params: raw -> application/json
        {
        "username" : "admin",
        "password": "admin"
        }
Response:
{
    "map": {
        "responseMessage": "Sign In Successfull",
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOiIxMCIsInRpbWVTdGFtcCI6IjIwMTkwMzMxMTY1MDUwIn0.8qvIwEwS47iUeWL85b1d2jHFb2-BOewjtYK8qIX-F2KmSPCZzBb7vjYzGke61vXULL0_tdaXCvh8vxE7_cUsEQ"
    }
}
```

### /resource Request - To pass JWT Token for authentication
From Postman,
```
POST: localhost:8080/demo/resource

Params: Authorization -> Bearer Token -> paste the recieved "token" value
                         (or)
        Headers -> Set Key -> Authorization and Value -> paste the recieved "token" value
Response:
{
    "map": {
        "resource": "Valuable Resource",
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOiIxMCIsInRpbWVTdGFtcCI6IjIwMTkwMzMxMTY1MTA4In0.1AI0riG0q6SNkuccDz_zrzqkCv6fwH1BNbuZIc63OHW7cnRZa74dtolAU01nesLCMrS_d84guWCHsJCrqIYnCg"
    }
}
```

## Built With

* [Spring-MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html)
* [Maven](https://maven.apache.org/) - Dependency Management
* [Tomcat-Server](http://tomcat.apache.org/)
* [Spring-Security](https://docs.spring.io/spring-security/site/docs/3.0.x/reference/security-filter-chain.html)
* [Redis](https://redis.io/topics/quickstart) 

## Authors

* **Anup Nair** - *Initial work* - [playmakerAnup](https://github.com/playmakerAnup)

## Acknowledgments

* Special thanks to Dejan Milosevic whose article I referred to create this project 
(https://www.toptal.com/java/rest-security-with-jwt-spring-security-and-java)

