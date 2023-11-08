package com.accesspoint.rulesengine.controller;

import com.accesspoint.rulesengine.entity.Rule;
import java.util.List;

public class CreateRuleSetRequest {
    public String name;
    public List<Rule> rule;
}
