package com.accesspoint.rulesengine.service;

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

    public List<Ruleset> getAllExtended() {
        List<Ruleset> rulesetList = rulesetRepository.findAll();
        return rulesetList;
    }

    public ResponseEntity<Ruleset> getById(Long id){
        Ruleset ruleset = rulesetRepository.findById(id)
        .orElseThrow(() -> new BadRequestException("Id not found"));
        return new ResponseEntity<>(ruleset, HttpStatus.OK);
    }

    public ResponseEntity<Ruleset> createRuleset(Ruleset incomingRulesetData) {
        // Error handling
        if (incomingRulesetData.getName() == null || incomingRulesetData.getName().isEmpty()) throw new BadRequestException("Name cannot be empty");
        if (incomingRulesetData.getRules() == null || incomingRulesetData.getRules().isEmpty()) throw new BadRequestException("Rules cannot be empty");
        incomingRulesetData.getRules().stream().forEach(rule -> {
            if (rule.getPriority() == 0.0) throw new BadRequestException("Rule priority cannot be 0");
            if (rule.getConditions() == null || rule.getConditions().isEmpty()) throw new BadRequestException("Conditions cannot be empty");
            if (rule.getEvent_type() == null) throw new BadRequestException("Rule event type cannot be null");
            rule.getConditions().stream().forEach(condition -> {
                if (condition.getFact_type() == null) throw new BadRequestException("Condition fact type cannot be null");
                if (condition.getValue_type() == null) throw new BadRequestException("Condition value type cannot be null");
            });
        });

        List<Double> existingPriorities = new ArrayList<>();
        // Gather all priority values from the incoming ruleset
        incomingRulesetData.getRules().forEach(rule -> {
            // Throw error if duplicate priorities
            if(existingPriorities.contains(rule.getPriority())){
                System.out.println("Priority Error Thrown");
                throw new PriorityAlreadyExistsException(rule);
            } else {
                existingPriorities.add(rule.getPriority());
            }
        });

        // Building out new ruleset with incomingRulesetData
        Ruleset rulesetData = this.rulesetRepository.save(
                Ruleset.builder()
                        .name(incomingRulesetData.getName())
                        .rules(incomingRulesetData.getRules())
                .build()
        );
        return ResponseEntity.ok().body(this.rulesetRepository.getReferenceById(rulesetData.getId()));
    }

    public ResponseEntity<Ruleset> updateRuleset(Long id, Ruleset incomingRuleset) {
        // Error handling
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

        // Updating data in oldRuleset to be the same as our incomingRuleset
        Ruleset updatedRuleset2 = rulesetRepository.findById(id) //
                .map(oldRuleset -> {
                    if (!oldRuleset.getName().equals(incomingRuleset.getName())) oldRuleset.setName(incomingRuleset.getName());
                    if (oldRuleset.getRules() != incomingRuleset.getRules()) {
                        // Rules data in incomingRuleset is different to oldRuleset
                        for (Rule incomingRule : incomingRuleset.getRules()) {
                            if (oldRuleset.getRules().contains(incomingRule)) continue;
                            if (incomingRule.getId() == null) {
                                // oldRuleset rules DOES NOT contain incomingRuleset rule
                                incomingRule.setRuleset(oldRuleset);
                                Rule savedRule = this.ruleRepository.save(incomingRule);
                                oldRuleset.addRuleToList(savedRule);
                            } else {
                                // oldRuleset contains incomingRuleset rule but the data is different
                                Optional<Rule> updatedRule = ruleRepository.findById(incomingRule.getId())
                                        .map(oldRule -> {
                                            if (oldRule.getPriority() != incomingRule.getPriority()) oldRule.setPriority(incomingRule.getPriority());
                                            if (oldRule.getEvent_type() != incomingRule.getEvent_type()) oldRule.setEvent_type(incomingRule.getEvent_type());
                                            if (oldRule.getConditions() != incomingRule.getConditions()) {
                                                // Conditions data for incomingRule is different to oldRule
                                                for (Condition newCondition : incomingRule.getConditions()) {
                                                    if (oldRule.getConditions().contains(newCondition)) continue;
                                                    if (newCondition.getId() == null) {
                                                        // oldRule conditions DOES NOT contain incomingRule condition
                                                        newCondition.setRule(incomingRule);
                                                        Condition savedCondition = this.conditionRepository.save(newCondition);
                                                        oldRule.addConditionToList(savedCondition);
                                                    } else {
                                                        // oldRule contains incomingRule condition but the data is different
                                                        Optional<Condition> updatedCondition = conditionRepository.findById(newCondition.getId())
                                                                .map(oldCondition -> {
                                                                    if (oldCondition.getFact_type() != newCondition.getFact_type()) oldCondition.setFact_type(newCondition.getFact_type());
                                                                    if (oldCondition.getValue_type() != newCondition.getValue_type()) oldCondition.setValue_type(newCondition.getValue_type());
                                                                    return oldCondition;
                                                                });
                                                    }
                                                }
                                            }
                                            // Deletes conditions that are in oldRule but not in the newRule
                                            List<Long> oldRuleConditionIds = oldRule.getConditions().stream().map(condition -> condition.getId()).toList();
                                            List<Long> incomingRuleConditionIds = incomingRule.getConditions().stream().map(condition -> condition.getId()).toList();

                                            if (oldRuleConditionIds.size() > incomingRuleConditionIds.size()) {
                                                for (Long conditionId : oldRuleConditionIds) {
                                                    if (!incomingRuleConditionIds.contains(conditionId)) oldRule.removeConditionFromList(conditionRepository.getReferenceById(conditionId));
                                                }
                                            }
                                            return oldRule;
                                        });
                            }
                        }
                        // Deletes rules that are in oldRuleset but not in the newRuleset
                        List<Long> oldRulesetRuleIds = oldRuleset.getRules().stream().map(rule -> rule.getId()).toList();
                        List<Long> incomingRulesetRuleIds = incomingRuleset.getRules().stream().map(rule -> rule.getId()).toList();

                        if (oldRulesetRuleIds.size() > incomingRulesetRuleIds.size()) {
                            for (Long ruleID : oldRulesetRuleIds) {
                                if (!incomingRulesetRuleIds.contains(ruleID)) oldRuleset.removeRuleFromList(ruleRepository.getReferenceById(ruleID));
                            }
                        }
                    }
                    return rulesetRepository.save(oldRuleset);
                })
                .orElseThrow(() -> new BadRequestException("id not found")); // Handles when PUT endpoint is called with an id that does not exist
        return new ResponseEntity<>(updatedRuleset2, HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteRulesetById(Long id) {
        if (rulesetRepository.findById(id).isEmpty()) {
            // Error Handling
            throw new BadRequestException("Id does not exist");
        } else {
            // Deleting ruleset with incoming id
            rulesetRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}