package kr.co.skudeview.infra.exception;

import kr.co.skudeview.infra.model.ResponseStatus;

public class WrongPasswordException extends BusinessLogicException {

    public WrongPasswordException(ResponseStatus responseStatus) {
        super(responseStatus);
    }

    public WrongPasswordException(String message) {
        super(message);
    }
}