package kr.co.skudeview.domain.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FollowRequestDto {

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class CREATE {

        private String fromMemberNickname;

        private String toMemberNickname;

    }


}
