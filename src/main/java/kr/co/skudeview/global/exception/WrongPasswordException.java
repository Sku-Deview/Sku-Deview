package kr.co.skudeview.global.exception;

import kr.co.skudeview.global.model.ResponseStatus;

public class WrongPasswordException extends BusinessLogicException {

    public WrongPasswordException(ResponseStatus responseStatus) {
        super(responseStatus);
    }

    public WrongPasswordException(String message) {
        super(message);
    }
}