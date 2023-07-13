package kr.co.skudeview.controller;

import jakarta.validation.Valid;
import kr.co.skudeview.infra.model.ResponseFormat;
import kr.co.skudeview.infra.model.ResponseStatus;
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
@RequestMapping("/api/v1")
public class SkillApiController {

    private final SkillService skillService;

    @PostMapping("/skill")
    public ResponseFormat<Void> createSkill(@RequestBody @Valid SkillRequestDto.CREATE create) {
        skillService.createSkill(create);
        return ResponseFormat.success(ResponseStatus.SUCCESS_CREATE);
    }

    @PutMapping("/skill")
    public ResponseFormat<Void> updateSkill(@RequestBody @Valid SkillRequestDto.UPDATE update) {
        skillService.updateSkill(update);
        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    @DeleteMapping("/skill/{skillId}")
    public ResponseFormat<Void> deleteSkill(@PathVariable(name = "skillId") Long skillId){
        skillService.deleteSkill(skillId);
        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    // 모든 skill 조회
    @GetMapping("/skill")
    public ResponseFormat<List<SkillResponseDto.READ>> getAllSkills() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, skillService.getAllSkills());
    }

}
