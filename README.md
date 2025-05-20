# API_Rate_Limiter
With this project I will try to explore AOP with spring boot and build a basic Rate Limiter Application. Basically different user can hit a dummy api not more than a certain amount of time. Project will also cover the scheduling options to reset entries for a certain user after time period ends.

@USE
1. We have exposed an dummy api with route "/api/v1/dummy".
2. Create an env.properties file in this location "src/main/java/resources"
3. Fill these properties on env.properties
   LOCAL_DB_URL
   LOCAL_DB_USER
   LOCAL_DB_PASS
4. Open POSTMAN and try to hit "localhost:8080/api/v1/dummy"
5. Add this token as Authorization --> dummyTokenForRateLimiterApplication_ + {{ anyName }}
6. Application will allow upto 5 hits per minute, After then an error response will be sent. Application will allow hits after a minute or so.
7. Application will differentiate between user with {{ anyName }} property.