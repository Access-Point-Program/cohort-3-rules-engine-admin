package com.accesspoint.rulesengine;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rule")
public class Rule {

    @Getter @Setter private @Id Long id;
    @OneToMany(mappedBy = "rule")
    private Set<Condition> Conditions = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "ruleset_id")
    @Getter @Setter private Ruleset ruleset;

    @Getter @Setter private double priority;

    @Getter @Setter private EventType event;

    Rule(double priority, EventType event_type) {
        this.priority = priority;
        this.event = event;
        // if stuff breaks, add secondary key here
    }

    @Override
    public boolean equals(Object r) {

        if (this == r)
            return true;
        if (!(r instanceof Rule))
            return false;
        Rule rule = (Rule) r;
        return Objects.equals(this.id, rule.id)
                && Objects.equals(this.ruleset, rule.ruleset)
                && Objects.equals(this.priority, rule.priority)
                && Objects.equals(this.event, rule.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.ruleset, this.priority, this.event);
    }

    @Override
    public String toString() {
        return "Rule{" + "id=" + this.id + ", ruleset_id=" + this.ruleset + ", priority=" + this.priority + ", event_type=" + this.event;
    }
}
