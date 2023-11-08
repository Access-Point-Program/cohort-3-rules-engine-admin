package com.accesspoint.rulesengine.entity;

import java.util.Objects;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Entity
public class Condition {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @ManyToOne
    @JoinColumn(name = "rule_id")
    private Rule rule;

    private FactType fact_type;

    private ValueType value_type;
}