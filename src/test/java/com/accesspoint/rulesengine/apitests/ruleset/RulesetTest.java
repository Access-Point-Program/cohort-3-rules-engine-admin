package com.accesspoint.rulesengine.apitests.ruleset;

import com.accesspoint.rulesengine.controller.CreateRuleSetRequest;
import com.accesspoint.rulesengine.entity.Condition;
import com.accesspoint.rulesengine.entity.Rule;
import com.accesspoint.rulesengine.entity.Ruleset;
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
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

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
    public void PostRequest() throws JSONException {
//        JSONObject json = new JSONObject().put("name", "Test1").put("rules", " [ {\"priority\": \"111\", \"event_type\": \"BACK\", \"conditions\": [ { \"fact_type\":\"FRONT\",\"value_type\":\"END\"}]},{\"priority\": 111111,\"event_type\": \"FORWARD\",\"conditions\": [{ \"fact_type\":\"FRONT\",\"value_type\":\"EMPTY\"}]},{\"priority\": 111116, \"event_type\": \"LEFT\",\"conditions\": [{\"fact_type\":\"FRONT\",\"value_type\":\"WALL\"},{\"fact_type\":\"RIGHT\",\"value_type\":\"END\"}]}]");

        String json = {"name":"Test1","rules": [ {"priority": 11, "event_type": "FORWARD", "conditions": [{"fact_type":"FRONT","value_type":"END"}]},{"priority": 21, "event_type": "FORWARD","conditions": [{"fact_type":"FRONT","value_type":"EMPTY"}]},{"priority": 31, "event_type": "LEFT","conditions": [{"fact_type":"FRONT","value_type":"WALL"},{"fact_type":"RIGHT","value_type":"END"}]}]}



        Set<Condition> fakeCondition1 = new HashSet<>();
        fakeCondition1.add(new Condition(100L, FRONT, END, null));

        Set<Condition> fakeCondition2 = new HashSet<>();
        fakeCondition2.add(new Condition(200L, FRONT, EMPTY, null));
        fakeCondition2.add(new Condition(201L, LEFT, EMPTY, null));

        Set<Rule> fakeRules = new HashSet<>();
        fakeRules.add(new Rule(100L, 11, FORWARD, null, fakeCondition1));
        fakeRules.add(new Rule(200L, 21, FORWARD, null, fakeCondition2));

        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test1")
                        .rules(fakeRules)
                .build();

        when(rulesetRepository.save(Mockito.any(Ruleset.class))).thenReturn(ruleset);

        given()
                .contentType(ContentType.JSON)
                .body(json)
        .when()
                .post("/ruleset")
                .then()
        .log().all()
                .statusCode(201)
                .body("name", equalTo("Test1"))
                .body("rules", equalTo(fakeRules));
    }

// "[ {\"priority\": \"111\", \"event_type\": \"BACK\", \"conditions\": [ { \"fact_type\":\"FRONT\",\"value_type\":\"END\"}]},{\"priority\": 111111,\"event_type\": \"FORWARD\",\"conditions\": [{ \"fact_type\":\"FRONT\",\"value_type\":\"EMPTY\"}]},{\"priority\": 111116, \"event_type\": \"LEFT\",\"conditions\": [{\"fact_type\":\"FRONT\",\"value_type\":\"WALL\"},{\"fact_type\":\"RIGHT\",\"value_type\":\"END\"}]}]"
/*
    POST endpoint tests
    -

 */

}
