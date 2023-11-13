package com.accesspoint.rulesengine.apitests.ruleset;

import com.accesspoint.rulesengine.entity.Rule;
import com.accesspoint.rulesengine.entity.Ruleset;
import com.accesspoint.rulesengine.repository.RulesetRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
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
        JSONObject json = new JSONObject().put("name", "Test1").put("rules", " [ {\"priority\": \"111\", \"event_type\": \"BACK\", \"conditions\": [ { \"fact_type\":\"FRONT\",\"value_type\":\"END\"}]},{\"priority\": 111111,\"event_type\": \"FORWARD\",\"conditions\": [{ \"fact_type\":\"FRONT\",\"value_type\":\"EMPTY\"}]},{\"priority\": 111116, \"event_type\": \"LEFT\",\"conditions\": [{\"fact_type\":\"FRONT\",\"value_type\":\"WALL\"},{\"fact_type\":\"RIGHT\",\"value_type\":\"END\"}]}]");

//        ObjectMapper mapper = new ObjectMapper();
//        Ruleset rules = mapper.convertValue(json, new TypeReference<Ruleset>() {
//        });

        Ruleset ruleset =
                Ruleset.builder()
                        .name("Test1")
                        .rules(json.get("rules").)
                        .build();
        when(rulesetRepository.save(Mockito.any(Ruleset.class))).thenReturn(rules);


        given()
                .contentType(ContentType.JSON)
                .body(json.toString())
                .when()
                .post("/ruleset")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", equalTo("Test1"))
                .body("rules", equalTo("[ {\"priority\": \"111\", \"event_type\": \"BACK\", \"conditions\": [ { \"fact_type\":\"FRONT\",\"value_type\":\"END\"}]},{\"priority\": 111111,\"event_type\": \"FORWARD\",\"conditions\": [{ \"fact_type\":\"FRONT\",\"value_type\":\"EMPTY\"}]},{\"priority\": 111116, \"event_type\": \"LEFT\",\"conditions\": [{\"fact_type\":\"FRONT\",\"value_type\":\"WALL\"},{\"fact_type\":\"RIGHT\",\"value_type\":\"END\"}]}]"));
    }

/*
    POST endpoint tests
    -

 */

}
