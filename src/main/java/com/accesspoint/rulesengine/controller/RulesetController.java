package com.accesspoint.rulesengine.controller;

import com.accesspoint.rulesengine.entity.Ruleset;
import com.accesspoint.rulesengine.model.RulesetModel;
import com.accesspoint.rulesengine.service.RulesetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RulesetController {

    @Autowired
    private RulesetService rulesetService;

    @GetMapping("/ruleset")
    public List<RulesetModel> all() {
        return rulesetService.getAll();
    }

    @PostMapping("/ruleset")
    ResponseEntity<Ruleset> newRuleset(@RequestBody CreateRuleSetRequest request) {
        return this.rulesetService.createRuleset(request);
    }
}



// create entities, join by ruleset save ruleset