### NewsfeedREST App
Test task for a Java Developer position. RESTful app for dealing with news articles via dedicated endpoints.

#### Run
Project uses containerization with Docker Compose for PostgreSQL.
```
In IDE:
  1. clone git repository https://github.com/Lexxkit/NewsfeedRESTapi.git
  2. run Docker on your machine
  3. open 'docker-compose.yaml'  in project and run it OR type 'docker-compose up' in a terminal
  4. run 'NewsApp.java'
```

#### Endpoints 
- SwaggerUI available after launching the app at: http://localhost:8080/swagger-ui/index.html

#### Stack
```
Java 17
Spring Boot 3
Hibernate
PostreSQL
Criteria API
Mapstruct
Lombok
Liquibase
Maven
SpringDoc OpenAPI UI
```

#### Functionality
A REST web application for managing the news feed on a website.
Each news article consists of a name, content, publication date, and the category to which the news article belongs.

Each category contains a name, and multiple news articles can be linked to it.

The application provides the following capabilities:
- view the paginated list of news;
- search for news by category, name and content;
- create and edit news;
- delete news;
- view a list of all categories;
- create category;
- delete category.

#### In Progress
1. Add more unit tests to cover all service methods.
2. Containerize the whole app with docker-compose.
3. Add Test-containers for integration testing.