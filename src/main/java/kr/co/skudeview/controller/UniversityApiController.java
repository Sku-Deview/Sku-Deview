package kr.co.skudeview.controller;

import kr.co.skudeview.infra.model.ResponseFormat;
import kr.co.skudeview.infra.model.ResponseStatus;
import kr.co.skudeview.service.UniversityService;
import kr.co.skudeview.service.dto.request.UniversityDto;
import kr.co.skudeview.service.model.custom.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
        return ResponseFormat.success(ResponseStatus.SUCCESS_CREATE);
    }


    @GetMapping("/university")
    public ResponseFormat<UniversityDto> getUniversity(@RequestBody UniversityDto universityDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, universityService.getUniversityMajor(universityDto));
    }


    @PutMapping("/university/{memberId}")
    public ResponseFormat<Void> updateUniversity(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                 @RequestBody UniversityDto universityDto) {
        universityService.updateUniversity(userDetails.getUsername(), universityDto);

        return ResponseFormat.success(ResponseStatus.SUCCESS_NO_CONTENT);
    }

    @DeleteMapping("/university/{memberId}")
    public ResponseFormat<Void> deleteUniversity(@AuthenticationPrincipal CustomUserDetails userDetails) {
        universityService.deleteUniversity(userDetails.getUsername());
        return ResponseFormat.success(ResponseStatus.SUCCESS_NO_CONTENT);
    }

}
