package kr.co.skudeview.infra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseFormat<T>  {

    private boolean isSuccessful;

    private Optional<T> data;

    private String message;

    private HttpStatus statusCode;

    // Success - If the ResponseStatus is declared + return Only Message
    public static <T> ResponseFormat<T> success(ResponseStatus responseStatus) {
        return ResponseFormat.<T>builder()
                .isSuccessful(true)
                .data(Optional.empty())
                .message(responseStatus.getMessage())
                .statusCode(responseStatus.getStatusCode())
                .build();
    }

    // Success - If the ResponseStatus is Not declared + return Only Message
    public static <T> ResponseFormat<T> success(String message,
                                                HttpStatus httpStatus) {
        return ResponseFormat.<T>builder()
                .isSuccessful(true)
                .data(Optional.empty())
                .message(message)
                .statusCode(httpStatus)
                .build();
    }

    // Success - If the ResponseStatus is declared + return Message And Data
    public static <T> ResponseFormat<T> successWithData(ResponseStatus responseStatus,
                                                        T data) {
        return ResponseFormat.<T>builder()
                .isSuccessful(true)
                .data(Optional.ofNullable(data))
                .message(responseStatus.getMessage())
                .statusCode(responseStatus.getStatusCode())
                .build();
    }

    // Success - If the ResponseStatus is Not declared + return Message And Data
    public static <T> ResponseFormat<T> successWithData(String message,
                                                        HttpStatus httpStatus,
                                                        T data) {
        return ResponseFormat.<T>builder()
                .isSuccessful(true)
                .data(Optional.ofNullable(data))
                .message(message)
                .statusCode(httpStatus)
                .build();
    }

    // Failed - If the ResponseStatus is declared
    public static <T> ResponseFormat<T> error(ResponseStatus responseStatus) {
        return ResponseFormat.<T>builder()
                .isSuccessful(false)
                .data(Optional.empty())
                .message(responseStatus.getMessage())
                .statusCode(responseStatus.getStatusCode())
                .build();
    }

    // Failed - If the ResponseStatus is Not declared
    public static <T> ResponseFormat<T> error(String message,
                                                HttpStatus httpStatus) {
        return ResponseFormat.<T>builder()
                .isSuccessful(false)
                .data(Optional.empty())
                .message(message)
                .statusCode(httpStatus)
                .build();
    }

}
