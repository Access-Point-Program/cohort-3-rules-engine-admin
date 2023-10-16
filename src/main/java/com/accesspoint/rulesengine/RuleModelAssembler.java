package com.accesspoint.rulesengine;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class RuleModelAssembler implements RepresentationModelAssembler<Rule, EntityModel<Rule>>{

    @Override
    public EntityModel<Rule> toModel(Rule rule) {

        EntityModel<Rule> ruleModel = EntityModel.of(rule,
                linkTo(methodOn(RuleController.class).one(rule.getId())).withSelfRel(),
                linkTo(methodOn(RuleController.class).all()).withRel("rules"));

        return ruleModel;
    }


}
