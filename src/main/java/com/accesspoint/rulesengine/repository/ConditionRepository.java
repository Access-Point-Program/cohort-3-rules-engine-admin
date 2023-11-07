package com.accesspoint.rulesengine.repository;


import com.accesspoint.rulesengine.entity.Condition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConditionRepository extends JpaRepository<Condition, Long> {

}