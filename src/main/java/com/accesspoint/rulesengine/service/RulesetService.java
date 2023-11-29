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

    public ResponseEntity<Ruleset> updateRuleset(Long id, Ruleset incomingRuleset){

        if (incomingRuleset.getName() == null || incomingRuleset.getName().isEmpty()) throw new BadRequestException("Name cannot be empty");
        if (incomingRuleset.getRules() == null || incomingRuleset.getRules().isEmpty()) throw new BadRequestException("Rules cannot be empty");
        incomingRuleset.getRules().stream().forEach(rule -> {
            if (rule.getPriority() == 0.0) throw new BadRequestException("Rule priority cannot be 0");
            if (rule.getEvent_type() == null) throw new BadRequestException("Rule event type cannot be null");
            if (rule.getConditions() == null || rule.getConditions().isEmpty()) throw new BadRequestException("Conditions cannot be empty");
            rule.getConditions().stream().forEach(condition -> {
                if (condition.getFact_type() == null) throw new BadRequestException("Condition fact type cannot be null");
                if (condition.getValue_type() == null) throw new BadRequestException("Condition value type cannot be null");
            });
        });



        Ruleset updatedRuleset2 = rulesetRepository.findById(id) //
                .map(oldRuleset -> {
                    //TODO: optimize Hibernate select calls
                    if (!oldRuleset.getName().equals(incomingRuleset.getName())) oldRuleset.setName(incomingRuleset.getName());
                    if (oldRuleset.getRules() != incomingRuleset.getRules()) {
                        for(Rule incomingRule : incomingRuleset.getRules()){

                            if (incomingRule.getId() == null){
                                // oldRuleset rules DOES NOT contain incomingRuleset rule
                                incomingRule.setRuleset(oldRuleset);
                                Rule savedRule = this.ruleRepository.save(incomingRule);
                                oldRuleset.addRuleToList(savedRule);
                            } else {
                                Optional<Rule> updatedRule = ruleRepository.findById(incomingRule.getId())
                                        .map(oldRule -> {
                                            if (oldRule.getPriority() != incomingRule.getPriority()) oldRule.setPriority(incomingRule.getPriority());
                                            if (oldRule.getEvent_type() != incomingRule.getEvent_type()) oldRule.setEvent_type(incomingRule.getEvent_type());
                                            if (oldRule.getConditions() != incomingRule.getConditions()){
                                                for(Condition newCondition : incomingRule.getConditions()){
                                                    if (newCondition.getId() == null){
                                                        // oldRule conditions DOES NOT contain incomingRuleset condition
                                                        newCondition.setRule(incomingRule);
                                                        Condition savedCondition = this.conditionRepository.save(newCondition);
                                                        oldRule.addConditionToList(savedCondition);
                                                    } else {
                                                        Optional<Condition> updatedCondition = conditionRepository.findById(newCondition.getId())
                                                                .map(oldCondition -> {
                                                                    if (oldCondition.getFact_type() != newCondition.getFact_type()) oldCondition.setFact_type(newCondition.getFact_type());
                                                                    if (oldCondition.getValue_type() != newCondition.getValue_type()) oldCondition.setValue_type(newCondition.getValue_type());

                                                                    return oldCondition;
                                                                });
                                                    }
                                                }
                                            }
                                            List<Long> oldRuleConditionIds = oldRule.getConditions().stream().map(condition -> condition.getId()).toList();
                                            List<Long> incomingRuleConditionIds = incomingRule.getConditions().stream().map(condition -> condition.getId()).toList();

                                            if(oldRuleConditionIds.size() > incomingRuleConditionIds.size()) {
                                                for(Long conditionId: oldRuleConditionIds) {
                                                    if (!incomingRuleConditionIds.contains(conditionId)) oldRule.removeConditionFromList(conditionRepository.getReferenceById(conditionId));

                                                }
                                            }

                                            return oldRule;
                                        });
                            }
                        }

                        List<Long> oldRulesetRuleIds = oldRuleset.getRules().stream().map(rule -> rule.getId()).toList();
                        List<Long> incomingRulesetRuleIds = incomingRuleset.getRules().stream().map(rule -> rule.getId()).toList();

                        if(oldRulesetRuleIds.size() > incomingRulesetRuleIds.size()) {
                            for(Long ruleID: oldRulesetRuleIds) {
                                if (!incomingRulesetRuleIds.contains(ruleID)) oldRuleset.removeRuleFromList(ruleRepository.getReferenceById(ruleID));

                            }
                        }
                    }
                    return rulesetRepository.save(oldRuleset);
                })
                .orElseThrow(() -> new BadRequestException("id not found"));

        return new ResponseEntity<>(updatedRuleset2, HttpStatus.OK);
    }
}