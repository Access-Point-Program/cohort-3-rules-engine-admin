package com.accesspoint.rulesengine.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rule")
public class Rule {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @OneToMany(mappedBy = "rule", fetch = FetchType.EAGER)
    private Set<Condition> conditions = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "ruleset_id")
    private Ruleset ruleset;

    private double priority;

    @Enumerated(EnumType.STRING)
    @ColumnTransformer(write = "?::EventType")
    private EventType event_type;

//    public getConditionsList(Rule rule) {
//        return rule.getConditions()
//    }
}