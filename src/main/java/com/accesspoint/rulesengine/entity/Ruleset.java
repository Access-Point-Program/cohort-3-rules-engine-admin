package com.accesspoint.rulesengine.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Entity
@Validated
@Table(name = "ruleset")
public class Ruleset {

    @Getter @Setter private @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id ;

    @OneToMany(mappedBy = "ruleset", fetch = FetchType.EAGER)
    private Set<Rule> Rules = new HashSet<>();

    @Getter @Setter @NotNull
    private String name;

    @CreationTimestamp
    @Getter @Setter @NotNull private Timestamp creation_date;

    Ruleset() {}

    public Ruleset(Long id, String name, Timestamp creation_date) {
        this.name = name;
        this.id = id;
        this.creation_date = creation_date;
    }

    @Override
    public boolean equals(Object rs) {

        if (this == rs)
            return true;
        if (!(rs instanceof Ruleset))
            return false;
        Ruleset ruleset = (Ruleset) rs;
        return Objects.equals(this.id, ruleset.id)
                && Objects.equals(this.name, ruleset.name)
                && Objects.equals(this.creation_date, ruleset.creation_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.creation_date);
    }

    @Override
    public String toString() {
        return "Ruleset{" + "id=" + this.id + ", name=" + this.name + ", creation date=" + this.creation_date + "}";
    }
}
