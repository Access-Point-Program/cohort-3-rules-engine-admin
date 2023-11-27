package com.accesspoint.rulesengine.service;

import com.accesspoint.rulesengine.controller.CreateRuleSetRequest;
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
import org.springframework.http.HttpStatus;

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

    public ResponseEntity<Ruleset> createRuleset(Ruleset incomingRulesetData) {

        if (incomingRulesetData.getName().isEmpty()) throw new BadRequestException("Name cannot be empty");
        if (incomingRulesetData.getRules() == null) throw new BadRequestException("Rules cannot be empty");
        incomingRulesetData.getRules().stream().forEach(rule -> {
            if (rule.getPriority() == 0.0) throw new BadRequestException("Rule priority cannot be 0");
            if (rule.getConditions() == null) throw new BadRequestException("Conditions cannot be empty");
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

        Ruleset rulesetData = this.rulesetRepository.save(
                Ruleset.builder()
                        .name(incomingRulesetData.getName())
                        .rules(incomingRulesetData.getRules())
                .build()
        );

        return ResponseEntity.ok().body(this.rulesetRepository.getReferenceById(rulesetData.getId()));
    }

    public ResponseEntity<HttpStatus> deleteRulesetById(Long id) {
        if (rulesetRepository.findById(id).isEmpty()) {
            throw new BadRequestException("Id does not exist");
        } else {
            rulesetRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}