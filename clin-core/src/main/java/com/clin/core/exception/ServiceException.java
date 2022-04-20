package com.clin.core.exception;

import com.clin.core.client.ResultCode;

/**
 * <pre>
 * Title :  业务服务的统一异常处理
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-02-28
 **/
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 2359767895161832954L;
    private final ResultCode resultCode;

    public ResultCode getResultCode() {
        return resultCode;
    }

    public ServiceException(String message) {
        super(message);
        this.resultCode = ResultCode.FAILURE;
    }

    public ServiceException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }

    public ServiceException(ResultCode resultCode, String msg) {
        super(msg);
        this.resultCode = resultCode;
    }

    public ServiceException(ResultCode resultCode, Throwable cause) {
        super(cause);
        this.resultCode = resultCode;
    }

    public ServiceException(String msg, Throwable cause) {
        super(msg, cause);
        this.resultCode = ResultCode.FAILURE;
    }

}
