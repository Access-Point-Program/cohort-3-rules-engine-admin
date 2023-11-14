package com.accesspoint.rulesengine.apitests.ruleset;

import com.accesspoint.rulesengine.controller.CreateRuleSetRequest;
import com.accesspoint.rulesengine.entity.Condition;
import com.accesspoint.rulesengine.entity.Rule;
import com.accesspoint.rulesengine.entity.Ruleset;
import com.accesspoint.rulesengine.repository.ConditionRepository;
import com.accesspoint.rulesengine.repository.RuleRepository;
import com.accesspoint.rulesengine.repository.RulesetRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

    // TODO: tests to ensure the error handling is executed how it should be from the service
}