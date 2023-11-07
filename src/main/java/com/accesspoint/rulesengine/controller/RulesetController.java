package com.accesspoint.rulesengine.controller;

import com.accesspoint.rulesengine.entity.Rule;
import com.accesspoint.rulesengine.entity.Ruleset;
import com.accesspoint.rulesengine.model.RulesetModel;
import com.accesspoint.rulesengine.service.RuleService;
import com.accesspoint.rulesengine.service.RulesetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

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
    ResponseEntity<RulesetModel> newRuleset(@Valid @RequestBody Ruleset ruleset) {
        return rulesetService.createRuleset(ruleset);
    }
}

// create entities, join by ruleset save ruleset