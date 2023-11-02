package com.accesspoint.rulesengine.controller;

import com.accesspoint.rulesengine.model.RulesetModel;
import com.accesspoint.rulesengine.service.RulesetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class RulesetController {

    @Autowired
    private RulesetService rulesetService;

    @GetMapping("/ruleset")
    public List<RulesetModel> all() {
        return rulesetService.getAll();
    }


}
