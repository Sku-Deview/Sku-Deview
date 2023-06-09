package kr.co.skudeview.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Gender {

    MALE("남자"),

    FEMALE("여자");

    String gender;

    public static Gender of(String gender) throws Exception {
        return Arrays.stream(Gender.values())
                .filter(type -> type.toString().equalsIgnoreCase(gender))
                .findAny().orElseThrow(() -> new Exception(""));
    }
}
