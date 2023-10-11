package kr.co.skudeview.domain.follow.service;

import kr.co.skudeview.domain.follow.dto.FollowRequestDto;
import kr.co.skudeview.domain.follow.dto.FollowResponseDto;

import java.util.List;

public interface FollowService {

    FollowResponseDto.READ createFollow(String email, FollowRequestDto.CREATE create);

    // fromMember가 팔로잉 한 목록 화인
    List<FollowResponseDto.READ> getFollowings(String fromMemberNickname);

    // toMember로 팔로워 목록 확인
    List<FollowResponseDto.READ> getFollowers(String toMemberNickname);

    // 팔로잉 삭제 -> fromMemberNickname 팔로우했던 toMemberNickname 삭제
    Long deleteFollow(String fromMemberNickname, String toMemberNickname);

}
