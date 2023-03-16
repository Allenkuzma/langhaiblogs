package cc.langhai.exception;

import cc.langhai.response.ResultResponse;
import cc.langhai.response.ReturnCode;
import cc.langhai.response.SystemReturnCode;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常捕捉
 *
 * @author langhai
 * @date 2022-11-22 20:52
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕捉的是自定义异常
     *
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public ResultResponse businessException(Exception exception){
        if(exception instanceof BusinessException){
            BusinessException businessException = (BusinessException) exception;
            ReturnCode returnCode = businessException.getReturnCode();
            return new ResultResponse(returnCode.getCode(), returnCode.getMessage(), null);
        }

        return new ResultResponse(SystemReturnCode.SYSTEM_UNKNOWN_00000.getCode(),
                SystemReturnCode.SYSTEM_UNKNOWN_00000.getMessage(), null);
    }

    /**
     * 捕捉的是参数校验异常
     *
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultResponse validException(Exception exception){
        if(exception instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) exception;
            return new ResultResponse(SystemReturnCode.SYSTEM_UNKNOWN_00000.getCode(),
                    validException.getBindingResult().getAllErrors().get(0).getDefaultMessage(), null);
        }

        return new ResultResponse(SystemReturnCode.SYSTEM_UNKNOWN_00000.getCode(),
                SystemReturnCode.SYSTEM_UNKNOWN_00000.getMessage(), null);
    }

    /**
     * 捕捉的是权限不足异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = AuthException.class)
    public ModelAndView authException(Exception exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/error");
        if(exception instanceof AuthException){
            AuthException authException = (AuthException) exception;
            ReturnCode returnCode = authException.getReturnCode();
            // 添加错误详细信息
            modelAndView.addObject("status", returnCode.getCode());
            modelAndView.addObject("message", returnCode.getMessage());
            return modelAndView;
        }
        // 添加错误详细信息
        modelAndView.addObject("status", SystemReturnCode.SYSTEM_UNKNOWN_00000.getCode());
        modelAndView.addObject("message", SystemReturnCode.SYSTEM_UNKNOWN_00000.getMessage());
        return modelAndView;
    }

    /**
     * 捕捉的是Exception异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView exception(Exception exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/error");
        // 添加错误详细信息
        modelAndView.addObject("status", SystemReturnCode.SYSTEM_UNKNOWN_00000.getCode());
        modelAndView.addObject("message", SystemReturnCode.SYSTEM_UNKNOWN_00000.getMessage());
        return modelAndView;
    }
}
