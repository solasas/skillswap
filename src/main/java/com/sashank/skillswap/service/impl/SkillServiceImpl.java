package com.sashank.skillswap.service.impl;

import com.sashank.skillswap.dto.request.CreateSkillRequest;
import com.sashank.skillswap.dto.response.SkillResponse;
import com.sashank.skillswap.entity.Skill;
import com.sashank.skillswap.enums.SkillCategory;
import com.sashank.skillswap.exception.AlreadyExistsException;
import com.sashank.skillswap.exception.ResourceNotFoundException;
import com.sashank.skillswap.repository.SkillRepository;
import com.sashank.skillswap.service.SkillService;
import com.sashank.skillswap.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private DtoMapper dtoMapper;

    @Override
    public SkillResponse createSkill(CreateSkillRequest request) {
        if (skillRepository.findByName(request.getName()).isPresent()) {
            throw new AlreadyExistsException("Skill with name '" + request.getName() + "' already exists");
        }

        Skill skill = Skill.builder()
                .name(request.getName())
                .category(request.getCategory())
                .description(request.getDescription())
                .build();

        skill = skillRepository.save(skill);
        return dtoMapper.toSkillResponse(skill);
    }

    @Override
    public List<SkillResponse> getAllSkills() {
        return skillRepository.findAll().stream()
                .map(dtoMapper::toSkillResponse)
                .toList();
    }

    @Override
    public List<SkillResponse> getSkillsByCategory(SkillCategory category) {
        return skillRepository.findByCategory(category).stream()
                .map(dtoMapper::toSkillResponse)
                .toList();
    }

    @Override
    public SkillResponse getSkillById(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));
        return dtoMapper.toSkillResponse(skill);
    }
}

