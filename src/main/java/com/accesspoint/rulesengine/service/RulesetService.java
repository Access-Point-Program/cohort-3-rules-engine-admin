package com.accesspoint.rulesengine.service;

import com.accesspoint.rulesengine.model.RulesetModel;
import com.accesspoint.rulesengine.repository.RulesetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RulesetService {

    private final RulesetRepository rulesetRepository;

    public RulesetService(RulesetRepository repository) {
        this.rulesetRepository = repository;
    }

    public List<RulesetModel> getAll() {
        List<RulesetModel> rulesetList = rulesetRepository.findAll().stream()
                .map(ruleset -> new RulesetModel(ruleset.getId(), ruleset.getName(), ruleset.getCreation_date()))
                .collect(Collectors.toList());
        return rulesetList;
    };
}