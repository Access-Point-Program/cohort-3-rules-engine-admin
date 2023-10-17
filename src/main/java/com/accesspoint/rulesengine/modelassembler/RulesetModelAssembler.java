package com.accesspoint.rulesengine.modelassembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.accesspoint.rulesengine.entity.Ruleset;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class RulesetModelAssembler implements RepresentationModelAssembler<Ruleset, EntityModel<Ruleset>>{

    @Override
    public EntityModel<Ruleset> toModel(Ruleset ruleset) {

        EntityModel<Ruleset> rulesetModel = EntityModel.of(ruleset,
                linkTo(methodOn(RulesetController.class).one(ruleset.getId())).withSelfRel(),
                linkTo(methodOn(RulesetController.class).all()).withRel("rulesets"));

        return rulesetModel;
    }
}