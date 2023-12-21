package com.accesspoint.rulesengine.controller;

import com.accesspoint.rulesengine.entity.Ruleset;
import com.accesspoint.rulesengine.model.RulesetModel;
import com.accesspoint.rulesengine.service.RulesetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class RulesetController {

    @Autowired
    private RulesetService rulesetService;

    @CrossOrigin(origins = {"http://host.docker.internal:9030", "http://host.docker.internal:9005", "http://host.docker.internal:9010"})
    @GetMapping("/ruleset") // Calls service to get all ruleset names, ids, and creation dates
    public List<RulesetModel> all() {
        return rulesetService.getAll();
    }

    @CrossOrigin(origins = {"http://host.docker.internal:9030", "http://host.docker.internal:9005", "http://host.docker.internal:9010"})
    @GetMapping("/ruleset/{id}") // Calls service to get a ruleset by its id to return the ruleset's name, id, creation date, and it's rules and conditions information
    public ResponseEntity<Ruleset> one(@PathVariable Long id) {
        return rulesetService.getById(id);
    }

    @CrossOrigin(origins = "http://host.docker.internal:9005")
    @GetMapping("/ruleset-extended") // Calls service to get all rulesets and their name, id, creation date, rules and conditions
    public List<Ruleset> allExtended() {
        return rulesetService.getAllExtended();
    }

    @PostMapping("/ruleset") // Calls service to create a ruleset and it's rules and conditions
    ResponseEntity<Ruleset> newRuleset(@RequestBody Ruleset ruleset) {
        return this.rulesetService.createRuleset(ruleset);
    }

    @PutMapping("/ruleset/{id}") // Calls service to update an existing ruleset and it's rules and conditions
    ResponseEntity<Ruleset> updateRuleset(@PathVariable Long id, @RequestBody Ruleset ruleset) {
        return rulesetService.updateRuleset(id, ruleset);
    }

    @CrossOrigin(origins = "http://host.docker.internal:9030")
    @DeleteMapping("/ruleset/{id}") // Calls service to delete an existing ruleset and it's rules and conditions
    ResponseEntity<HttpStatus> deleteRuleset(@PathVariable Long id) {
        return rulesetService.deleteRulesetById(id);
    }
}