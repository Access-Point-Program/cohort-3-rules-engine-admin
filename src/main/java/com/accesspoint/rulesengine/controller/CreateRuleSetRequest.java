package com.accesspoint.rulesengine.controller;

import com.accesspoint.rulesengine.entity.Condition;
import com.accesspoint.rulesengine.entity.Rule;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;

public class CreateRuleSetRequest {
    public String name;
    public List<Map<String, Object>> rules;
}
