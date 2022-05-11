package oldmoon.api.advice;

import oldmoon.api.enums.HttpCode;

/**
 * @Description 自定义业务异常
 * @Author hupg
 * @Date 2021/7/2 14:09
 */
public class BusinessException extends RuntimeException {

    private HttpCode httpStatus;

    BusinessException(HttpCode httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpCode getHttpStatus() {
        return httpStatus;
    }
}
