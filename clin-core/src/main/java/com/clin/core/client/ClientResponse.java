package com.clin.core.client;

import java.io.Serializable;

/**
 * <pre>
 * Title :  处理微服务之间的调用返回结果
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-02-28
 **/
public class ClientResponse implements Serializable {

    private static final long serialVersionUID = 2271573785794685249L;
    private ResultCode code = ResultCode.SUCCESS;// 调用返回默认成功
    private String message;// 调用返回信息

    public ClientResponse(ResultCode code) {
        this.code = code;
    }

    public ClientResponse(ResultCode code, String message) {
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return code == ResultCode.SUCCESS;
    }

    public ResultCode getCode() {
        return code;
    }

    public void setCode(ResultCode code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
