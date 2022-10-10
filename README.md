# Recipe finder

This application is built as an assessment, and is a REST API application built using Spring Boot and Maven.

The application allows a user to perform the following basic set of tasks:
- Adding a new recipe
- Fetching a list of recipes
- Fetching a recipe by identifier
- Updating a recipe
- Deleting a recipe


Additionally users are able to filter using request parameters based on the following criteria:
-. Whether or not the dish is vegetarian (boolean)
-. The number of servings (integer)
-. Specific ingredients (either include or exclude) (as a comma separated list of ingredients)
-. Text search within the instructions.

## Building the project
The project can be built using the maven command line. Ensure that you have Apache Maven installed and added to the PATH variable in order to access it from the cmd line, or are using a compatible IDE such as Eclipse/IntelliJ
```
  mvn clean install
```
  
## Running Tests
  ```
  mvn test
  ```
  
## Running the application
We can run the spring boot application by first building the project, and then within the project folder, execute:
```
  mvn spring-boot:run
  ```
  

## API Documentation
API Documentation for the endpoints has been done using Swagger(OpenAPI 3.0). On running the application, the API documentation can be viewed at:
 ``` 
 <localhost>/swagger-ui/index.html
 ```
Usually, the value for ```<localhost>``` would be ```localhost:8080```

## Database
The application uses an H2 embedded database as the datasource, and the entities are created on startup of the application. After startup, the database can be accessed by navigating to:
```
http://localhost:8080/h2console
```
The configured username is ```sa``` and there is no password.
Simply click on ```Connect``` to proceed to the database console.

## Sample Request

### Adding a new recipe
The following endpoint is called to add a new recipe:
```
POST: <localhost>/recipes/
```
With an example request body as follows:
```
{
  "name": "Malai Kofta",
  "type": "VEGETERIAN",
  "ingredients": [
    {
      "name": "Ricotta",
      "amount": "1 cup"
    },
{
      "name": "Mashed potatoes",
      "amount": "1 cup"
    },
{
      "name": "Oil",
      "amount": "2 tbsp"
    }
  ],
  "serves": 4,
  "instructions": "Mix ricotta and potatoes, make tiny balls and fry them."
}
```
On successful creation we would receive a status of ```201``` denoting ```CREATED``` and a url in the ```location``` header. In the above case, it is:
```
http://localhost:8080/recipes/1
```

### Retrieving a list of recipes
We can retrieve a list of recipes with the following endpoint
```
GET <localhost>/recipes/
```
With the above request, we would receive a sample response such as:
```
[
   {
      "id":1,
      "name":"Mashed Potatoes",
      "type":"VEGETERIAN",
      "serves":6,
      "instructions":"guess",
      "ingredients":[
         {
            "id":4,
            "name":"Salt",
            "amount":"1 tsp"
         },
         {
            "id":3,
            "name":"Butter",
            "amount":"1 cup"
         },
         {
            "id":2,
            "name":"Potato",
            "amount":"4 pcs"
         }
      ]
   }
]```
