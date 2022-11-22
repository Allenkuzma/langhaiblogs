package cc.langhai.exception;

import cc.langhai.response.ResultResponse;
import cc.langhai.response.ReturnCode;
import cc.langhai.response.SystemReturnCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕捉
 *
 * @author langhai
 * @date 2022-11-22 20:52
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultResponse exception(Exception exception){
        if(exception instanceof BusinessException){
            BusinessException businessException = (BusinessException) exception;
            ReturnCode returnCode = businessException.getReturnCode();
            return new ResultResponse(returnCode.getCode(), returnCode.getMessage(), null);
        }

        return new ResultResponse(SystemReturnCode.SYSTEM_UNKNOWN_00000.getCode(),
                SystemReturnCode.SYSTEM_UNKNOWN_00000.getMessage(), null);
    }
}
