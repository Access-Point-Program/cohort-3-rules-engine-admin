# Rules Engine Admin

This application will be responsible for creating a User Interface that will allow users to edit business rules. These rules will be for how the Robot should move through the Factory. For example, IF there is a wall in front of the robot AND there is a wall to the right of the robot THEN turn left. The rules engine that we will use is called JSON rules which enables simple flat rulesets. There is already an example UI for these rules that can be used for guidance. This application should also track the name of the ruleset. The operators enabled for the Rules UI should be what is available through the rule’s engine. The rules engine requires “facts” or information to run. The facts will be a 3 x 3 grid of the spaces around the robot. 

### Technologies:
- Java / SpringBoot / Rest Assured
- Angular v16.2.8 / Typescript / Jasmine / Karma
- Docker / Jenkins
- Postgres / SQL
- Postman

### Team Members

- Scott Schmelzle
- Zach Duncan
- Sophia Hales
- Cole Nelson
- Sergio Rodas

### Requirements:

- 1 React / JavaScript / Redux application.
  - 1 Page for creating a new Ruleset.
  - 1 Page for editing Rulesets that have been created.

- 1 Java / SpringBoot API
  - 1 GET endpoint to retrieve all Rulesets (id, name, date created).
  - 1 GET endpoint to retrieve a Ruleset with its Rules and Conditions.
  - 1 POST endpoint for creating a new Ruleset and its Rules and Conditions.
  - 1 PUT endpoint for updating a Ruleset and its Rules and Conditions.
  - 1 DELETE endpoint for deleting a Ruleset and its Rules and Conditions.

# Setting Up PostgreSQL Tables in pgAdmin

- Right-click database -> Query Tool
- Paste in code from [sql/create.ddl](https://github.com/Access-Point-Program/cohort-3-rules-engine-admin/blob/2fa165b962dda1049bb8ee32e7312352060c6410/sql/create.ddl) file
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
6.
   - Go to the following URl 'http://localhost:8080/#/create-ruleset' to create a rule.
   - Go to the following URl 'http://localhost:8080/#/update-ruleset/{id}' to edit a rule ("{id}" should be replaced with the id of the rule you want to edit).
