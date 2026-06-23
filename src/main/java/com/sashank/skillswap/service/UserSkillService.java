package com.sashank.skillswap.service;

import com.sashank.skillswap.dto.request.AddUserSkillRequest;
import com.sashank.skillswap.dto.response.UserSkillResponse;
import java.util.List;

public interface UserSkillService {
    UserSkillResponse addUserSkill(Long userId, AddUserSkillRequest request);
    List<UserSkillResponse> getUserSkills(Long userId);
    void removeUserSkill(Long userId, Long userSkillId);
}

