package com.accesspoint.rulesengine.apitests.ruleset;

import com.accesspoint.rulesengine.entity.Condition;
import com.accesspoint.rulesengine.entity.Rule;
import com.accesspoint.rulesengine.entity.Ruleset;
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
import java.util.Optional;
import java.util.TimeZone;
import static com.accesspoint.rulesengine.entity.EventType.FORWARD;
import static com.accesspoint.rulesengine.entity.FactType.FRONT;
import static com.accesspoint.rulesengine.entity.FactType.LEFT;
import static com.accesspoint.rulesengine.entity.ValueType.EMPTY;
import static com.accesspoint.rulesengine.entity.ValueType.END;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class GetByIdTests {

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
}