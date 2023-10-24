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
- Paste:

CREATE TABLE ruleset (
   id BIGSERIAL PRIMARY KEY,
   name TEXT NOT NULL,
   creation_date TIMESTAMP NOT NULL
);

CREATE TYPE EventType AS ENUM('FORWARD', 'RIGHT', 'LEFT');
CREATE TABLE rule (
   id BIGSERIAL PRIMARY KEY,
   ruleset_id BIGSERIAL NOT NULL,
   FOREIGN KEY(ruleset_id) REFERENCES ruleset(id),
   priority DOUBLE PRECISION NOT NULL UNIQUE,
   event_type EventType NOT NULL
);

CREATE TYPE FactType AS ENUM('FRONT', 'RIGHT', 'LEFT', 'BEHIND');
CREATE TYPE ValueType AS ENUM('WALL', 'EMPTY', 'END');
CREATE TABLE condition (
   id BIGSERIAL PRIMARY KEY,
   rule_id BIGSERIAL NOT NULL,
   FOREIGN KEY(rule_id) REFERENCES rule(id),
   fact_type FactType NOT NULL,
   value_type ValueType NOT NULL
);

- Click play button  (Execute/Refresh)
- Right-click database -> Refresh
- Verify that ruleset, rule, and condition table are under the Tables tab.
- Verify that eventtype, facttype and valuetype are under the Types tab. 
