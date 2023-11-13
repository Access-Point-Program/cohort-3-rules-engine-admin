package com.accesspoint.rulesengine.exception;

import com.accesspoint.rulesengine.entity.Rule;

public class PriorityAlreadyExistsException extends RuntimeException{
    public PriorityAlreadyExistsException(Rule rule) {
        super("Rule priority already exists in database.\nRule in question: " + rule);
    }
}
