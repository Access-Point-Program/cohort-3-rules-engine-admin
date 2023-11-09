package com.accesspoint.rulesengine.model;

import com.accesspoint.rulesengine.entity.EventType;
import com.accesspoint.rulesengine.entity.Ruleset;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

public class RuleModel {
    @Getter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ruleset_id")
    @Getter @Setter private Ruleset ruleset;

    @Getter @Setter private double priority;

    @Getter @Setter private EventType event_type;

    public RuleModel(Long id, Ruleset ruleset, double priority, EventType event_type) {
        this.id = id;
        this.ruleset = ruleset;
        this.priority = priority;
        this.event_type = event_type;
    }

    @Override
    public String toString() {
        return "Rule{" + "id=" + this.id + ", ruleset id=" + this.ruleset + ", priority=" + this.priority + ", event type=" + this.event_type + "}";
    }
}
