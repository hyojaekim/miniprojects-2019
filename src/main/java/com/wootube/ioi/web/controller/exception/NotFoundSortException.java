package com.wootube.ioi.web.controller.exception;

public class NotFoundSortException extends RuntimeException {
    public NotFoundSortException() {
        super("존재하지 않는 정렬 방식 입니다.");
    }
}
