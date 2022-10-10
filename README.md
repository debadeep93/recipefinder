# Recipe finder

This application is built as an assessment, and is a REST API application built using Spring Boot and Maven.

The application allows a user to perform the following basic set of tasks:
- Adding a new recipe
- Fetching a list of recipes
- Fetching a recipe by identifier
- Updating a recipe
- Deleting a recipe


Additionally users are able to filter using request parameters based on the following criteria:
1. Whether or not the dish is vegetarian (boolean)
2. The number of servings (integer)
3. Specific ingredients (either include or exclude) (as a comma separated list of ingredients)
4. Text search within the instructions.

## Building the project
The project can be built using the maven command line. Ensure that you have Apache Maven installed and added to the PATH variable in order to access it from the cmd line, or are using a compatible IDE such as Eclipse/IntelliJ

  mvn clean install
  
## Running Tests
  mvn test
  
## Running the application
We can run the spring boot application by first building the project, and then within the project folder, execute:
  mvn spring-boot:run
  

## API Documentation
API Documentation for the endpoints has been done using Swagger(OpenAPI 3.0). On running the application, the API documentation can be viewed at
  <localhost>/swagger-ui/index.html
Usually, the value for <localhost> would be localhost:8080


