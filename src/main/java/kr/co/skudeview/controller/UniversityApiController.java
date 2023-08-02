package kr.co.skudeview.controller;

import kr.co.skudeview.infra.model.ResponseFormat;
import kr.co.skudeview.infra.model.ResponseStatus;
import kr.co.skudeview.service.UniversityService;
import kr.co.skudeview.service.dto.request.UniversityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UniversityApiController {

    private final UniversityService universityService;

    @PostMapping("/university/{memberId}")
    public ResponseFormat<Void> createUniversity(@PathVariable final Long memberId, @RequestBody UniversityDto universityDto) {
        universityService.createUniversity(memberId, universityDto);
        return ResponseFormat.success(ResponseStatus.SUCCESS_CREATE);
    }


    @GetMapping("/university")
    public ResponseFormat<UniversityDto> getUniversity(@RequestBody UniversityDto universityDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, universityService.getUniversityMajor(universityDto));
    }


    @PutMapping("/university/{memberId}")
    public ResponseFormat<Void> updateUniversity(@PathVariable final Long memberId, @RequestBody UniversityDto universityDto) {
        universityService.updateUniversity(memberId, universityDto);
        return ResponseFormat.success(ResponseStatus.SUCCESS_NO_CONTENT);
    }

    @DeleteMapping("/university/{memberId}")
    public ResponseFormat<Void> deleteUniversity(@PathVariable final Long memberId) {
        universityService.deleteUniversity(memberId);
        return ResponseFormat.success(ResponseStatus.SUCCESS_NO_CONTENT);
    }

}
