package com.fqfx.UserCenter.tools.entity;

/**
 * 错误码信息 用于枚举错误值
 *
 * @author noobug.com
 */
public class ErrorCode {

    private Integer code;

    private String msg;

    public ErrorCode() {
    }

    public ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static ErrorCode of(Integer code, String msg) {
        return new ErrorCode(code, msg);
    }
}
