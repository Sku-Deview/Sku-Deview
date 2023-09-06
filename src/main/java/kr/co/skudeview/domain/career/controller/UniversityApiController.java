package kr.co.skudeview.domain.career.controller;

import kr.co.skudeview.domain.career.dto.UniversityDto;
import kr.co.skudeview.domain.career.service.UniversityService;
import kr.co.skudeview.global.model.ResponseFormat;
import kr.co.skudeview.global.model.ResponseStatus;
import kr.co.skudeview.global.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UniversityApiController {

    private final UniversityService universityService;

    @PostMapping("/university")
    public ResponseFormat<Void> createUniversity(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                 @RequestBody UniversityDto universityDto) {
        universityService.createUniversity(userDetails.getUsername(), universityDto);
        return ResponseFormat.success(kr.co.skudeview.global.model.ResponseStatus.SUCCESS_CREATE);
    }


    @GetMapping("/university")
    public ResponseFormat<UniversityDto> getUniversity(@RequestBody UniversityDto universityDto) {
        return ResponseFormat.successWithData(kr.co.skudeview.global.model.ResponseStatus.SUCCESS_OK, universityService.getUniversityMajor(universityDto));
    }


    @PutMapping("/university/{memberId}")
    public ResponseFormat<Void> updateUniversity(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                 @RequestBody UniversityDto universityDto) {
        universityService.updateUniversity(userDetails.getUsername(), universityDto);

        return ResponseFormat.success(kr.co.skudeview.global.model.ResponseStatus.SUCCESS_NO_CONTENT);
    }

    @DeleteMapping("/university/{memberId}")
    public ResponseFormat<Void> deleteUniversity(@AuthenticationPrincipal CustomUserDetails userDetails) {
        universityService.deleteUniversity(userDetails.getUsername());
        return ResponseFormat.success(ResponseStatus.SUCCESS_NO_CONTENT);
    }

}
