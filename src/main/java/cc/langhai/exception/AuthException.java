package cc.langhai.exception;

import cc.langhai.response.ReturnCode;

/**
 * 自定义权限异常
 *
 * @author langhai
 * @date 2023-02-16 18:16
 */
public class AuthException extends RuntimeException{

    private ReturnCode returnCode;

    public AuthException(ReturnCode returnCode){
        super();
        this.returnCode = returnCode;
    }

    public ReturnCode getReturnCode() {
        return returnCode;
    }
}
