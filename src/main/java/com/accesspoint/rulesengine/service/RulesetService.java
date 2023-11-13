package com.accesspoint.rulesengine.service;

import com.accesspoint.rulesengine.controller.CreateRuleSetRequest;
import com.accesspoint.rulesengine.entity.Ruleset;
import com.accesspoint.rulesengine.exception.PriorityAlreadyExistsException;
import com.accesspoint.rulesengine.model.RulesetModel;
import com.accesspoint.rulesengine.repository.ConditionRepository;
import com.accesspoint.rulesengine.repository.RuleRepository;
import com.accesspoint.rulesengine.repository.RulesetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

        // TODO: Exception for ruleset name field is not empty or null

        // TODO: Exception for making sure at least 1 rule is in the incomingRulesetData
        //  (and it contains a "priority", "event_type", and not null "conditions" field)

        // TODO: Exception for making sure at least 1 condition is in each rule
        //  (and it contains a "fact_type" and "value_type")


        Set<Double> existingPriorities = new HashSet<>();
        // Gather all priority values in the rule database table
        this.ruleRepository.findAll().forEach(rule -> existingPriorities.add(rule.getPriority()));
        // If the priority of an incoming rule already exists in the database, throw exception
        for (int i = 0; i < incomingRulesetData.rules.size(); i++){
            incomingRulesetData.rules.stream().forEach(rule -> {
                if (existingPriorities.contains(rule.getPriority())){
                    System.out.println("Priority Error Thrown");
                    throw new PriorityAlreadyExistsException(rule);
                }
            });
        }

        Ruleset rulesetData = this.rulesetRepository.save(
                Ruleset.builder()
                        .name(incomingRulesetData.name)
                        .rules(incomingRulesetData.rules)
                .build()
        );

        return ResponseEntity.ok().body(this.rulesetRepository.getReferenceById(rulesetData.getId()));
    }
}