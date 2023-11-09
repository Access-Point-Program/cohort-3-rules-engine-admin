package com.accesspoint.rulesengine.entity;

import java.io.Serializable;
import java.util.Set;
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

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    private double priority;

    @Enumerated(EnumType.STRING)
    @ColumnTransformer(write = "?::EventType")
    private EventType event_type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ruleset_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Ruleset ruleset;

    @OneToMany(mappedBy = "rule", fetch = FetchType.EAGER)
    private Set<Condition> conditions;
}