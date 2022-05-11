package oldmoon.api.advice;

import oldmoon.api.enums.HttpCode;
import oldmoon.api.http.ResultObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeoutException;


/**
 * @Description 全局异常捕获处理
 * @Author hupg
 * @Date 2021/7/2 14:22
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandlerAdvice {
    Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultObject handlerBusinessException(BusinessException e) {
        logger.error("业务异常：{}", e.getMessage());
        e.printStackTrace();
        ResultObject resp = new ResultObject();
        resp.setResultCode(e.getHttpStatus().getCode());
        resp.setResultMsg(e.getMessage());
        resp.setResultInfo(e.toString());
        return resp;
    }

    @ExceptionHandler(TimeoutException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultObject handlerTimeoutException(TimeoutException e) {
        logger.error("超时异常：");
        e.printStackTrace();
        ResultObject resp = new ResultObject();
        resp.setResultCode(HttpCode.ERROR.getCode());
        resp.setResultMsg("超时异常，请联系管理员！");
        // 控制台写异常（使用try块，免去手动关闭流操作）
        try (StringWriter sw = new StringWriter();
             PrintWriter printWriter = new PrintWriter(sw, true)) {
            e.printStackTrace(printWriter);
            resp.setResultInfo(sw.toString());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return resp;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultObject handlerException(Exception e) {
        logger.error("未知异常：");
        e.printStackTrace();
        ResultObject resp = new ResultObject();
        resp.setResultCode(HttpCode.UNKNOWN_ERROR.getCode());
        resp.setResultMsg(HttpCode.UNKNOWN_ERROR.getMessage());
        resp.setResultInfo(e.toString());
        return resp;
    }

}
