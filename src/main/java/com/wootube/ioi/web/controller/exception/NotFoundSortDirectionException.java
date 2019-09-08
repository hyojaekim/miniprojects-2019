package com.wootube.ioi.web.controller.exception;

public class NotFoundSortDirectionException extends RuntimeException {
    public NotFoundSortDirectionException() {
        super("존재하지 않는 정렬 순서 입니다.");
    }
}
