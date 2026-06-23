package com.sashank.skillswap.dto.response;

import com.sashank.skillswap.enums.SkillLevel;
import com.sashank.skillswap.enums.SkillType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSkillResponse {
    private Long id;
    private SkillResponse skill;
    private SkillType type;
    private SkillLevel level;
}

