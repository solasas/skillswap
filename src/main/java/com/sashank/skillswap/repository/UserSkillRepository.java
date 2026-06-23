package com.sashank.skillswap.repository;

import com.sashank.skillswap.entity.UserSkill;
import com.sashank.skillswap.enums.SkillType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {
    List<UserSkill> findByUserIdAndType(Long userId, SkillType type);
    List<UserSkill> findByUserId(Long userId);
}

