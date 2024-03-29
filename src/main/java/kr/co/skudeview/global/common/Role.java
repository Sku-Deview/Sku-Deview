package kr.co.skudeview.global.common;

import kr.co.skudeview.global.exception.NotFoundException;
import kr.co.skudeview.global.model.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Role {

    ROLE_ADMIN("관리자"),

    ROLE_MANAGER("매니저"),

    ROLE_USER("유저");

    String userRole;

    public static Role of(String userRole) {

        return Arrays.stream(Role.values())
                .filter(role -> role.toString().equalsIgnoreCase(userRole))
                .findAny().orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_MEMBER_ROLE_NOT_FOUND));
    }

}
