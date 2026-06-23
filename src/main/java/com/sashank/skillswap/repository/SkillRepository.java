package com.sashank.skillswap.repository;

import com.sashank.skillswap.entity.Skill;
import com.sashank.skillswap.enums.SkillCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findByName(String name);
    List<Skill> findByCategory(SkillCategory category);
}

