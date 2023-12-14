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

# Setting Up Database Locally
- Create a user
- Create Environmental Variables
- Create a server
- Set Up PostgreSQL Tables in pgAdmin

# Creating User
- Start by logging into the Postgres 15 Server, and expanding it.
- Right-click Login/Group Roles -> Create -> Login/Group role
- General: 
  - Name = rules-engine-admin
- Definition:
  - Password = put a password here you want to use
  - Connection limit = -1
  - Privileges = Can login, Inherit rights from the parent roles
- Save

# Setting Up Environmental Variables
- In Windows search bar, type "Environmental variables"
- Click "Edit the system environmental variables"
- Click "Environmental Variables..." button (towards bottom right of screen)
- Under "System variables" click "New..." button
- For our Username
  - Variable name = rules_engine_username
  - Variable value = our database username (rules-engine-admin)
- For our Password
  - Variable name = rules_engine_password
  - Variable value = same password as before 
- MAKE SURE TO PUSH OK TWICE
- After it has been saved and you close out, ensure you restart your IDE

# Creating the Database
- Right-click the "Databases" directory
- Click on "Create", "Database..."
- General
  - Name = rules-engine
  - Owner = rules-engine-admin (or the user you created above)
- Save

# Creating Server
- Right-click Servers -> Register -> Server...
- General:
  - Name = rules-engine
- Connection:
  - Host name/address = localhost
  - LEAVE PORT AS DEFAULT!!!
  - Maintenance database = postgres
  - Username = rules-engine-admin
  - Password = same password as before
  - Check "Save password?"
- Save

# Setting Up PostgreSQL Tables in pgAdmin
- Log into the new server you created (with the password you used for creating your user)
- Expand the new server once signed in
- Right-click the rules-engine database from the new server -> Query Tool
- Paste in code from [sql/create.ddl](https://github.com/Access-Point-Program/cohort-3-rules-engine-admin/blob/2fa165b962dda1049bb8ee32e7312352060c6410/sql/create.ddl) file
- Click play button  (Execute/Refresh)
- Right-click database -> Refresh
- Click to open the rules-engine database you created inside the new server
- Click to open the "Schema" tab
- Under the "Tables" tab, verify that ruleset, rule, and condition tables exist.
- Under the "Types" tab, verify that eventtype, facttype and valuetype exist.
- Under the "Sequences" tab, verify that "condition_id_seq", "condition_rule_id_seq", "rule_id_seq", "rule_ruleset_id_seq", and "ruleset_id_seq" exist.

# Running the entire application
1. Clone down the repository
2. Run sql code to create the required tables 
3. Open the project and __navigate into the `front-end` directory__, then run `npm install`
4. After it has finished installing, __navigate back to the root directory__, and run `mvn clean install`
[//]: # (5. Once the project has finished building, then you can run the executable jar file. &#40;Below&#41;)
[//]: # (&#40;Depending on terminal '\' might need to be changed to '/'&#41; )
[//]: # (`java -jar .\target\rules-engine-0.0.1-SNAPSHOT.jar`  or `java -jar ./target/rules-engine-0.0.1-SNAPSHOT.jar`)
5. Follow the Docker guide below
6.
   - Go to the following URl 'http://localhost:9004/create-ruleset' to create a rule.
   - Go to the following URl 'http://localhost:9004/update-ruleset/{id}' to edit a rule ("{id}" should be replaced with the id of the rule you want to edit).

## Docker

### Building a Docker Image

Run the following as a build configuration or a command in the terminal.

```bash
mvn spring-boot:build-image
```

### Running the Docker Image

1. Open a bash terminal and run the following command to start create and start the docker container from the image.

```bash
docker run \
    -p 9004:9004 \
    --rm \
    -d \
    --name=rules-engine-admin \
    --env rules_engine_username \
    --env rules_engine_password \
    cohort-3-rules-engine-admin:latest
```

> For the environment variables replace any reference of `localhost` with `host.docker.internal`. If you are using `localhost` anywhere within the application it will need to be a configuration option that can be changed when running in a docker container.
2. Validate that the docker container is running with the following command

```bash
docker ps
```

It should look something like this.

```bash
CONTAINER ID   IMAGE                                COMMAND              CREATED         STATUS         PORTS                    NAMES
78264899f2d0   cohort-3-rules-engine-admin:latest   "/cnb/process/web"   3 minutes ago   Up 3 minutes   0.0.0.0:9004->9004/tcp   rules-engine-admin
```

3. Check the logs to make sure the application is correctly running. You will need the container ID from the `docker ps` command to run this.

```bash
docker logs 78264899f2d0
```

### To Stop the Docker container

Run

```bash
docker ps
```

Grab the container ID.

```bash
CONTAINER ID   IMAGE                                COMMAND              CREATED         STATUS         PORTS                    NAMES
78264899f2d0   cohort-3-rules-engine-admin:latest   "/cnb/process/web"   3 minutes ago   Up 3 minutes   0.0.0.0:9004->9004/tcp   rules-engine-admin
```

Use the container ID with the stop command.

```bash
docker stop 78264899f2d0
```

# Accessing Postman Endpoints
- Download file in repository named "[Ruleset Endpoints.postman_collection.json](https://github.com/Access-Point-Program/cohort-3-rules-engine-admin/blob/7ae6a172a0188bb6b8e5b7e1a70f28e58111cfb7/Ruleset%20Endpoints.postman_collection.json)" (If you have the repository cloned you should have it downloaded already)
- In Postman, click on "Collections" on sidebar
- Click on "Import" (top right of collections popup)
- Select the file you just downloaded
- Select "Open"