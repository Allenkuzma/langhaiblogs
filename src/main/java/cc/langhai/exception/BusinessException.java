package cc.langhai.exception;

import cc.langhai.response.ReturnCode;

/**
 * 自定义异常
 *
 * @author langhai
 * @date 2022-11-22 20:49
 */
public class BusinessException extends RuntimeException{

    private ReturnCode returnCode;

    public BusinessException(ReturnCode returnCode){
        super();
        this.returnCode = returnCode;
    }

    public ReturnCode getReturnCode() {
        return returnCode;
    }
}
