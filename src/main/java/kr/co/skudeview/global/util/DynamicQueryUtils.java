package kr.co.skudeview.global.util;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class DynamicQueryUtils {

    public static <T> BooleanExpression filter(T condition, Function<T, BooleanExpression> function) {
        T temp = condition;

        if (temp instanceof String s && !StringUtils.hasText(s)) {
            temp = null;
        }

        if (temp instanceof List c && CollectionUtils.isEmpty(c)) {
            temp = null;
        }

        if (temp instanceof Set c && CollectionUtils.isEmpty(c)) {
            temp = null;
        }

        return Optional.ofNullable(temp)
                .map(function)
                .orElse(null);
    }

}
