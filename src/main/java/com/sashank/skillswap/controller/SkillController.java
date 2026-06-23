package com.sashank.skillswap.controller;

import com.sashank.skillswap.dto.request.CreateSkillRequest;
import com.sashank.skillswap.dto.response.SkillResponse;
import com.sashank.skillswap.enums.SkillCategory;
import com.sashank.skillswap.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @GetMapping
    public ResponseEntity<List<SkillResponse>> getAllSkills(
            @RequestParam(required = false) SkillCategory category) {
        List<SkillResponse> skills;
        if (category != null) {
            skills = skillService.getSkillsByCategory(category);
        } else {
            skills = skillService.getAllSkills();
        }
        return ResponseEntity.ok(skills);
    }

    @GetMapping("/categories")
    public ResponseEntity<SkillCategory[]> getCategories() {
        return ResponseEntity.ok(SkillCategory.values());
    }

    @PostMapping
    public ResponseEntity<SkillResponse> createSkill(
            @RequestAttribute("userId") Long userId,
            @Valid @RequestBody CreateSkillRequest request) {
        SkillResponse response = skillService.createSkill(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponse> getSkillById(@PathVariable Long id) {
        SkillResponse skill = skillService.getSkillById(id);
        return ResponseEntity.ok(skill);
    }
}

