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
                    //TODO: optimize Hibernate select calls
                    if (ruleset.getName().equals(newRuleset.getName())) {
                        System.out.println("Different Names");
                        ruleset.setName(newRuleset.getName());
                    }
                    if (ruleset.getRules() != newRuleset.getRules()) {
                        System.out.println("Different Rules");
                        for(Rule rule : newRuleset.getRules()){
                            System.out.println(rule);
                            if (rule.getId() == null){
                                // ruleset rules DOES NOT contain current newRuleset rules rule
                                System.out.println("RULE RECOGNIZED AS NEW RULE");
                                rule.setRuleset(ruleset);
                                this.ruleRepository.save(rule);
                                ruleset.addRuleToList(rule);
                            } else {
                                Optional<Rule> updatedRule = ruleRepository.findById(rule.getId())
                                        .map(oldRule -> {
                                            if (oldRule.getPriority() != rule.getPriority()){
                                                System.out.println("Different Rule Priority");
                                                oldRule.setPriority(rule.getPriority());
                                            }
                                            if (oldRule.getEvent_type() != rule.getEvent_type()){
                                                System.out.println("Different Rule Event Type");
                                                oldRule.setEvent_type(rule.getEvent_type());
                                            }
                                            if (oldRule.getConditions() != rule.getConditions()){
                                                System.out.println("Different Conditions");
                                                for(Condition condition : rule.getConditions()){
                                                    if (condition.getId() == null){
                                                        // ruleset rules DOES NOT contain current newRuleset rules rule
                                                        System.out.println("CONDITION RECOGNIZED AS NEW CONDITION");
                                                        condition.setRule(rule);
                                                        this.conditionRepository.save(condition);
                                                        oldRule.addConditionToList(condition);
                                                    } else {
                                                        Optional<Condition> updatedCondition = conditionRepository.findById(condition.getId())
                                                                .map(oldCondition -> {
                                                                    if (oldCondition.getFact_type() != condition.getFact_type()){
                                                                        System.out.println("Different Rule Priority");
                                                                        oldCondition.setFact_type(condition.getFact_type());
                                                                    }
                                                                    if (oldCondition.getValue_type() != condition.getValue_type()){
                                                                        System.out.println("Different Rule Event Type");
                                                                        oldCondition.setValue_type(condition.getValue_type());
                                                                    }

                                                                    return oldCondition;
                                                                });
                                                    }
                                                }
                                            }
                                            List<Long> ruleConditionIds = oldRule.getConditions().stream().map(condition -> condition.getId()).toList();
                                            System.out.println(ruleConditionIds);
                                            List<Long> newRuleConditionIds = rule.getConditions().stream().map(condition -> condition.getId()).toList();
                                            System.out.println(newRuleConditionIds);

                                            if(ruleConditionIds.size() > newRuleConditionIds.size()) {
                                                for(Long conditionId: ruleConditionIds) {
                                                    if (!newRuleConditionIds.contains(conditionId)) {
                                                        System.out.println("hi");
                                                        oldRule.removeConditionFromList(conditionRepository.getReferenceById(conditionId));
                                                    }
                                                }
                                                System.out.println(oldRule.getConditions().stream().map(condition -> condition.getId()).toList());
                                                System.out.println(rule.getConditions().stream().map(condition -> condition.getId()).toList());
                                            }

                                            return oldRule;
                                        });
                            }
//                            for
//                            List<Long> ruleConditionIds = rule.getConditions().stream().map(condition -> condition.getId()).toList();
//                            System.out.println(ruleConditionIds);
//                            List<Long> ruleConditionRuleIds = newRuleset.getRules().stream().map(rule -> rule.getId()).toList();
//                            System.out.println(ruleConditionRuleIds);
                        }

                        // TODO: Somehow need to remove the rules that are not included inside the newRuleset (by id?)
                        List<Long> rulesetRuleIds = ruleset.getRules().stream().map(rule -> rule.getId()).toList();
                        System.out.println(rulesetRuleIds);
                        List<Long> newRulesetRuleIds = newRuleset.getRules().stream().map(rule -> rule.getId()).toList();
                        System.out.println(newRulesetRuleIds);

                        if(rulesetRuleIds.size() > newRulesetRuleIds.size()) {
                            for(Long ruleID: rulesetRuleIds) {
                                if (!newRulesetRuleIds.contains(ruleID)) {
                                    ruleset.removeRuleFromList(ruleRepository.getReferenceById(ruleID));
                                }
                            }
                        }



                    }
                    System.out.println(ruleset);
                    return rulesetRepository.save(ruleset);
                })
                .orElseThrow(() -> new BadRequestException("id not found"));
        System.out.println(updatedRuleset2);

        return new ResponseEntity<>(updatedRuleset2, HttpStatus.OK);
    }
}