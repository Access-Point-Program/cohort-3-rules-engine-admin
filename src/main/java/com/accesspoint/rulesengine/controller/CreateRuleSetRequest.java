package com.accesspoint.rulesengine.controller;

import com.accesspoint.rulesengine.entity.Rule;
import java.util.Set;

public class CreateRuleSetRequest {
    public String name;
    public Set<Rule> rules;
}