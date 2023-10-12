package kr.co.skudeview.domain.follow.dto;

import kr.co.skudeview.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FollowResponseDto {

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class READ {

        private Member fromMember;

        private Member toMember;

        private int followingCnt;

        private int followerCnt;

    }
}
