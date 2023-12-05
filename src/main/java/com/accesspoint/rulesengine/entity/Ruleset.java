package com.accesspoint.rulesengine.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ruleset")
public class Ruleset implements Serializable {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @CreationTimestamp @NotNull
    private Timestamp creation_date;

    // Joins ruleset table to rule table
    @OneToMany(mappedBy = "ruleset", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Rule> rules;

    public void addRuleToList(Rule rule){
        rules.add(rule);
        rule.setRuleset(this);
    }

    public void removeRuleFromList(Rule rule){
        rules.remove(rule);
        rule.setRuleset(this);
    }

    @PrePersist
    private void prePersist() {
        rules.forEach( c -> c.setRuleset(this));
    }
}