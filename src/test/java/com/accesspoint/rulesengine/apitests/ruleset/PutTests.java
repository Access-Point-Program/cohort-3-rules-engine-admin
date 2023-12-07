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
import static com.accesspoint.rulesengine.entity.FactType.LEFT;
import static com.accesspoint.rulesengine.entity.FactType.RIGHT;
import static com.accesspoint.rulesengine.entity.ValueType.EMPTY;
import static com.accesspoint.rulesengine.entity.ValueType.END;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class PutTests {

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
    public void givenIncomingRuleset_whenPutEndpointIsCalledWithNoChanges_thenNothingChanges() {
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
        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset));
        when(rulesetRepository.save(Mockito.eq(ruleset))).thenReturn(ruleset);

        // Given the ruleset, when put request, then response body is as expected
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

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset1));
        when(rulesetRepository.save(Mockito.eq(ruleset1))).thenReturn(ruleset1);

        // Given the ruleset, when put request, then response body is as expected
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

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset1));
        when(ruleRepository.findById(Mockito.eq(1000L))).thenReturn(Optional.of(rule));
        when(rulesetRepository.save(Mockito.eq(ruleset1))).thenReturn(ruleset1);

        // Given the ruleset, when put request, then response body is as expected
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

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset1));
        when(ruleRepository.findById(Mockito.eq(1000L))).thenReturn(Optional.of(rule));
        when(rulesetRepository.save(Mockito.eq(ruleset1))).thenReturn(ruleset1);

        // Given the ruleset, when put request, then response body is as expected
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

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset1));
        when(ruleRepository.findById(Mockito.eq(1000L))).thenReturn(Optional.of(rule));
        when(conditionRepository.findById(Mockito.eq(10000L))).thenReturn(Optional.of(condition));
        when(rulesetRepository.save(Mockito.eq(ruleset1))).thenReturn(ruleset1);

        // Given the ruleset, when put request, then response body is as expected
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

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset1));
        when(ruleRepository.findById(Mockito.eq(1000L))).thenReturn(Optional.of(rule));
        when(conditionRepository.findById(Mockito.eq(10000L))).thenReturn(Optional.of(condition));
        when(rulesetRepository.save(Mockito.eq(ruleset1))).thenReturn(ruleset1);

        // Given the ruleset, when put request, then response body is as expected
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

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset1));
        when(ruleRepository.findById(Mockito.eq(1000L))).thenReturn(Optional.of(rule));
        when(ruleRepository.save(Mockito.eq(rule2))).thenReturn(rule3);
        when(rulesetRepository.save(Mockito.eq(ruleset1))).thenReturn(ruleset1);

        // Given the ruleset, when put request, then response body is as expected
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

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset1));
        when(ruleRepository.findById(Mockito.eq(1000L))).thenReturn(Optional.of(rule));
        when(ruleRepository.getReferenceById(Mockito.eq(1001L))).thenReturn(rule2);
        when(rulesetRepository.save(Mockito.eq(ruleset1))).thenReturn(ruleset1);

        // Given the ruleset, when put request, then response body is as expected
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

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset1));
        when(ruleRepository.findById(Mockito.eq(1000L))).thenReturn(Optional.of(rule));
        when(conditionRepository.findById(Mockito.eq(10000L))).thenReturn(Optional.of(condition));
        when(conditionRepository.save(Mockito.eq(condition2))).thenReturn(condition3);
        when(rulesetRepository.save(Mockito.eq(ruleset1))).thenReturn(ruleset1);

        // Given the ruleset, when put request, then response body is as expected
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
        // Building out the mock ruleset
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

        // Mock ALL repository methods that get called in the service
        when(rulesetRepository.findById(Mockito.eq(100L))).thenReturn(Optional.ofNullable(ruleset1));
        when(ruleRepository.findById(Mockito.eq(1000L))).thenReturn(Optional.of(rule));
        when(conditionRepository.findById(Mockito.eq(10000L))).thenReturn(Optional.of(condition));
        when(conditionRepository.getReferenceById(Mockito.eq(10001L))).thenReturn(condition2);
        when(rulesetRepository.save(Mockito.eq(ruleset1))).thenReturn(ruleset1);

        // Given the ruleset, when put request, then response body is as expected
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
        // Building out the mock ruleset
        Ruleset ruleset =
                Ruleset.builder()
                        .name("")
                        .id(100L)
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        // Given the ruleset, when put request, then response body is as expected
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
        // Building out the mock ruleset
        Ruleset ruleset =
                Ruleset.builder()
                        .id(100L)
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        // Given the ruleset, when put request, then response body is as expected
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
        // Building out the mock ruleset
        List<Rule> blankRules = Collections.emptyList();

        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(blankRules)
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        // Given the ruleset, when put request, then response body is as expected
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
        // Building out the mock ruleset
        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        // Given the ruleset, when put request, then response body is as expected
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
        // Building out the mock ruleset
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

        // Given the ruleset, when put request, then response body is as expected
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

        // Given the ruleset, when put request, then response body is as expected
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
        // Building out the mock ruleset
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

        // Given the ruleset, when put request, then response body is as expected
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
        // Building out the mock ruleset
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

        // Given the ruleset, when put request, then response body is as expected
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

        // Given the ruleset, when put request, then response body is as expected
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

        // Given the ruleset, when put request, then response body is as expected
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

        // Given the ruleset, when put request, then response body is as expected
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