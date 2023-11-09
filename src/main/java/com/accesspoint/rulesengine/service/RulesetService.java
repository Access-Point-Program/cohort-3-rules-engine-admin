package com.accesspoint.rulesengine.service;

import com.accesspoint.rulesengine.controller.CreateRuleSetRequest;
import com.accesspoint.rulesengine.entity.Ruleset;
import com.accesspoint.rulesengine.entity.Rule;
import com.accesspoint.rulesengine.entity.Condition;
import com.accesspoint.rulesengine.model.RulesetModel;
import com.accesspoint.rulesengine.repository.ConditionRepository;
import com.accesspoint.rulesengine.repository.RuleRepository;
import com.accesspoint.rulesengine.repository.RulesetRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ResponseEntity<Ruleset> createRuleset(CreateRuleSetRequest incomingRulesetData) {
        System.out.println(Ruleset.builder()
                .name(incomingRulesetData.name)
                .rules(incomingRulesetData.rules)
                .build());
        Ruleset rulesetData = this.rulesetRepository.save(
                Ruleset.builder()
                        .name(incomingRulesetData.name)
                        .rules(incomingRulesetData.rules)
                .build()
        );
        System.out.println(rulesetData);

        incomingRulesetData.rules.stream().forEach(rule -> {
            Rule ruleData = this.ruleRepository.save(
                   Rule.builder()
                           .priority(rule.getPriority())
                           .event_type(rule.getEvent_type())
                           .ruleset(rulesetData)
                           .conditions(rule.getConditions())
                   .build());
            rule.getConditions().forEach(condition -> {
                this.conditionRepository.save(
                        Condition.builder()
                                .fact_type(condition.getFact_type())
                                .value_type(condition.getValue_type())
                                .rule(ruleData)
                                .build());
            });
        });

        System.out.println(this.rulesetRepository.findAll());
        return ResponseEntity.ok().body(this.rulesetRepository.getReferenceById(rulesetData.getId()));
    }
}