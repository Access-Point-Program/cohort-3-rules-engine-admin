package com.accesspoint.rulesengine.service;

import com.accesspoint.rulesengine.controller.CreateRuleSetRequest;
import com.accesspoint.rulesengine.entity.Ruleset;
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

        Ruleset rulesetData = this.rulesetRepository.save(
                Ruleset.builder()
                        .name(incomingRulesetData.name)
                        .rules(incomingRulesetData.rules)
                .build()
        );

        return ResponseEntity.ok().body(this.rulesetRepository.getReferenceById(rulesetData.getId()));
    }
}