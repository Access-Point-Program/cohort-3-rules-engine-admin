package com.accesspoint.rulesengine.service;

import com.accesspoint.rulesengine.entity.Condition;
import com.accesspoint.rulesengine.entity.Rule;
import com.accesspoint.rulesengine.entity.Ruleset;
import com.accesspoint.rulesengine.model.RulesetModel;
import com.accesspoint.rulesengine.repository.ConditionRepository;
import com.accesspoint.rulesengine.repository.RuleRepository;
import com.accesspoint.rulesengine.repository.RulesetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RulesetService {

    @Autowired
    private RulesetRepository rulesetRepository;

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private ConditionRepository conditionRepository;

    public List<RulesetModel> getAll() {
        List<RulesetModel> rulesetList = rulesetRepository.findAll().stream()
                .map(ruleset -> new RulesetModel(ruleset.getId(), ruleset.getName(), ruleset.getCreation_date()))
                .collect(Collectors.toList());
        return rulesetList;
    }

    ;

//    public void createRuleset(Ruleset ruleset){
//        rulesetRepository.save(ruleset);
//    }


    public ResponseEntity<Ruleset> createRuleset(Ruleset ruleset, Rule rule, Condition condition) {
        Ruleset newRuleset = rulesetRepository
                .save(new Ruleset(ruleset.getId(), ruleset.getName(), ruleset.getCreation_date()));
        Rule newRule = ruleRepository
                .save(new Rule(rule.getId(), rule.getRuleset_id(), rule.getPriority(), rule.getEvent_type()));
        Condition newCondition = conditionRepository
                .save(new Condition(condition.getId(), condition.getRule(), condition.getFact_type(), condition.getValue_type()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

//ruleset service has multiple repositories if you need to break it up