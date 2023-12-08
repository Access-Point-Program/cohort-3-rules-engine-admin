package com.accesspoint.rulesengine.entity;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Entity
@Table(name = "rule")
public class Rule implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private double priority;

    @Enumerated(EnumType.STRING)
    @ColumnTransformer(write = "?::EventType")
    @NotNull
    private EventType event_type;

    // Joins rule table to ruleset table through the ruleset_id
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruleset_id", nullable=false, updatable=false)
    @ColumnTransformer(write = "?::bigint")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Ruleset ruleset;

    // Joins rule table to condition table
    @OneToMany(mappedBy = "rule", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true )
    private List<Condition> conditions;

    // Right before the rule is saved to the database, the children conditions of the rule are populated
    @PrePersist
    private void prePersist() {
        conditions.forEach( c -> {
            c.setRule(this);
        });
    }

    public void addConditionToList(Condition condition) {
        conditions.add(condition);
        condition.setRule(this);
    }
    public void removeConditionFromList(Condition condition) {
        conditions.remove(condition);
        condition.setRule(this);
    }
}