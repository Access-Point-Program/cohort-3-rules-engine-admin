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
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class DeleteTests {

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

        // Given the ruleset, when delete request, then response body is as expected
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

        // Given the ruleset, when delete request, then response body is as expected
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