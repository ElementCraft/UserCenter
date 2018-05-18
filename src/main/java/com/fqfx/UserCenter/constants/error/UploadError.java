package com.fqfx.UserCenter.constants.error;

import com.fqfx.UserCenter.tools.entity.ErrorCode;

public class UploadError {

    public static final ErrorCode TOO_LARGE = ErrorCode.of(1, "上传文件超过限制大小");
    public static final ErrorCode TOO_SMALL = ErrorCode.of(2, "上传文件不足最低大小");
    public static final ErrorCode NO_ALLOW_EXT = ErrorCode.of(3, "不允许上传该格式文件");
    public static final ErrorCode IO_EXCEPTION = ErrorCode.of(4, "文件传输异常，请重试");
}
