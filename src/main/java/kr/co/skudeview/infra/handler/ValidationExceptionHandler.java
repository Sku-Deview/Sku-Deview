package kr.co.skudeview.infra.handler;


import kr.co.skudeview.infra.model.ResponseErrorFormat;
import kr.co.skudeview.infra.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

//검증 예외(ValidationException)에 대한 처리를 담당하는 클래스
/*
스프링에선 스프링 예외를 미리 처리해둔 추상 클래스를 제공하는데 이 추상 클래스가ResponseEntityExceptionHandler
그래서, 위 예시에선 이것을 사용하지만 무엇인가 직관적이지 못하고, 내가 확실하게 제어를 하고 싶었기 떄문에,
수혁님이 말한것처럼 NotValidException이란 예외 핸들러 클래스를 커스텀해서 만든 것입니당
 */
@RequiredArgsConstructor
@Slf4j
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorFormat> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("-------HandleMethodArgumentNotValidException-------", e);

        final ResponseStatus responseStatus = ResponseStatus.FAIL_INVALID_PARAMETER;

        return handleExceptionInternal(e, responseStatus);
    }

    private ResponseEntity<ResponseErrorFormat> handleExceptionInternal(final BindException e,
                                                                        final ResponseStatus responseStatus) {

        return ResponseEntity
                .status(responseStatus.getStatusCode())
                .body(makeResponseErrorFormat(e, responseStatus));
    }

    private ResponseErrorFormat makeResponseErrorFormat(final BindException e,
                                                        final ResponseStatus responseStatus) {

        final List<ResponseErrorFormat.ValidationException> validationExceptions = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ResponseErrorFormat.ValidationException::of)
                .collect(Collectors.toList());

        return ResponseErrorFormat.builder()
                .message(responseStatus.getMessage())
                .statusCode(responseStatus.getStatusCode())
                .validationExceptions(validationExceptions)
                .build();
    }
}
