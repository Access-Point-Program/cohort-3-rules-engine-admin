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
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static com.accesspoint.rulesengine.entity.EventType.FORWARD;
import static com.accesspoint.rulesengine.entity.FactType.*;
import static com.accesspoint.rulesengine.entity.ValueType.EMPTY;
import static com.accesspoint.rulesengine.entity.ValueType.END;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class PostTests {

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
    public void givenRuleset_whenPostNameDNE_thenCustomErrorIsCalled() {
        // Building out the mock ruleset
        Ruleset ruleset =
                Ruleset.builder()
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
                .then().log().all()
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
    public void givenRuleset_whenPostRulesDNE_thenCustomErrorIsCalled() {
        // Building out the mock ruleset
        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test")
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
                .then().log().all()
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
    public void givenRuleset_whenPostEndpointPriorityDNE_thenCustomErrorIsCalled() {
        // Building out the mock ruleset
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

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.save(Mockito.any(Ruleset.class))).thenReturn(ruleset);
        when(rulesetRepository.getReferenceById(Mockito.any(Long.class))).thenReturn(ruleset);
        when(ruleRepository.save(Mockito.eq(rule))).thenReturn(rule);

        // Given the ruleset, when post request, then response body is as expected
        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
                .when()
                .post("/ruleset")
                .then().log().all()
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
    public void givenRuleset_whenPOSTRulesEventTypeDNE_thenCustomErrorIsCalled() {
        // Building out the mock ruleset
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
                        .conditions(List.of(condition))
                        .build();

        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.save(Mockito.any(Ruleset.class))).thenReturn(ruleset);
        when(rulesetRepository.getReferenceById(Mockito.any(Long.class))).thenReturn(ruleset);
        when(ruleRepository.save(Mockito.eq(rule))).thenReturn(rule);

        // Given the ruleset, when post request, then response body is as expected
        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
                .when()
                .post("/ruleset")
                .then().log().all()
                .assertThat()
                .statusCode(400)
                .body(equalTo("Rule event type cannot be null"));
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
    public void givenRuleset_whenPostEndpointConditionsDNE_thenCustomErrorIsCalled() {
        // Building out the mock ruleset
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

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.save(Mockito.any(Ruleset.class))).thenReturn(ruleset);
        when(rulesetRepository.getReferenceById(Mockito.any(Long.class))).thenReturn(ruleset);
        when(ruleRepository.save(Mockito.eq(rule))).thenReturn(rule);

        // Given the ruleset, when post request, then response body is as expected
        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
                .when()
                .post("/ruleset")
                .then().log().all()
                .assertThat()
                .statusCode(400)
                .body(equalTo("Conditions cannot be empty"));
    }

    @Test
    public void givenRuleset_whenPostEndpointValueTypeDNE_thenCustomErrorIsCalled() {
        // Building out the mock ruleset
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

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.save(Mockito.any(Ruleset.class))).thenReturn(ruleset);
        when(rulesetRepository.getReferenceById(Mockito.any(Long.class))).thenReturn(ruleset);
        when(ruleRepository.save(Mockito.eq(rule))).thenReturn(rule);
        when(conditionRepository.save(Mockito.eq(condition))).thenReturn(condition);

        // Given the ruleset, when post request, then response body is as expected
        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
                .when()
                .post("/ruleset")
                .then().log().all()
                .assertThat()
                .statusCode(400)
                .body(equalTo("Condition value type cannot be null"));
    }

    @Test
    public void givenRuleset_whenPostEndpointFactTypeDNE_thenCustomErrorIsCalled() {
        // Building out the mock ruleset
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

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.save(Mockito.any(Ruleset.class))).thenReturn(ruleset);
        when(rulesetRepository.getReferenceById(Mockito.any(Long.class))).thenReturn(ruleset);
        when(ruleRepository.save(Mockito.eq(rule))).thenReturn(rule);
        when(conditionRepository.save(Mockito.eq(condition))).thenReturn(condition);

        // Given the ruleset, when post request, then response body is as expected
        given()
                .contentType(ContentType.JSON)
                .body(ruleset)
                .when()
                .post("/ruleset")
                .then().log().all()
                .assertThat()
                .statusCode(400)
                .body(equalTo("Condition fact type cannot be null"));
    }
}