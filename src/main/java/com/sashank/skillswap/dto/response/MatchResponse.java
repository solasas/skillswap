package com.sashank.skillswap.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchResponse {
    private UserResponse user;
    private List<SkillResponse> theyCanTeachMe;
    private List<SkillResponse> iCanTeachThem;
    private boolean isMutual;
}

