package kr.co.skudeview.domain.follow.controller;

import kr.co.skudeview.domain.follow.dto.FollowRequestDto;
import kr.co.skudeview.domain.follow.dto.FollowResponseDto;
import kr.co.skudeview.domain.follow.service.FollowService;
import kr.co.skudeview.domain.message.dto.MessageRequestDto;
import kr.co.skudeview.global.auth.CustomUserDetails;
import kr.co.skudeview.global.model.ResponseFormat;
import kr.co.skudeview.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FollowApiController {

    private final FollowService followService;

    @PostMapping("/follow")
    public ResponseFormat<FollowResponseDto.READ> createFollow(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                               @RequestBody FollowRequestDto.CREATE create) {

        FollowResponseDto.READ follow = followService.createFollow(userDetails.getUsername(), create);
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, follow);
    }


    // TODO : Following, Follower List READ API, DELETE API

}
