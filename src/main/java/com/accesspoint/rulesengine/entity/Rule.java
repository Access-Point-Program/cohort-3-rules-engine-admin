package com.accesspoint.rulesengine.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Builder
@Entity
@Table(name = "rule")
public class Rule {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @OneToMany(mappedBy = "rule", fetch = FetchType.EAGER)
    private Set<Condition> Conditions = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "ruleset_id")
    private Ruleset ruleset;

    private double priority;

    private EventType event_type;
}