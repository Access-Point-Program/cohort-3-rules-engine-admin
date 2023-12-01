package com.accesspoint.rulesengine.apitests.ruleset;

import com.accesspoint.rulesengine.entity.Condition;
import com.accesspoint.rulesengine.entity.EventType;
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
import org.springframework.test.context.junit4.SpringRunner;
import java.sql.Timestamp;
import java.util.*;
import static com.accesspoint.rulesengine.entity.EventType.FORWARD;
import static com.accesspoint.rulesengine.entity.FactType.*;
import static com.accesspoint.rulesengine.entity.ValueType.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
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
        .then().log().all()
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
        Rule fakeRule1 = new Rule(100L, 1, FORWARD, null, fakeCondition1);
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

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.save(Mockito.any(Ruleset.class))).thenReturn(ruleset);

        // Given the ruleset, when post request, then response body is as expected
        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
        .when()
                .post("/ruleset")
        .then()
                .assertThat()
                .statusCode(409)
                .body(containsString("Rule priority already exists in ruleset."))
                .body(containsString("Rule in question: Rule(id=200, priority=1.0, event_type=FORWARD, conditions=[Condition(id=200, fact_type=FRONT, value_type=EMPTY), Condition(id=201, fact_type=LEFT, value_type=EMPTY)])"));
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
    @Test
    public void givenRuleset_whenGetById_thenReturnCorrectContent() {
        // Building out the mock ruleset
        Condition fakeCon3_1 = new Condition(1000L, FRONT, END, null);
        Condition fakeCon4_1 = new Condition(2000L, FRONT, EMPTY, null);
        Condition fakeCon4_2 = new Condition(2010L, LEFT, EMPTY, null);
        List<Condition> fakeCondition3 = new ArrayList<>();
        fakeCondition3.add(fakeCon3_1);
        List<Condition> fakeCondition4 = new ArrayList<>();
        fakeCondition4.add(fakeCon4_1);
        fakeCondition4.add(fakeCon4_2);
        Rule fakeRule3 = new Rule(1000L, 110, FORWARD, null, fakeCondition3);
        Rule fakeRule4 = new Rule(2000L, 210, FORWARD, null, fakeCondition4);
        List<Rule> fakeRules2 = new ArrayList<>();
        fakeRules2.add(fakeRule3);
        fakeRules2.add(fakeRule4);
        Ruleset ruleset2 =
                Ruleset.builder()
                        .name("Test2")
                        .rules(fakeRules2)
                        .id(101L)
                        .creation_date(Timestamp.valueOf("2200-01-01 01:15:30.500"))
                        .build();

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.findById(Mockito.eq(101L))).thenReturn(Optional.ofNullable(ruleset2));

        given()
                .contentType(ContentType.JSON)
                .body(ruleset2)
        .when()
                .get("/ruleset/101")
        .then()
                .statusCode(200)
                .body("id", equalTo(101))
                .body("name", equalTo("Test2"))
                .body("creation_date", equalTo("2200-01-01T01:15:30.500+00:00"))
                // Rule at index 0
                .body("rules[0].id", equalTo((1000)))
                .body("rules[0].priority", equalTo((110.0F)))
                // Rule at index 0, condition at index 0
                .body("rules[0].conditions[0].id", equalTo((1000)))
                .body("rules[0].conditions[0].fact_type", equalTo(("FRONT")))
                .body("rules[0].conditions[0].value_type", equalTo(("END")))
                .body("rules[1].id", equalTo((2000)))
                .body("rules[1].priority", equalTo((210.0F)))
                // Rule at index 1, condition at index 0
                .body("rules[1].conditions[0].id", equalTo((2000)))
                .body("rules[1].conditions[0].fact_type", equalTo(("FRONT")))
                .body("rules[1].conditions[0].value_type", equalTo(("EMPTY")))
                // Rule at index 0, condition at index 1
                .body("rules[1].conditions[1].id", equalTo((2010)))
                .body("rules[1].conditions[1].fact_type", equalTo(("LEFT")))
                .body("rules[1].conditions[1].value_type", equalTo(("EMPTY")));
    }
    @Test
    public void givenRuleset_whenGetByIdDoesntExist_thenCustomErrorIsCalled() {
        // Building out the mock ruleset
        Condition fakeCon3_1 = new Condition(1000L, FRONT, END, null);
        Condition fakeCon4_1 = new Condition(2000L, FRONT, EMPTY, null);
        Condition fakeCon4_2 = new Condition(2010L, LEFT, EMPTY, null);
        List<Condition> fakeCondition3 = new ArrayList<>();
        fakeCondition3.add(fakeCon3_1);
        List<Condition> fakeCondition4 = new ArrayList<>();
        fakeCondition4.add(fakeCon4_1);
        fakeCondition4.add(fakeCon4_2);
        Rule fakeRule3 = new Rule(1000L, 110, FORWARD, null, fakeCondition3);
        Rule fakeRule4 = new Rule(2000L, 210, FORWARD, null, fakeCondition4);
        List<Rule> fakeRules2 = new ArrayList<>();
        fakeRules2.add(fakeRule3);
        fakeRules2.add(fakeRule4);
        Ruleset ruleset2 =
                Ruleset.builder()
                        .name("Test2")
                        .rules(fakeRules2)
                        .id(101L)
                        .creation_date(Timestamp.valueOf("2200-01-01 01:15:30.500"))
                        .build();

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.findById(Mockito.eq(101L))).thenReturn(Optional.ofNullable(ruleset2));

        given()
                .contentType(ContentType.JSON)
                .body(ruleset2)
        .when()
                .get("/ruleset/100")
        .then()
                .assertThat()
                .statusCode(400)
                .body(equalTo("Id not found"));
    }
    @Test
    public void givenIncomingRuleset_whenPutEndpointIsCalledWithNoChanges_thenNothingChanges() {
        Condition condition =
                Condition.builder()
                        .id(10000L)
                        .fact_type(RIGHT)
                        .value_type(END)
                        .build();

        Rule rule =
                Rule.builder()
                        .id(1000L)
                        .priority(5)
                        .event_type(EventType.RIGHT)
                        .conditions(List.of(condition))
                        .build();

        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset));
        when(rulesetRepository.save(Mockito.eq(ruleset))).thenReturn(ruleset);

        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
        .when()
                .put("/ruleset/100")
        .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(100))
                .body("name", equalTo("Test"))
                .body("creation_date", equalTo("2000-01-01T01:15:30.500+00:00"))
                .body("rules[0].id", equalTo((1000)))
                .body("rules[0].priority", equalTo((5F)))
                .body("rules[0].conditions[0].id", equalTo((10000)))
                .body("rules[0].conditions[0].fact_type", equalTo(("RIGHT")))
                .body("rules[0].conditions[0].value_type", equalTo(("END")));
    }
    @Test
    public void givenIncomingRuleset_whenPutEndpointIsCalledWithNameChange_thenOnlyNameIsChanged() {
        Condition condition =
                Condition.builder()
                        .id(10000L)
                        .fact_type(RIGHT)
                        .value_type(END)
                        .build();

        Rule rule =
                Rule.builder()
                        .id(1000L)
                        .priority(5)
                        .event_type(EventType.RIGHT)
                        .conditions(List.of(condition))
                        .build();

        Ruleset ruleset1 =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();


        Ruleset ruleset2 =
                Ruleset.builder()
                        .name("Test Changed")
                        .id(100L)
                        .rules(List.of(rule))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset1));
        when(rulesetRepository.save(Mockito.eq(ruleset1))).thenReturn(ruleset1);

        given()
                .contentType(ContentType.JSON)
                .body(ruleset2)
        .when()
                .put("/ruleset/100")
        .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(100))
                .body("name", equalTo("Test Changed"))
                .body("creation_date", equalTo("2000-01-01T01:15:30.500+00:00"))
                .body("rules[0].id", equalTo((1000)))
                .body("rules[0].priority", equalTo((5F)))
                .body("rules[0].event_type", equalTo("RIGHT"))
                .body("rules[0].conditions[0].id", equalTo((10000)))
                .body("rules[0].conditions[0].fact_type", equalTo(("RIGHT")))
                .body("rules[0].conditions[0].value_type", equalTo(("END")));
    }
    @Test
    public void givenIncomingRuleset_whenPutEndpointIsCalledWithRulePriorityChanged_thenOnlyPriorityIsChanged() {
        Condition condition =
                Condition.builder()
                        .id(10000L)
                        .fact_type(RIGHT)
                        .value_type(END)
                        .build();

        Rule rule =
                Rule.builder()
                        .id(1000L)
                        .priority(5)
                        .event_type(EventType.RIGHT)
                        .conditions(List.of(condition))
                        .build();

        Ruleset ruleset1 =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        Rule rule2 =
                Rule.builder()
                        .id(1000L)
                        .priority(6)
                        .event_type(EventType.RIGHT)
                        .conditions(List.of(condition))
                        .build();

        Ruleset ruleset2 =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule2))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset1));
        when(ruleRepository.findById(Mockito.eq(1000L))).thenReturn(Optional.of(rule));
        when(rulesetRepository.save(Mockito.eq(ruleset1))).thenReturn(ruleset1);

        given()
                .contentType(ContentType.JSON)
                .body(ruleset2)
        .when()
                .put("/ruleset/100")
        .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(100))
                .body("name", equalTo("Test"))
                .body("creation_date", equalTo("2000-01-01T01:15:30.500+00:00"))
                .body("rules[0].id", equalTo((1000)))
                .body("rules[0].priority", equalTo((6F)))
                .body("rules[0].event_type", equalTo("RIGHT"))
                .body("rules[0].conditions[0].id", equalTo((10000)))
                .body("rules[0].conditions[0].fact_type", equalTo(("RIGHT")))
                .body("rules[0].conditions[0].value_type", equalTo(("END")));
    }
    @Test
    public void givenIncomingRuleset_whenPutEndpointIsCalledWithRuleEventTypeChanged_thenOnlyEventTypeIsChanged() {
        Condition condition =
                Condition.builder()
                        .id(10000L)
                        .fact_type(RIGHT)
                        .value_type(END)
                        .build();

        Rule rule =
                Rule.builder()
                        .id(1000L)
                        .priority(5)
                        .event_type(EventType.RIGHT)
                        .conditions(List.of(condition))
                        .build();

        Ruleset ruleset1 =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        Rule rule2 =
                Rule.builder()
                        .id(1000L)
                        .priority(5)
                        .event_type(EventType.LEFT)
                        .conditions(List.of(condition))
                        .build();

        Ruleset ruleset2 =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule2))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset1));
        when(ruleRepository.findById(Mockito.eq(1000L))).thenReturn(Optional.of(rule));
        when(rulesetRepository.save(Mockito.eq(ruleset1))).thenReturn(ruleset1);

        given()
                .contentType(ContentType.JSON)
                .body(ruleset2)
        .when()
                .put("/ruleset/100")
        .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(100))
                .body("name", equalTo("Test"))
                .body("creation_date", equalTo("2000-01-01T01:15:30.500+00:00"))
                .body("rules[0].id", equalTo((1000)))
                .body("rules[0].priority", equalTo((5F)))
                .body("rules[0].event_type", equalTo("LEFT"))
                .body("rules[0].conditions[0].id", equalTo((10000)))
                .body("rules[0].conditions[0].fact_type", equalTo(("RIGHT")))
                .body("rules[0].conditions[0].value_type", equalTo(("END")));
    }
    @Test
    public void givenIncomingRuleset_whenPutEndpointIsCalledWithConditionFactTypeChanged_thenOnlyFactTypeIsChanged() {
        Condition condition =
                Condition.builder()
                        .id(10000L)
                        .fact_type(RIGHT)
                        .value_type(END)
                        .build();

        Rule rule =
                Rule.builder()
                        .id(1000L)
                        .priority(5)
                        .event_type(EventType.RIGHT)
                        .conditions(List.of(condition))
                        .build();

        Ruleset ruleset1 =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        Condition condition2 =
                Condition.builder()
                        .id(10000L)
                        .fact_type(LEFT)
                        .value_type(END)
                        .build();
        Rule rule2 =
                Rule.builder()
                        .id(1000L)
                        .priority(5)
                        .event_type(EventType.RIGHT)
                        .conditions(List.of(condition2))
                        .build();

        Ruleset ruleset2 =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule2))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset1));
        when(ruleRepository.findById(Mockito.eq(1000L))).thenReturn(Optional.of(rule));
        when(conditionRepository.findById(Mockito.eq(10000L))).thenReturn(Optional.of(condition));
        when(rulesetRepository.save(Mockito.eq(ruleset1))).thenReturn(ruleset1);

        given()
                .contentType(ContentType.JSON)
                .body(ruleset2)
        .when()
                .put("/ruleset/100")
        .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(100))
                .body("name", equalTo("Test"))
                .body("creation_date", equalTo("2000-01-01T01:15:30.500+00:00"))
                .body("rules[0].id", equalTo((1000)))
                .body("rules[0].priority", equalTo((5F)))
                .body("rules[0].event_type", equalTo("RIGHT"))
                .body("rules[0].conditions[0].id", equalTo((10000)))
                .body("rules[0].conditions[0].fact_type", equalTo(("LEFT")))
                .body("rules[0].conditions[0].value_type", equalTo(("END")));
    }
    @Test
    public void givenIncomingRuleset_whenPutEndpointIsCalledWithConditionValueTypeChanged_thenOnlyValueTypeIsChanged() {
        Condition condition =
                Condition.builder()
                        .id(10000L)
                        .fact_type(RIGHT)
                        .value_type(END)
                        .build();

        Rule rule =
                Rule.builder()
                        .id(1000L)
                        .priority(5)
                        .event_type(EventType.RIGHT)
                        .conditions(List.of(condition))
                        .build();

        Ruleset ruleset1 =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        Condition condition2 =
                Condition.builder()
                        .id(10000L)
                        .fact_type(RIGHT)
                        .value_type(EMPTY)
                        .build();
        Rule rule2 =
                Rule.builder()
                        .id(1000L)
                        .priority(5)
                        .event_type(EventType.RIGHT)
                        .conditions(List.of(condition2))
                        .build();

        Ruleset ruleset2 =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule2))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset1));
        when(ruleRepository.findById(Mockito.eq(1000L))).thenReturn(Optional.of(rule));
        when(conditionRepository.findById(Mockito.eq(10000L))).thenReturn(Optional.of(condition));
        when(rulesetRepository.save(Mockito.eq(ruleset1))).thenReturn(ruleset1);


        given()
                .contentType(ContentType.JSON)
                .body(ruleset2)
        .when()
                .put("/ruleset/100")
        .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(100))
                .body("name", equalTo("Test"))
                .body("creation_date", equalTo("2000-01-01T01:15:30.500+00:00"))
                .body("rules[0].id", equalTo((1000)))
                .body("rules[0].priority", equalTo((5F)))
                .body("rules[0].event_type", equalTo("RIGHT"))
                .body("rules[0].conditions[0].id", equalTo((10000)))
                .body("rules[0].conditions[0].fact_type", equalTo(("RIGHT")))
                .body("rules[0].conditions[0].value_type", equalTo(("EMPTY")));
    }
    @Test
    public void givenIncomingRuleset_whenPutEndpointIsCalledWithRuleAdded_thenRuleAdded() {
        Condition condition =
                Condition.builder()
                        .id(10000L)
                        .fact_type(RIGHT)
                        .value_type(END)
                        .build();

        Rule rule =
                Rule.builder()
                        .id(1000L)
                        .priority(5)
                        .event_type(EventType.RIGHT)
                        .conditions(List.of(condition))
                        .build();

        Ruleset ruleset1 =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(new ArrayList<>(List.of(rule)))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        Condition condition2 =
                Condition.builder()
                        .fact_type(RIGHT)
                        .value_type(EMPTY)
                        .build();


        Rule rule2 =
                Rule.builder()
                        .priority(6)
                        .event_type(EventType.LEFT)
                        .conditions(List.of(condition2))
                        .build();

        Ruleset ruleset2 =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule, rule2))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        Condition condition3 =
                Condition.builder()
                        .id(10001L)
                        .fact_type(RIGHT)
                        .value_type(EMPTY)
                        .build();

        Rule rule3 =
                Rule.builder()
                        .id(1001L)
                        .priority(6)
                        .event_type(EventType.LEFT)
                        .conditions(List.of(condition3))
                        .build();

        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset1));
        when(ruleRepository.findById(Mockito.eq(1000L))).thenReturn(Optional.of(rule));
        when(ruleRepository.save(Mockito.eq(rule2))).thenReturn(rule3);
        when(rulesetRepository.save(Mockito.eq(ruleset1))).thenReturn(ruleset1);

        given()
                .contentType(ContentType.JSON)
                .body(ruleset2)
        .when()
                .put("/ruleset/100")
        .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(100))
                .body("name", equalTo("Test"))
                .body("creation_date", equalTo("2000-01-01T01:15:30.500+00:00"))
                .body("rules[0].id", equalTo((1000)))
                .body("rules[0].priority", equalTo((5F)))
                .body("rules[0].event_type", equalTo("RIGHT"))
                .body("rules[0].conditions[0].id", equalTo((10000)))
                .body("rules[0].conditions[0].fact_type", equalTo(("RIGHT")))
                .body("rules[0].conditions[0].value_type", equalTo(("END")))
                .body("rules[1].id", equalTo((1001)))
                .body("rules[1].priority", equalTo((6F)))
                .body("rules[1].event_type", equalTo("LEFT"))
                .body("rules[1].conditions[0].id", equalTo((10001)))
                .body("rules[1].conditions[0].fact_type", equalTo(("RIGHT")))
                .body("rules[1].conditions[0].value_type", equalTo(("EMPTY")));
    }
    @Test
    public void givenIncomingRuleset_whenPutEndpointIsCalledWithRuleRemoved_thenRuleRemoved() {
        Condition condition =
                Condition.builder()
                        .id(10000L)
                        .fact_type(RIGHT)
                        .value_type(END)
                        .build();

        Rule rule =
                Rule.builder()
                        .id(1000L)
                        .priority(5)
                        .event_type(EventType.RIGHT)
                        .conditions(List.of(condition))
                        .build();

        Condition condition2 =
                Condition.builder()
                        .id(10001L)
                        .fact_type(RIGHT)
                        .value_type(EMPTY)
                        .build();

        Rule rule2 =
                Rule.builder()
                        .id(1001L)
                        .priority(6)
                        .event_type(EventType.LEFT)
                        .conditions(List.of(condition2))
                        .build();

        Ruleset ruleset1 =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(new ArrayList<>(List.of(rule, rule2)))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        Ruleset ruleset2 =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset1));
        when(ruleRepository.findById(Mockito.eq(1000L))).thenReturn(Optional.of(rule));
        when(ruleRepository.getReferenceById(Mockito.eq(1001L))).thenReturn(rule2);
        when(rulesetRepository.save(Mockito.eq(ruleset1))).thenReturn(ruleset1);

        given()
                .contentType(ContentType.JSON)
                .body(ruleset2)
        .when()
                .put("/ruleset/100")
        .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(100))
                .body("name", equalTo("Test"))
                .body("creation_date", equalTo("2000-01-01T01:15:30.500+00:00"))
                .body("rules[0].id", equalTo((1000)))
                .body("rules[0].priority", equalTo((5F)))
                .body("rules[0].event_type", equalTo("RIGHT"))
                .body("rules[0].conditions[0].id", equalTo((10000)))
                .body("rules[0].conditions[0].fact_type", equalTo(("RIGHT")))
                .body("rules[0].conditions[0].value_type", equalTo(("END")))
                .body("rules.size()", equalTo(1));
    }
    @Test
    public void givenIncomingRuleset_whenPutEndpointIsCalledWithConditionAdded_thenConditionAdded() {
        Condition condition =
                Condition.builder()
                        .id(10000L)
                        .fact_type(RIGHT)
                        .value_type(END)
                        .build();

        Rule rule =
                Rule.builder()
                        .id(1000L)
                        .priority(5)
                        .event_type(EventType.RIGHT)
                        .conditions(new ArrayList<>(List.of(condition)))
                        .build();

        Ruleset ruleset1 =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(new ArrayList<>(List.of(rule)))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        Condition condition2 =
                Condition.builder()
                        .fact_type(RIGHT)
                        .value_type(EMPTY)
                        .build();

        Rule rule2 =
                Rule.builder()
                        .id(1000L)
                        .priority(5)
                        .event_type(EventType.RIGHT)
                        .conditions(new ArrayList<>(List.of(condition, condition2)))
                        .build();

        Ruleset ruleset2 =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule2))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        Condition condition3 =
                Condition.builder()
                        .id(10001L)
                        .fact_type(RIGHT)
                        .value_type(EMPTY)
                        .build();

        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset1));
        when(ruleRepository.findById(Mockito.eq(1000L))).thenReturn(Optional.of(rule));
        when(conditionRepository.findById(Mockito.eq(10000L))).thenReturn(Optional.of(condition));
        when(conditionRepository.save(Mockito.eq(condition2))).thenReturn(condition3);
        when(rulesetRepository.save(Mockito.eq(ruleset1))).thenReturn(ruleset1);

        given()
                .contentType(ContentType.JSON)
                .body(ruleset2)
        .when()
                .put("/ruleset/100")
        .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(100))
                .body("name", equalTo("Test"))
                .body("creation_date", equalTo("2000-01-01T01:15:30.500+00:00"))
                .body("rules[0].id", equalTo((1000)))
                .body("rules[0].priority", equalTo((5F)))
                .body("rules[0].event_type", equalTo("RIGHT"))
                .body("rules[0].conditions[0].id", equalTo((10000)))
                .body("rules[0].conditions[0].fact_type", equalTo(("RIGHT")))
                .body("rules[0].conditions[0].value_type", equalTo(("END")))
                .body("rules[0].conditions[1].id", equalTo((10001)))
                .body("rules[0].conditions[1].fact_type", equalTo(("RIGHT")))
                .body("rules[0].conditions[1].value_type", equalTo(("EMPTY")));
    }
    @Test
    public void givenIncomingRuleset_whenPutEndpointIsCalledWithConditionRemoved_thenConditionRemoved() {
        Condition condition =
                Condition.builder()
                        .id(10000L)
                        .fact_type(RIGHT)
                        .value_type(END)
                        .build();

        Condition condition2 =
                Condition.builder()
                        .id(10001L)
                        .fact_type(RIGHT)
                        .value_type(EMPTY)
                        .build();

        Rule rule =
                Rule.builder()
                        .id(1000L)
                        .priority(5)
                        .event_type(EventType.RIGHT)
                        .conditions(new ArrayList<>(List.of(condition, condition2)))
                        .build();

        Ruleset ruleset1 =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(new ArrayList<>(List.of(rule)))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        Rule rule2 =
                Rule.builder()
                        .id(1000L)
                        .priority(5)
                        .event_type(EventType.RIGHT)
                        .conditions(new ArrayList<>(List.of(condition)))
                        .build();

        Ruleset ruleset2 =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule2))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset1));
        when(ruleRepository.findById(Mockito.eq(1000L))).thenReturn(Optional.of(rule));
        when(conditionRepository.findById(Mockito.eq(10000L))).thenReturn(Optional.of(condition));
        when(conditionRepository.getReferenceById(Mockito.eq(10001L))).thenReturn(condition2);
        when(rulesetRepository.save(Mockito.eq(ruleset1))).thenReturn(ruleset1);

        given()
                .contentType(ContentType.JSON)
                .body(ruleset2)
        .when()
                .put("/ruleset/100")
        .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(100))
                .body("name", equalTo("Test"))
                .body("creation_date", equalTo("2000-01-01T01:15:30.500+00:00"))
                .body("rules[0].id", equalTo((1000)))
                .body("rules[0].priority", equalTo((5F)))
                .body("rules[0].event_type", equalTo("RIGHT"))
                .body("rules[0].conditions[0].id", equalTo((10000)))
                .body("rules[0].conditions[0].fact_type", equalTo(("RIGHT")))
                .body("rules[0].conditions[0].value_type", equalTo(("END")))
                .body("rules[0].conditions.size()", equalTo(1));
    }
    @Test
    public void givenRuleset_whenPutEndpointNameIsBlank_thenCustomErrorIsCalled() {
        Ruleset ruleset =
                Ruleset.builder()
                        .name("")
                        .id(100L)
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
        .when()
                .put("/ruleset/100")
        .then()
                .assertThat()
                .statusCode(400)
                .body(equalTo("Name cannot be empty"));
    }
    @Test
    public void givenRuleset_whenPutEndpointNameDNE_thenCustomErrorIsCalled() {
        Ruleset ruleset =
                Ruleset.builder()
                        .id(100L)
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
        .when()
                .put("/ruleset/100")
        .then().log().all()
                .assertThat()
                .statusCode(400)
                .body(equalTo("Name cannot be empty"));
    }
    @Test
    public void givenRuleset_whenPutEndpointRulesIsBlank_thenCustomErrorIsCalled() {
        List<Rule> blankRules = Collections.emptyList();

        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(blankRules)
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
        .when()
                .put("/ruleset/100")
        .then().log().all()
                .assertThat()
                .statusCode(400)
                .body(equalTo("Rules cannot be empty"));
    }
    @Test
    public void givenRuleset_whenPutEndpointRulesDNE_thenCustomErrorIsCalled() {
        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
        .when()
                .put("/ruleset/100")
        .then().log().all()
                .assertThat()
                .statusCode(400)
                .body(equalTo("Rules cannot be empty"));
    }
    @Test
    public void givenRuleset_whenPutEndpointPriorityIs0_thenCustomErrorIsCalled() {
        Rule fakeRule = new Rule(1000L, 0.0, FORWARD, null, null);
        List<Rule> fakeRules = new ArrayList<>();
        fakeRules.add(fakeRule);

        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(fakeRules)
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
        .when()
                .put("/ruleset/100")
        .then().log().all()
                .assertThat()
                .statusCode(400)
                .body(equalTo("Rule priority cannot be 0"));
    }
    @Test
    public void givenRuleset_whenPutEndpointPriorityDNE_thenCustomErrorIsCalled() {
        Rule rule =
                Rule.builder()
                        .id(1000L)
                        .event_type((FORWARD))
                        .build();

        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
        .when()
                .put("/ruleset/100")
        .then().log().all()
                .assertThat()
                .statusCode(400)
                .body(equalTo("Rule priority cannot be 0"));
    }
    @Test
    public void givenRuleset_whenPutEndpointEventTypeDNE_thenCustomErrorIsCalled() {
        Rule rule =
                Rule.builder()
                        .id(1000L)
                        .priority(5)
                        .build();

        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
        .when()
                .put("/ruleset/100")
        .then().log().all()
                .assertThat()
                .statusCode(400)
                .body(equalTo("Rule event type cannot be null"));
    }
    @Test
    public void givenRuleset_whenPutEndpointConditionsIsBlank_thenCustomErrorIsCalled() {
        List<Condition> emptyConditions = Collections.emptyList();

        Rule rule =
                Rule.builder()
                        .id(1000L)
                        .priority(5)
                        .event_type(EventType.RIGHT)
                        .conditions(emptyConditions)
                        .build();

        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
        .when()
                .put("/ruleset/100")
        .then().log().all()
                .assertThat()
                .statusCode(400)
                .body(equalTo("Conditions cannot be empty"));
    }
    @Test
    public void givenRuleset_whenPutEndpointConditionsDNE_thenCustomErrorIsCalled() {
        Rule rule =
                Rule.builder()
                        .id(1000L)
                        .priority(5)
                        .event_type(EventType.RIGHT)
                        .build();

        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
        .when()
                .put("/ruleset/100")
        .then().log().all()
                .assertThat()
                .statusCode(400)
                .body(equalTo("Conditions cannot be empty"));
    }
    @Test
    public void givenRuleset_whenPutEndpointFactTypeDNE_thenCustomErrorIsCalled() {
        Condition condition =
                Condition.builder()
                        .build();

        Rule rule =
                Rule.builder()
                        .id(1000L)
                        .priority(5)
                        .event_type(EventType.RIGHT)
                        .conditions(List.of(condition))
                        .build();

        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
        .when()
                .put("/ruleset/100")
        .then().log().all()
                .assertThat()
                .statusCode(400)
                .body(equalTo("Condition fact type cannot be null"));
    }
    @Test
    public void givenRuleset_whenPutEndpointValueTypeDNE_thenCustomErrorIsCalled() {
        Condition condition =
                Condition.builder()
                        .fact_type(RIGHT)
                        .build();

        Rule rule =
                Rule.builder()
                        .id(1000L)
                        .priority(5)
                        .event_type(EventType.RIGHT)
                        .conditions(List.of(condition))
                        .build();

        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
        .when()
                .put("/ruleset/100")
        .then().log().all()
                .assertThat()
                .statusCode(400)
                .body(equalTo("Condition value type cannot be null"));
    }
}