package com.accesspoint.rulesengine.controller;



import com.accesspoint.rulesengine.entity.Ruleset;
import com.accesspoint.rulesengine.model.RulesetModel;
import com.accesspoint.rulesengine.service.RulesetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class RulesetController {

    private final RulesetService rulesetService;

    public RulesetController(RulesetService service) {
        this.rulesetService = service;
    }

    @GetMapping("/ruleset")
    public List<RulesetModel> all() {
        return rulesetService.getAll();
    }


}
