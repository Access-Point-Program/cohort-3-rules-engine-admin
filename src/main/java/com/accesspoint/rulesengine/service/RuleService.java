package com.accesspoint.rulesengine.service;

import com.accesspoint.rulesengine.entity.Rule;
//import com.accesspoint.rulesengine.model.RuleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RuleService {
    @Autowired
    private RuleRepository ruleRepository;

    public ResponseEntity<RuleModel> createRule(Rule rule) {
        Rule newRule = ruleRepository
                .save(new Rule(rule.getId(), rule.getRuleset(), rule.getPriority(), rule.getEvent_type()));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
