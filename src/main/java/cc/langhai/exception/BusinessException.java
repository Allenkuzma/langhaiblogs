package cc.langhai.exception;

import cc.langhai.response.ReturnCode;

/**
 * 自定义异常
 *
 * @author langhai
 * @date 2022-11-22 20:49
 */
public class BusinessException extends RuntimeException {

    private Integer errCode;

    private String errMessage;

    private ReturnCode returnCode;

    public BusinessException(ReturnCode returnCode){
        super();
        this.returnCode = returnCode;
    }

    public BusinessException(Integer errCode, String errMessage) {
        super();
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public ReturnCode getReturnCode() {
        return returnCode;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }
}
