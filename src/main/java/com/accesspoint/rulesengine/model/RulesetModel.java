package com.accesspoint.rulesengine.model;

import java.sql.Timestamp;
import lombok.Getter;

// Model to stop our GetAll endpoint from getting data from rules and conditions
public class RulesetModel {

    @Getter
    private Long id;

    @Getter
    private String name;

    @Getter
    private Timestamp creation_date;

    public RulesetModel(Long id, String name, Timestamp creation_date) {
        this.id = id;
        this.name = name;
        this.creation_date = creation_date;
    }

    @Override
    public String toString() {
        return "Ruleset{" + "id=" + this.id + ", name=" + this.name + ", creation date=" + this.creation_date + "}";
    }
}