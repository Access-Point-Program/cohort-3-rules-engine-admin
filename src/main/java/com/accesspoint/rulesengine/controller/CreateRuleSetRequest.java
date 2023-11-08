package com.accesspoint.rulesengine.controller;


import com.accesspoint.rulesengine.entity.Rule;

public class CreateRuleSetRequest {
    public String name;
    public Rule rule = new Rule();
}
