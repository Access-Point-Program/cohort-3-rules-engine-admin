package com.accesspoint.rulesengine.apitests.ruleset;

import com.accesspoint.rulesengine.entity.Condition;
import com.accesspoint.rulesengine.entity.Rule;
import com.accesspoint.rulesengine.entity.Ruleset;
import com.accesspoint.rulesengine.repository.ConditionRepository;
import com.accesspoint.rulesengine.repository.RuleRepository;
import com.accesspoint.rulesengine.repository.RulesetRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import java.sql.Timestamp;
import java.util.*;

import static com.accesspoint.rulesengine.entity.EventType.FORWARD;
import static com.accesspoint.rulesengine.entity.FactType.*;
import static com.accesspoint.rulesengine.entity.ValueType.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class RulesetTest {

    @LocalServerPort
    private Integer port;

    @MockBean
    private RulesetRepository rulesetRepository;
    @MockBean
    private RuleRepository ruleRepository;
    @MockBean
    private ConditionRepository conditionRepository;

    @Before
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = this.port;
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void whenRulesetGETEndpointIsCalled_thenResponseStatusCodeIs200() {
        given()
        .when().get("/ruleset")
        .then().log().all().statusCode(200);
    }

    @Test
    public void whenRulesetGETEndpointCalled_thenResponseBodyContainsCorrectDataAndFormat() {

        Ruleset testRuleset =
                Ruleset.builder()
                        .name("test")
                        .id(100L)
                        .creation_date(Timestamp.valueOf("2020-01-01 03:12:50.581"))
                        .build();

        Ruleset testRuleset2 =
                Ruleset.builder()
                        .name("test2")
                        .id(101L)
                        .creation_date(Timestamp.valueOf("3020-01-01 03:12:50.581"))
                        .build();

        when(rulesetRepository.findAll()).thenReturn(List.of(testRuleset, testRuleset2));

        given()
        .when().get("/ruleset")
        .then().log().all()
                .body("[0]", hasEntry("id",100))
                .body("[0]", hasEntry("name","test"))
                .body("[0]", hasEntry("creation_date","2020-01-01T03:12:50.581+00:00"))
                .body("[1]", hasEntry("id",101))
                .body("[1]", hasEntry("name","test2"))
                .body("[1]", hasEntry("creation_date","3020-01-01T03:12:50.581+00:00"));
    }

    @Test
    public void whenRulesetGETEndpointCalled_thenResponseBodyIsContentTypeJSON(){
        given()
        .when().get("/ruleset")
        .then().log().headers()
                .header("Content-Type", "application/json");
    }

    @Test
    public void givenRuleset_whenRulesetPOSTEndpointCalled_thenResponseStatusCodeIs200() {

        // Building out the mock ruleset
        Condition fakeCon1_1 = new Condition(100L, FRONT, END, null);
        Condition fakeCon2_1 = new Condition(200L, FRONT, EMPTY, null);
        Condition fakeCon2_2 = new Condition(201L, LEFT, EMPTY, null);
        List<Condition> fakeCondition1 = new ArrayList<>();
        fakeCondition1.add(fakeCon1_1);
        List<Condition> fakeCondition2 = new ArrayList<>();
        fakeCondition2.add(fakeCon2_1);
        fakeCondition2.add(fakeCon2_2);
        Rule fakeRule1 = new Rule(100L, 11, FORWARD, null, fakeCondition1);
        Rule fakeRule2 = new Rule(200L, 21, FORWARD, null, fakeCondition2);
        List<Rule> fakeRules = new ArrayList<>();
        fakeRules.add(fakeRule1);
        fakeRules.add(fakeRule2);
        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test1")
                        .rules(fakeRules)
                        .id(100L)
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                .build();

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.save(Mockito.any(Ruleset.class))).thenReturn(ruleset);
        when(rulesetRepository.getReferenceById(Mockito.any(Long.class))).thenReturn(ruleset);
        when(ruleRepository.save(Mockito.eq(fakeRule1))).thenReturn(fakeRule1);
        when(conditionRepository.save(Mockito.eq(fakeCon1_1))).thenReturn(fakeCon1_1);

        // Given the ruleset, when post request, then response body is as expected
        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
        .when()
                .post("/ruleset")
                .then()
        .log().all()
                .statusCode(200);
    }

    @Test
    public void givenRuleset_whenRulesetPOSTEndpointCalled_thenResponseBodyIsContentTypeJSON() {

        // Building out the mock ruleset
        Condition fakeCon1_1 = new Condition(100L, FRONT, END, null);
        Condition fakeCon2_1 = new Condition(200L, FRONT, EMPTY, null);
        Condition fakeCon2_2 = new Condition(201L, LEFT, EMPTY, null);
        List<Condition> fakeCondition1 = new ArrayList<>();
        fakeCondition1.add(fakeCon1_1);
        List<Condition> fakeCondition2 = new ArrayList<>();
        fakeCondition2.add(fakeCon2_1);
        fakeCondition2.add(fakeCon2_2);
        Rule fakeRule1 = new Rule(100L, 11, FORWARD, null, fakeCondition1);
        Rule fakeRule2 = new Rule(200L, 21, FORWARD, null, fakeCondition2);
        List<Rule> fakeRules = new ArrayList<>();
        fakeRules.add(fakeRule1);
        fakeRules.add(fakeRule2);
        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test1")
                        .rules(fakeRules)
                        .id(100L)
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.save(Mockito.any(Ruleset.class))).thenReturn(ruleset);
        when(rulesetRepository.getReferenceById(Mockito.any(Long.class))).thenReturn(ruleset);
        when(ruleRepository.save(Mockito.eq(fakeRule1))).thenReturn(fakeRule1);
        when(conditionRepository.save(Mockito.eq(fakeCon1_1))).thenReturn(fakeCon1_1);

        // Given the ruleset, when post request, then response body is as expected
        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
        .when()
                .post("/ruleset")
                .then()
        .log().headers()
                .header("Content-Type", "application/json");
    }

    @Test
    public void givenRuleset_whenRulesetPOSTEndpointCalled_thenResponseBodyContainsCorrectData() {

        // Building out the mock ruleset
        Condition fakeCon1_1 = new Condition(100L, FRONT, END, null);
        Condition fakeCon2_1 = new Condition(200L, FRONT, EMPTY, null);
        Condition fakeCon2_2 = new Condition(201L, LEFT, EMPTY, null);
        List<Condition> fakeCondition1 = new ArrayList<>();
        fakeCondition1.add(fakeCon1_1);
        List<Condition> fakeCondition2 = new ArrayList<>();
        fakeCondition2.add(fakeCon2_1);
        fakeCondition2.add(fakeCon2_2);
        Rule fakeRule1 = new Rule(100L, 11, FORWARD, null, fakeCondition1);
        Rule fakeRule2 = new Rule(200L, 21, FORWARD, null, fakeCondition2);
        List<Rule> fakeRules = new ArrayList<>();
        fakeRules.add(fakeRule1);
        fakeRules.add(fakeRule2);
        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test1")
                        .rules(fakeRules)
                        .id(100L)
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.save(Mockito.any(Ruleset.class))).thenReturn(ruleset);
        when(rulesetRepository.getReferenceById(Mockito.any(Long.class))).thenReturn(ruleset);
        when(ruleRepository.save(Mockito.eq(fakeRule1))).thenReturn(fakeRule1);
        when(conditionRepository.save(Mockito.eq(fakeCon1_1))).thenReturn(fakeCon1_1);

        // Given the ruleset, when post request, then response body is as expected
        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
                .when()
                .post("/ruleset")
                .then()
                .log().all()
                .statusCode(200)
                // Ruleset
                .body("id", equalTo(100))
                .body("name", equalTo("Test1"))
                .body("creation_date", equalTo("2000-01-01T01:15:30.500+00:00"))
                // Rule at index 0
                .body("rules[0].id", equalTo((100)))
                .body("rules[0].priority", equalTo((11.0F)))
                // Rule at index 0, condition at index 0
                .body("rules[0].conditions[0].id", equalTo((100)))
                .body("rules[0].conditions[0].fact_type", equalTo(("FRONT")))
                .body("rules[0].conditions[0].value_type", equalTo(("END")))

                // Rule at index 1
                .body("rules[1].id", equalTo((200)))
                .body("rules[1].priority", equalTo((21.0F)))
                // Rule at index 1, condition at index 0
                .body("rules[1].conditions[0].id", equalTo((200)))
                .body("rules[1].conditions[0].fact_type", equalTo(("FRONT")))
                .body("rules[1].conditions[0].value_type", equalTo(("EMPTY")))
                // Rule at index 0, condition at index 1
                .body("rules[1].conditions[1].id", equalTo((201)))
                .body("rules[1].conditions[1].fact_type", equalTo(("LEFT")))
                .body("rules[1].conditions[1].value_type", equalTo(("EMPTY")));
    }

    @Test
    public void givenRuleset_whenPOSTNameIsEmpty_thenCustomErrorIsCalled() {
        // Building out the mock ruleset
        Condition fakeCon1_1 = new Condition(100L, FRONT, END, null);
        Condition fakeCon2_1 = new Condition(200L, FRONT, EMPTY, null);
        Condition fakeCon2_2 = new Condition(201L, LEFT, EMPTY, null);
        List<Condition> fakeCondition1 = new ArrayList<>();
        fakeCondition1.add(fakeCon1_1);
        List<Condition> fakeCondition2 = new ArrayList<>();
        fakeCondition2.add(fakeCon2_1);
        fakeCondition2.add(fakeCon2_2);
        Rule fakeRule1 = new Rule(100L, 11, FORWARD, null, fakeCondition1);
        Rule fakeRule2 = new Rule(200L, 21, FORWARD, null, fakeCondition2);
        List<Rule> fakeRules = new ArrayList<>();
        fakeRules.add(fakeRule1);
        fakeRules.add(fakeRule2);
        Ruleset ruleset =
                Ruleset.builder()
                        .name("")
                        .rules(fakeRules)
                        .id(100L)
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.save(Mockito.any(Ruleset.class))).thenReturn(ruleset);
        when(rulesetRepository.getReferenceById(Mockito.any(Long.class))).thenReturn(ruleset);
        when(ruleRepository.save(Mockito.eq(fakeRule1))).thenReturn(fakeRule1);
        when(conditionRepository.save(Mockito.eq(fakeCon1_1))).thenReturn(fakeCon1_1);

        // Given the ruleset, when post request, then response body is as expected
        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
                .when()
                .post("/ruleset")
                .then()
                .assertThat()
                .statusCode(400)
                .body(equalTo("Name cannot be empty"));
    }

    @Test
    public void givenRuleset_whenPOSTRulesIsEmpty_thenCustomErrorIsCalled() {
        // Building out the mock ruleset

        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test1")
                        .rules(null)
                        .id(100L)
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.save(Mockito.any(Ruleset.class))).thenReturn(ruleset);
        when(rulesetRepository.getReferenceById(Mockito.any(Long.class))).thenReturn(ruleset);
//        when(ruleRepository.save(Mockito.eq(fakeRule1))).thenReturn(fakeRule1);
//        when(conditionRepository.save(Mockito.eq(fakeCon1_1))).thenReturn(fakeCon1_1);

        // Given the ruleset, when post request, then response body is as expected
        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
                .when()
                .post("/ruleset")
                .then()
                .assertThat()
                .statusCode(400)
                .body(equalTo("Rules cannot be empty"));
    }

    @Test
    public void givenRuleset_whenPOSTRulesPriorityIsZero_thenCustomErrorIsCalled() {

        // Building out the mock ruleset
    Condition fakeCon1_1 = new Condition(100L, FRONT, END, null);
    Condition fakeCon2_1 = new Condition(200L, FRONT, EMPTY, null);
    Condition fakeCon2_2 = new Condition(201L, LEFT, EMPTY, null);
    List<Condition> fakeCondition1 = new ArrayList<>();
        fakeCondition1.add(fakeCon1_1);
    List<Condition> fakeCondition2 = new ArrayList<>();
        fakeCondition2.add(fakeCon2_1);
        fakeCondition2.add(fakeCon2_2);
    Rule fakeRule1 = new Rule(100L, 11, FORWARD, null, fakeCondition1);
    Rule fakeRule2 = new Rule(200L, 0, FORWARD, null, fakeCondition2);
    List<Rule> fakeRules = new ArrayList<>();
        fakeRules.add(fakeRule1);
        fakeRules.add(fakeRule2);
    Ruleset ruleset =
            Ruleset.builder()
                    .name("Test1")
                    .rules(fakeRules)
                    .id(100L)
                    .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                    .build();

    // Mock ALL repository methods that get called in the service
    when(rulesetRepository.save(Mockito.any(Ruleset.class))).thenReturn(ruleset);
    when(rulesetRepository.getReferenceById(Mockito.any(Long.class))).thenReturn(ruleset);
    when(ruleRepository.save(Mockito.eq(fakeRule1))).thenReturn(fakeRule1);
    when(conditionRepository.save(Mockito.eq(fakeCon1_1))).thenReturn(fakeCon1_1);

    // Given the ruleset, when post request, then response body is as expected
    given()
                .contentType(ContentType.JSON)
                .body(ruleset)
                .when()
                .post("/ruleset")
                .then()
                .assertThat()
                .statusCode(400)
                .body(equalTo("Rule priority cannot be 0"));
}

    //TODO: if priority already exists
    @Test
    public void givenRuleset_whenPOSTPriorityAlreadyExists_thenCustomErrorIsCalled() {

        // Building out the mock ruleset
        Condition fakeCon1_1 = new Condition(100L, FRONT, END, null);
        Condition fakeCon2_1 = new Condition(200L, FRONT, EMPTY, null);
        Condition fakeCon2_2 = new Condition(201L, LEFT, EMPTY, null);
        List<Condition> fakeCondition1 = new ArrayList<>();
        fakeCondition1.add(fakeCon1_1);
        List<Condition> fakeCondition2 = new ArrayList<>();
        fakeCondition2.add(fakeCon2_1);
        fakeCondition2.add(fakeCon2_2);
        Rule fakeRule1 = new Rule(100L, 11, FORWARD, null, fakeCondition1);
        Rule fakeRule2 = new Rule(200L, 1, FORWARD, null, fakeCondition2);
        List<Rule> fakeRules = new ArrayList<>();
        fakeRules.add(fakeRule1);
        fakeRules.add(fakeRule2);
        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test1")
                        .rules(fakeRules)
                        .id(100L)
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        Condition fakeCon3_1 = new Condition(300L, FRONT, END, null);
        Condition fakeCon4_1 = new Condition(4200L, FRONT, EMPTY, null);
        Condition fakeCon4_2 = new Condition(401L, LEFT, EMPTY, null);
        List<Condition> fakeCondition3 = new ArrayList<>();
        fakeCondition3.add(fakeCon3_1);
        List<Condition> fakeCondition4 = new ArrayList<>();
        fakeCondition4.add(fakeCon4_1);
        fakeCondition4.add(fakeCon4_2);
        Rule fakeRule3 = new Rule(300L, 11, FORWARD, null, fakeCondition3);
        Rule fakeRule4 = new Rule(400L, 2, FORWARD, null, fakeCondition4);
        List<Rule> fakeRules2 = new ArrayList<>();
        fakeRules2.add(fakeRule3);
        fakeRules2.add(fakeRule4);
        Ruleset ruleset2 =
                Ruleset.builder()
                        .name("Test2")
                        .rules(fakeRules2)
                        .id(101L)
                        .creation_date(Timestamp.valueOf("2020-01-01 01:15:30.500"))
                        .build();

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.save(Mockito.any(Ruleset.class))).thenReturn(ruleset);
        when(rulesetRepository.getReferenceById(Mockito.any(Long.class))).thenReturn(ruleset);
        when(ruleRepository.save(Mockito.eq(fakeRule3))).thenReturn(fakeRule3);
        when(conditionRepository.save(Mockito.eq(fakeCon3_1))).thenReturn(fakeCon3_1);
        when(ruleRepository.findAll()).thenReturn(fakeRules);

        // Given the ruleset, when post request, then response body is as expected
        given()
                .contentType(ContentType.JSON)
                .body(ruleset2)
                .when()
                .post("/ruleset")
                .then()
                .assertThat()
                .statusCode(409)
                .body(containsString("Rule priority already exists in database."))
                .body(containsString("Rule in question: Rule(id=300, priority=11.0, event_type=FORWARD, conditions=[Condition(id=300, fact_type=FRONT, value_type=END)])"));
    }

    @Test
    public void givenRuleset_whenPOSTRulesConditionsIsEmpty_thenCustomErrorIsCalled() {

        // Building out the mock ruleset
        Condition fakeCon1_1 = null;
        Condition fakeCon2_1 = new Condition(200L, FRONT, EMPTY, null);
        Condition fakeCon2_2 = new Condition(201L, LEFT, EMPTY, null);
        List<Condition> fakeCondition1 = new ArrayList<>();
        fakeCondition1.add(fakeCon1_1);
        List<Condition> fakeCondition2 = new ArrayList<>();
        fakeCondition2.add(fakeCon2_1);
        fakeCondition2.add(fakeCon2_2);
        Rule fakeRule1 = new Rule(100L, 11, FORWARD, null, null);
        Rule fakeRule2 = new Rule(200L, 0, FORWARD, null, fakeCondition2);
        List<Rule> fakeRules = new ArrayList<>();
        fakeRules.add(fakeRule1);
        fakeRules.add(fakeRule2);
        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test1")
                        .rules(fakeRules)
                        .id(100L)
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.save(Mockito.any(Ruleset.class))).thenReturn(ruleset);
        when(rulesetRepository.getReferenceById(Mockito.any(Long.class))).thenReturn(ruleset);
        when(ruleRepository.save(Mockito.eq(fakeRule1))).thenReturn(fakeRule1);
        when(conditionRepository.save(Mockito.eq(fakeCon1_1))).thenReturn(fakeCon1_1);

        // Given the ruleset, when post request, then response body is as expected
        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
                .when()
                .post("/ruleset")
                .then()
                .assertThat()
                .statusCode(400)
                .body(equalTo("Conditions cannot be empty"));
    }

    // TODO: deletes ruleset
    @Test
    public void givenRuleset_whenDELETE_thenCorrectDataIsPassed() {

        // Building out the mock ruleset
        Condition fakeCon1_1 = null;
        Condition fakeCon2_1 = new Condition(200L, FRONT, EMPTY, null);
        Condition fakeCon2_2 = new Condition(201L, LEFT, EMPTY, null);
        List<Condition> fakeCondition1 = new ArrayList<>();
        fakeCondition1.add(fakeCon1_1);
        List<Condition> fakeCondition2 = new ArrayList<>();
        fakeCondition2.add(fakeCon2_1);
        fakeCondition2.add(fakeCon2_2);
        Rule fakeRule1 = new Rule(100L, 11, FORWARD, null, null);
        Rule fakeRule2 = new Rule(200L, 0, FORWARD, null, fakeCondition2);
        List<Rule> fakeRules = new ArrayList<>();
        fakeRules.add(fakeRule1);
        fakeRules.add(fakeRule2);
        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test1")
                        .rules(fakeRules)
                        .id(100L)
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        // Mock ALL repository methods that get called in the service

        when(rulesetRepository.save(Mockito.any(Ruleset.class))).thenReturn(ruleset);
        when(rulesetRepository.getReferenceById(Mockito.any(Long.class))).thenReturn(ruleset);
        when(ruleRepository.save(Mockito.eq(fakeRule1))).thenReturn(fakeRule1);
        when(conditionRepository.save(Mockito.eq(fakeCon1_1))).thenReturn(fakeCon1_1);
        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset));

        // Given the ruleset, when post request, then response body is as expected
        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
                .when()
                .delete("/ruleset/100")
                .then()
                .assertThat()
                .statusCode(204);
    }
    // TODO: if id doesn't exist, throw error
    @Test
    public void givenRuleset_whenDELETERulesetwithInvalidID_thenCustomErrorIsThrown() {

        // Building out the mock ruleset
        Condition fakeCon1_1 = null;
        Condition fakeCon2_1 = new Condition(200L, FRONT, EMPTY, null);
        Condition fakeCon2_2 = new Condition(201L, LEFT, EMPTY, null);
        List<Condition> fakeCondition1 = new ArrayList<>();
        fakeCondition1.add(fakeCon1_1);
        List<Condition> fakeCondition2 = new ArrayList<>();
        fakeCondition2.add(fakeCon2_1);
        fakeCondition2.add(fakeCon2_2);
        Rule fakeRule1 = new Rule(100L, 11, FORWARD, null, null);
        Rule fakeRule2 = new Rule(200L, 0, FORWARD, null, fakeCondition2);
        List<Rule> fakeRules = new ArrayList<>();
        fakeRules.add(fakeRule1);
        fakeRules.add(fakeRule2);
        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test1")
                        .rules(fakeRules)
                        .id(100L)
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        // Mock ALL repository methods that get called in the service

        when(rulesetRepository.save(Mockito.any(Ruleset.class))).thenReturn(ruleset);
        when(rulesetRepository.getReferenceById(Mockito.any(Long.class))).thenReturn(ruleset);
        when(ruleRepository.save(Mockito.eq(fakeRule1))).thenReturn(fakeRule1);
        when(conditionRepository.save(Mockito.eq(fakeCon1_1))).thenReturn(fakeCon1_1);
        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset));

        // Given the ruleset, when post request, then response body is as expected
        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
                .when()
                .delete("/ruleset/101")
                .then()
                .assertThat()
                .statusCode(400)
                .body(equalTo("Id does not exist"));
    }
}