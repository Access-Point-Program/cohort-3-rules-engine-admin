package com.accesspoint.rulesengine.entity;

import java.io.Serializable;
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
@Table(name = "condition")
public class Condition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @ColumnTransformer(write = "?::FactType")
    @NotNull
    private FactType fact_type;

    @Enumerated(EnumType.STRING)
    @ColumnTransformer(write = "?::ValueType")
    @NotNull
    private ValueType value_type;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rule_id", nullable = false, updatable = false)
    @ColumnTransformer(write = "?::bigint")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Rule rule;
}