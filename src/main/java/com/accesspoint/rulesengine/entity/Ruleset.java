package com.accesspoint.rulesengine.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ruleset")
public class Ruleset {
   private @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id ;

    @OneToMany(mappedBy = "ruleset", fetch = FetchType.EAGER)
    private Set<Rule> rules = new HashSet<>();

    @NotNull
    private String name;

    @CreationTimestamp
    @NotNull private Timestamp creation_date;

}
