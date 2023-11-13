package com.accesspoint.rulesengine.repository;

import com.accesspoint.rulesengine.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule, Long> {

}