# API_Rate_Limiter
With this project I will try to explore AOP with spring boot and build a basic Rate Limiter Application.
Project will also cover the scheduling options to reset entries for a certain user after time period ends.


Basically different user can hit a dummy api not more than a certain amount of time(Five for now).
Each user has a waiting period of 5 minutes. At the Sixth time and beyond,
user will receive an error response with a queue serial number and a maximum estimated wait time period. 
In the background application will take care of the queued request and process them one by one maintaining
a specific time interval(For now 30 seconds).
If a user's waiting period is over user is welcome to make new hit unless user's quota is filled up with requests that was
queued already. Meaning a scheduler will track each user's queued requests too. And as soon as hit window gets opened for an user,
queued request will be processed for that user and hit count will be increased.

@USE
1. We have exposed an dummy api with route "/api/v1/dummy".
2. Create an env.properties file in this location "src/main/java/resources"
3. Fill these properties on env.properties
   LOCAL_DB_URL, 
   LOCAL_DB_USER, 
   LOCAL_DB_PASS .
4. Open POSTMAN and try to hit "localhost:8080/api/v1/dummy"
5. Add this token as Authorization --> dummyTokenForRateLimiterApplication_ + {{ anyName }}
6. Application will allow upto 5 hits per minute, After then an error response will be sent. Application will allow hits after a minute or so.
7. Application will differentiate between user with {{ anyName }} property.
