package com.accesspoint.rulesengine.entity;

import java.io.Serializable;
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
@Table(name = "condition")
public class Condition implements Serializable {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @Enumerated(EnumType.STRING)
    @ColumnTransformer(write = "?::FactType")
    private FactType fact_type;

    @Enumerated(EnumType.STRING)
    @ColumnTransformer(write = "?::ValueType")
    private ValueType value_type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rule_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Rule rule;
}