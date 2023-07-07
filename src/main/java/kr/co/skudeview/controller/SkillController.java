package kr.co.skudeview.controller;

import jakarta.validation.Valid;
import kr.co.skudeview.service.SkillService;
import kr.co.skudeview.service.dto.request.SkillRequestDto;
import kr.co.skudeview.service.dto.response.SkillResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/skills")
public class SkillController {

    private final SkillService skillService;

    @PostMapping("/create")
    public ResponseEntity<Void> createSkill(@RequestBody @Valid SkillRequestDto.CREATE create) throws Exception {
        skillService.createSkill(create);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateSkill(@RequestBody @Valid SkillRequestDto.UPDATE update) throws Exception {
        skillService.updateSkill(update);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{skill_id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable(name = "skill_id") Long skillId) throws Exception {
        skillService.deleteSkill(skillId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // skillId 로 skill 단일 조회
    @GetMapping("/get")
    public ResponseEntity<SkillResponseDto.READ> getSkill(@RequestParam Long skillId) throws Exception {
        return new ResponseEntity<>(skillService.getSkillBySkillId(skillId), HttpStatus.OK);
    }

    // 모든 skill 조회
    @GetMapping("/all")
    public ResponseEntity<List<SkillResponseDto.READ>> getAllSkills() throws Exception {
        return new ResponseEntity<>(skillService.getAllSkills(), HttpStatus.OK);
    }

}
