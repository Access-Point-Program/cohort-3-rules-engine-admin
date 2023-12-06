package com.accesspoint.rulesengine.apitests.ruleset;

import com.accesspoint.rulesengine.entity.*;
import com.accesspoint.rulesengine.repository.RulesetRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;
import java.util.TimeZone;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetAllExtendedTests {
    @LocalServerPort
    private Integer port;

    @MockBean
    private RulesetRepository rulesetRepository;

    @Before
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = this.port;
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void givenRuleset_whenGetAllExtended_thenResponseBodyIsContentTypeJSON(){
        given()
                .when().get("/ruleset-extended")
                .then().log().headers()
                .header("Content-Type", "application/json");
    }

    @Test
    public void givenRuleset_whenGetAllExtended_thenResponseStatusCodeIs200() {
        given()
                .when().get("/ruleset-extended")
                .then().log().all().statusCode(200);
    }

    @Test
    public void givenRuleset_whenGetAllExtended_thenReturnCorrectContent() {
        Condition condition =
                Condition.builder()
                        .id(10000L)
                        .fact_type(FactType.RIGHT)
                        .value_type(ValueType.EMPTY)
                        .build();

        Condition condition2 =
                Condition.builder()
                        .id(10001L)
                        .fact_type(FactType.LEFT)
                        .value_type(ValueType.EMPTY)
                        .build();

        Rule rule =
                Rule.builder()
                        .id(1000L)
                        .priority(5)
                        .event_type(EventType.RIGHT)
                        .conditions(List.of(condition, condition2))
                        .build();

        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test")
                        .id(100L)
                        .rules(List.of(rule))
                        .creation_date(Timestamp.valueOf("2000-01-01 01:15:30.500"))
                        .build();

        Condition condition3 =
                Condition.builder()
                        .id(20000L)
                        .fact_type(FactType.FRONT)
                        .value_type(ValueType.EMPTY)
                        .build();

        Rule rule2 =
                Rule.builder()
                        .id(2000L)
                        .priority(5)
                        .event_type(EventType.RIGHT)
                        .conditions(List.of(condition3))
                        .build();

        Condition condition4 =
                Condition.builder()
                        .id(30000L)
                        .fact_type(FactType.FRONT)
                        .value_type(ValueType.END)
                        .build();

        Rule rule3 =
                Rule.builder()
                        .id(2001L)
                        .priority(6)
                        .event_type(EventType.LEFT)
                        .conditions(List.of(condition4))
                        .build();

        Ruleset ruleset2 =
                Ruleset.builder()
                        .name("Test2")
                        .id(200L)
                        .rules(List.of(rule2, rule3))
                        .creation_date(Timestamp.valueOf("2020-01-01 01:15:30.500"))
                        .build();

        // Mock repository methods that get called in the service
        when(rulesetRepository.findAll()).thenReturn(List.of(ruleset, ruleset2));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/ruleset-extended")
                .then().log().all()
                .assertThat()
                .body("[0].id", equalTo(100))
                .body("[0].name", equalTo("Test"))
                .body("[0].creation_date", equalTo("2000-01-01T01:15:30.500+00:00"))
                .body("[0].rules[0].id", equalTo((1000)))
                .body("[0].rules[0].priority", equalTo((5F)))
                .body("[0].rules[0].event_type", equalTo("RIGHT"))
                .body("[0].rules[0].conditions[0].id", equalTo((10000)))
                .body("[0].rules[0].conditions[0].fact_type", equalTo(("RIGHT")))
                .body("[0].rules[0].conditions[0].value_type", equalTo(("EMPTY")))
                .body("[0].rules[0].conditions[1].id", equalTo((10001)))
                .body("[0].rules[0].conditions[1].fact_type", equalTo(("LEFT")))
                .body("[0].rules[0].conditions[1].value_type", equalTo(("EMPTY")))
                .body("[1].id", equalTo(200))
                .body("[1].name", equalTo("Test2"))
                .body("[1].creation_date", equalTo("2020-01-01T01:15:30.500+00:00"))
                .body("[1].rules[0].id", equalTo((2000)))
                .body("[1].rules[0].priority", equalTo((5F)))
                .body("[1].rules[0].event_type", equalTo("RIGHT"))
                .body("[1].rules[0].conditions[0].id", equalTo((20000)))
                .body("[1].rules[0].conditions[0].fact_type", equalTo(("FRONT")))
                .body("[1].rules[0].conditions[0].value_type", equalTo(("EMPTY")))
                .body("[1].rules[1].id", equalTo((2001)))
                .body("[1].rules[1].priority", equalTo((6F)))
                .body("[1].rules[1].event_type", equalTo("LEFT"))
                .body("[1].rules[1].conditions[0].id", equalTo((30000)))
                .body("[1].rules[1].conditions[0].fact_type", equalTo(("FRONT")))
                .body("[1].rules[1].conditions[0].value_type", equalTo(("END")));
    }
}