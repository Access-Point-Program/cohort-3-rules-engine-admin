package com.accesspoint.rulesengine.service;

import com.accesspoint.rulesengine.controller.CreateRuleSetRequest;
import com.accesspoint.rulesengine.entity.Ruleset;
import com.accesspoint.rulesengine.exception.BadRequestException;
import com.accesspoint.rulesengine.exception.PriorityAlreadyExistsException;
import com.accesspoint.rulesengine.model.RulesetModel;
import com.accesspoint.rulesengine.repository.ConditionRepository;
import com.accesspoint.rulesengine.repository.RuleRepository;
import com.accesspoint.rulesengine.repository.RulesetRepository;
import org.apache.tomcat.util.digester.Rules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.accesspoint.rulesengine.entity.EventType.*;

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

    public List<Ruleset> getById(Long id){
        List<Ruleset> rulesetList = rulesetRepository.findById(id).stream()
                .map(ruleset -> new Ruleset(ruleset.getId(), ruleset.getName(), ruleset.getCreation_date()))
                .collect(Collectors.toList());
        return rulesetList;
    }

    public ResponseEntity<Ruleset> createRuleset(CreateRuleSetRequest incomingRulesetData) {

        if (incomingRulesetData.name.isEmpty()) throw new BadRequestException("Name cannot be empty");
        if (incomingRulesetData.rules == null) throw new BadRequestException("Rules cannot be empty");
        incomingRulesetData.rules.stream().forEach(rule -> {
            if (rule.getPriority() == 0.0) throw new BadRequestException("Rule priority cannot be 0");
            if (rule.getConditions() == null) throw new BadRequestException("Conditions cannot be empty");
        });

        List<Double> existingPriorities = new ArrayList<>();
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