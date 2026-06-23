package com.sashank.skillswap.service;

import com.sashank.skillswap.dto.request.CreateSkillRequest;
import com.sashank.skillswap.dto.response.SkillResponse;
import com.sashank.skillswap.enums.SkillCategory;
import java.util.List;

public interface SkillService {
    SkillResponse createSkill(CreateSkillRequest request);
    List<SkillResponse> getAllSkills();
    List<SkillResponse> getSkillsByCategory(SkillCategory category);
    SkillResponse getSkillById(Long id);
}

