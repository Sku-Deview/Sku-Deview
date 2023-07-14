package kr.co.skudeview.domain.enums;

import kr.co.skudeview.infra.exception.NotFoundException;
import kr.co.skudeview.infra.model.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Role {

    ADMIN("관리자"),

    MANAGER("매니저"),

    USER("유저");

    String role;

    public static Role of(String role) {
        return Arrays.stream(Role.values())
                .filter(type -> type.toString().equalsIgnoreCase(role))
                .findAny().orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_MEMBER_ROLE_NOT_FOUND));
    }
}
