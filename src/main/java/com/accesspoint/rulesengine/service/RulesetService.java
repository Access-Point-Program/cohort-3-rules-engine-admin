package com.accesspoint.rulesengine.service;

import com.accesspoint.rulesengine.entity.Ruleset;
import com.accesspoint.rulesengine.model.RulesetModel;
import com.accesspoint.rulesengine.repository.RulesetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RulesetService {

    @Autowired
    private RulesetRepository rulesetRepository;

    public List<RulesetModel> getAll() {
        List<RulesetModel> rulesetList = rulesetRepository.findAll().stream()
                .map(ruleset -> new RulesetModel(ruleset.getId(), ruleset.getName(), ruleset.getCreation_date()))
                .collect(Collectors.toList());
        return rulesetList;
    }

    ;

//    public void createRuleset(Ruleset ruleset){
//        rulesetRepository.save(ruleset);
//    }


    public ResponseEntity<RulesetModel> createRuleset(Ruleset ruleset) {
        Ruleset newRuleset = rulesetRepository
                .save(new Ruleset(ruleset.getId(), ruleset.getName(), ruleset.getCreation_date()));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

