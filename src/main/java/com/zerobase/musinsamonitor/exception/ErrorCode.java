package com.zerobase.musinsamonitor.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    USER_NOT_FOUND(BAD_REQUEST, "사용자가 없습니다."),
    EMAIL_ALREADY_USED(BAD_REQUEST,"이미 사용 중인 이메일입니다."),
    EMAIL_NOT_FOUND(BAD_REQUEST, "존재하지 않는 Email 입니다."),
    PASSWORD_DO_NOT_MATCH(BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    NON_EXISTENT_BRAND(BAD_REQUEST, "존재하지 않는 브랜드입니다."),
    DO_NOT_SUPPORTED_CATEGORY(BAD_REQUEST,"지원하지 않는 카테고리입니다."),
    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),

    /* 500 Internal Server Error : 서버가 처리 방법을 모르는 상황이 발생. 서버는 아직 처리 방법을 알 수 없음.*/
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String description;

}
