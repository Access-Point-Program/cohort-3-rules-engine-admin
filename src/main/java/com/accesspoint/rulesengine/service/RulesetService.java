package com.accesspoint.rulesengine.service;

import com.accesspoint.rulesengine.controller.CreateRuleSetRequest;
import com.accesspoint.rulesengine.entity.Condition;
import com.accesspoint.rulesengine.entity.Rule;
import com.accesspoint.rulesengine.entity.Ruleset;
import com.accesspoint.rulesengine.exception.BadRequestException;
import com.accesspoint.rulesengine.exception.PriorityAlreadyExistsException;
import com.accesspoint.rulesengine.model.RulesetModel;
import com.accesspoint.rulesengine.repository.ConditionRepository;
import com.accesspoint.rulesengine.repository.RuleRepository;
import com.accesspoint.rulesengine.repository.RulesetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
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

    public ResponseEntity<Ruleset> getById(Long id){
        Ruleset ruleset = rulesetRepository.findById(id)
        .orElseThrow(() -> new BadRequestException("Id not found"));
        return new ResponseEntity<>(ruleset, HttpStatus.OK);
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

    public ResponseEntity<Ruleset> updateRuleset(Long id, Ruleset newRuleset){

//        if (incomingRulesetData.name.isEmpty()) throw new BadRequestException("Name cannot be empty");
//        if (incomingRulesetData.rules == null) throw new BadRequestException("Rules cannot be empty");
//        incomingRulesetData.rules.stream().forEach(rule -> {
//            if (rule.getPriority() == 0.0) throw new BadRequestException("Rule priority cannot be 0");
//            if (rule.getConditions() == null) throw new BadRequestException("Conditions cannot be empty");
//        });



        Ruleset updatedRuleset2 = rulesetRepository.findById(id) //
                .map(ruleset -> {
                    if (ruleset.getName() != newRuleset.getName()) {
                        System.out.println("Different Names");
                        ruleset.setName(newRuleset.getName());
                    }
                    if (ruleset.getRules() != newRuleset.getRules()) {
                        System.out.println("Different Rules");
                        List<Long> rulesetRuleIds = ruleset.getRules().stream().map(rule -> rule.getId()).toList();
                        System.out.println(rulesetRuleIds);
                        List<Long> newRulesetRuleIds = newRuleset.getRules().stream().map(rule -> rule.getId()).toList();
                        System.out.println(newRulesetRuleIds);

                        for(Rule rule : newRuleset.getRules()){
                            System.out.println(rule);
                            if (!ruleset.getRules().contains(rule)){
                                // ruleset rules DOES NOT contain current newRuleset rules rule
                                System.out.println(rule.getConditions());
                                List<Condition> conditionsList = new ArrayList<>();

                                for(Condition condition : rule.getConditions()){
                                    Condition buildCondition =
                                            Condition.builder()
                                                    .fact_type(condition.getFact_type())
                                                    .value_type(condition.getValue_type())
                                                    .build();
                                    conditionsList.add(buildCondition);
                                }

                                Rule buildRule =
                                        Rule.builder()
                                                .priority(rule.getPriority())
                                                .event_type(rule.getEvent_type())
                                                .conditions(conditionsList)
                                                .build();
                                ruleset.addRule(buildRule);
                            } //else {
//                                for (Condition condition : rule.getConditions()) {
//                                    if (!rule.getConditions().contains(condition)){
//                                        rule.addCondition(condition);
//                                        System.out.println(condition);
//                                    }
//                                }
//
//                                // ruleset rules DOES contain current newRuleset rules rule
//                                // TODO: Check to see if the conditions are different
//                                    // for each condition in conditions ?
//                            }
                        }

                        // TODO: Somehow need to remove the rules that are not included inside the newRuleset (by id?)

//                        ruleset.setRules(newRuleset.getRules());
                    }
                    System.out.println(ruleset);
                    return this.rulesetRepository.save(ruleset);
                }) //
                .orElseThrow(() -> new BadRequestException("id not found"));
        System.out.println(updatedRuleset2);
//        updatedRuleset.setName(ruleset.getName());
//        updatedRuleset.setRules(ruleset.getRules());

        return new ResponseEntity<>(updatedRuleset2, HttpStatus.OK);
    }
}