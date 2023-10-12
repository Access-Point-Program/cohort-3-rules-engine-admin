package com.accesspoint.rulesengine;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ruleset")
public class Ruleset {

    @Getter @Setter private @Id Long id;

    @OneToMany(mappedBy = "ruleset")
    private Set<Rule> Rules = new HashSet<>();

    @Getter @Setter private String name;

    @CreationTimestamp
    @Getter @Setter private Timestamp creation_date;

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
        return "Ruleset{" + "id=" + this.id + ", name=" + this.name + ", creation date=" + this.creation_date;
    }
}
