package com.accesspoint.rulesengine.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;
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
    private @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id ;

    @NotNull
    private String name;

    @CreationTimestamp
    @NotNull private Timestamp creation_date;

    @OneToMany(mappedBy = "ruleset", fetch = FetchType.EAGER)
    private Set<Rule> rules;
}