# Rules Engine Admin

This application will be responsible for creating a User Interface that will allow users to edit business rules. These rules will be for how the Robot should move through the Factory. For example, IF there is a wall in front of the robot AND there is a wall to the right of the robot THEN turn left. The rules engine that we will use is called JSON rules which enables simple flat rulesets. There is already an example UI for these rules that can be used for guidance. This application should also track the name of the ruleset. The operators enabled for the Rules UI should be what is available through the rule’s engine. The rules engine requires “facts” or information to run. The facts will be a 3 x 3 grid of the spaces around the robot. 

### Technologies:
- Java / SpringBoot / Rest Assured / Swagger
- JavaScript / React / Redux / Jest
- Cypress
- Docker / Jenkins
- Postgres / SQL / Firebase
- Python
- Postman

### Team Members

- Scott Schmelzle
- Zach Duncan
- Sophia Hales
- Cole Nelson
- Sergio Rodas

### Requirements:

- 1 React / JavaScript / Redux application.
  - 1 Page for viewing the edited version of the Ruleset.
  - OPTIONAL 1 Page for viewing the read-only version of the Ruleset.

- 1 Java / SpringBoot API
  - 1 GET endpoint to retrieve the Ruleset and Rules.
  - 1 POST endpoint for creating a new Ruleset and Rules.
  - 1 PUT endpoint for updating the Ruleset and Rules.

# Setting Up PostgreSQL Tables in pgAdmin

- Right-click database -> Query Tool
- Paste in code from sql/create.ddl file
- Click play button  (Execute/Refresh)
- Right-click database -> Refresh
- Verify that ruleset, rule, and condition table are under the Tables tab.
- Verify that eventtype, facttype and valuetype are under the Types tab. 

# Running the entire application

1. Clone down the repository
2. Run sql code to create the required tables
3. Open the project and __navigate into the `front-end` directory__, then run `npm install`
4. After it has finished installing, __navigate back to the root directory__, and run `mvn clean install`
5. Once the project has finished building, then you can run the executable jar file. (Below)
(Depending on terminal '\' might need to be changed to '/') 
`java -jar .\target\rules-engine-0.0.1-SNAPSHOT.jar`  or `java -jar ./target/rules-engine-0.0.1-SNAPSHOT.jar`
6. Go to the following URl 'http://localhost:8080/#/create-ruleset'