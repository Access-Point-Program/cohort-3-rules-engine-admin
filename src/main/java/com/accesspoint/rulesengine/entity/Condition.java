package com.accesspoint.rulesengine.entity;

import java.util.Objects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Condition {
    @Getter @Setter private @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;

    @ManyToOne
    @JoinColumn(name = "rule_id")
    @Getter @Setter private Rule rule;

    @Getter @Setter private FactType fact_type;

    @Getter @Setter private ValueType value_type;

    Condition() {}
    Condition(FactType fact_type, ValueType value_type) {
        this.fact_type = fact_type;
        this.value_type = value_type;
        // if stuff breaks, add secondary key here
    }

    @Override
    public boolean equals(Object c) {

        if (this == c)
            return true;
        if (!(c instanceof Condition))
            return false;
        Condition condition = (Condition) c;
        return Objects.equals(this.id, condition.id)
                && Objects.equals(this.rule, condition.rule)
                && Objects.equals(this.fact_type, condition.fact_type)
                && Objects.equals(this.value_type, condition.value_type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.rule, this.fact_type, this.value_type);
    }

    @Override
    public String toString() {
        return "Condition{" + "id=" + this.id + ", rule_id=" + this.rule + ", fact_type=" + this.fact_type + ", value_type=" + this.value_type;
    }




}
