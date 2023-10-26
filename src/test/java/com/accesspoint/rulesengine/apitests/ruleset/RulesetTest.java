package com.accesspoint.rulesengine.apitests.ruleset;

import com.accesspoint.rulesengine.entity.Ruleset;
import com.accesspoint.rulesengine.repository.RulesetRepository;
import io.restassured.RestAssured;
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
    public void StatusCode200() {
        given()
        .when().get("/ruleset")
        .then().log().all().statusCode(200);
    }

    @Test
    public void RequestBody() {

        Ruleset testRuleset = new Ruleset("test");
        testRuleset.setId(100L);
        testRuleset.setCreation_date(Timestamp.valueOf("2020-01-01 03:12:50.581"));

        Ruleset testRuleset2 = new Ruleset("test2");
        testRuleset2.setId(101L);
        testRuleset2.setCreation_date(Timestamp.valueOf("3020-01-01 03:12:50.581"));

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
    public void isResponseTypeJson(){
        given()
        .when().get("/ruleset")
        .then().log().headers()
                .header("Content-Type", "application/json");
    }

}
