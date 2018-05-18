package com.fqfx.UserCenter.tools.entity;


import lombok.Data;

@Data
public class LayuiResult<T> extends Result<T> {

    private Integer count;

    public LayuiResult() {
    }

    public LayuiResult(Integer code, Boolean success, String msg, Integer count, T data) {
        super(code, success, msg, data);
        this.count = count;
    }

    public static <T> LayuiResult<T> ok(String msg, Integer count, T t) {
        return new LayuiResult<>(0, true, msg, count, t);
    }

    public static <T> LayuiResult<T> ok(Integer count, T t) {
        return LayuiResult.ok(DEFAULT_SUCCESS_MSG, count, t);
    }

    public static <T> LayuiResult<T> ok() {
        return LayuiResult.ok(0, null);
    }
}
