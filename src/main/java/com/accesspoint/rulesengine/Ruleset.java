package com.accesspoint.rulesengine;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;


@Entity
public class Ruleset {

    @Getter @Setter private @Id @GeneratedValue Long id;

    @Getter @Setter private String name;

    Ruleset() {}

    Ruleset(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object rs) {

        if (this == rs)
            return true;
        if (!(rs instanceof Ruleset))
            return false;
        Ruleset ruleset = (Ruleset) rs;
        return Objects.equals(this.id, ruleset.id) && Objects.equals(this.name, ruleset.name);
    }

    @Override
    public int hashCode() { return Objects.hash(this.id, this.name); }

    @Override
    public String toString() {
        return "Ruleset{" + "id=" + this.id + ", name=" + this.name;
    }
}
