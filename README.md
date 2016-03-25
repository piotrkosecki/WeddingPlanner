# Wedding Planner

The application is akka-http based REST API

## Endpoints

### Adding guest to the list
    
    POST    /guests/

Endpoint is used for adding users in following JSON format:

    {
        "firstName" : "exampleFirstName",
        "lastName" :  "exampleLastName"
    }
    
### Listing current guests

    GET     /guests/ 
    
Accessing this endpoint tou should receive structure like that:
    
    [
        {
            "firstName": "exampleFirstName1",
            "lastName": "exampleLastName1"
        },
        {
            "firstName": "exampleFirstName2",
            "lastName": "exampleLastName2"
        }
    ]
    
## Running the application

If you want to run application with basic settings use:

    sbt run

If you want start it with different options than defined in `application.conf` use for example:

    sbt run -Dhttp.port=9000
    
## Testing the application

For running few simple tests just type:
    
    sbt test
    
## Other 

Persistence was left for future implementation in
    
    /web/DAO/GuestsDAOPersistant.scala
    
When implemented it could be easily switched between in memory implementation with `app.inMemory=false`