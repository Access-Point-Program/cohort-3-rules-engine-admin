package com.accesspoint.rulesengine.modelassembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.accesspoint.rulesengine.entity.Condition;
import com.accesspoint.rulesengine.entity.Ruleset;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

//@Component
//public class ConditionModelAssembler implements RepresentationModelAssembler<Condition, EntityModel<Condition>>{
//
//    @Override
//    public EntityModel<Condition> toModel(Condition condition) {
//
//        EntityModel<Condition> conditionModel = EntityModel.of(condition,
//                linkTo(methodOn(ConditionController.class).one(condition.getId())).withSelfRel(),
//                linkTo(methodOn(ConditionController.class).all()).withRel("conditions"));
//
//        return conditionModel;
//    }
//}