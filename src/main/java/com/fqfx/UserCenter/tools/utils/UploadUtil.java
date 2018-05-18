package com.fqfx.UserCenter.tools.utils;

import com.fqfx.UserCenter.constants.error.UploadError;
import com.fqfx.UserCenter.tools.entity.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Deprecated
public class UploadUtil {
    private final static String DEFAULT_SAVE_PATH = "uploads" + File.separator;
    private final static Long DEFAULT_MIN_SIZE = 0L;
    private final static Long DEFAULT_MAX_SIZE = 4L * 1024L * 1024L;
    private static AtomicInteger atomicIndex = new AtomicInteger(0);

    public static Result<String> checkAndSave(MultipartFile sourceFile, UploadLimit limit) {
        Assert.isTrue(limit.getMaxSize() == null || limit.getMaxSize() > 0, "Limit:MaxSize must >0");

        Long size = sourceFile.getSize();
        String fileName = sourceFile.getOriginalFilename();

        Long limitMinSize = Optional.ofNullable(limit.getMinSize()).orElse(DEFAULT_MIN_SIZE);
        if (size <= limitMinSize) {
            return Result.error(UploadError.TOO_SMALL);
        } else {
            Long limitMaxSize = Optional.ofNullable(limit.getMaxSize()).orElse(DEFAULT_MAX_SIZE);
            if (size > limitMaxSize) {
                return Result.error(UploadError.TOO_LARGE);
            } else {
                int idx = fileName.lastIndexOf('.');
                if (idx < 0) {
                    return Result.error(UploadError.NO_ALLOW_EXT);
                } else {
                    String ext = fileName.substring(idx + 1);
                    List<String> limitExts = Optional.ofNullable(limit.getAllowExt()).orElse(new ArrayList<String>());
                    for (String str : limitExts) {
                        if (str.toUpperCase().equals(ext.toUpperCase())) {
                            // 保存文件
                            String savePath = Optional.ofNullable(limit.getSavePath()).orElse(DEFAULT_SAVE_PATH);
                            File dir = new File(savePath);
                            dir = dir.getAbsoluteFile();

                            if (!dir.exists()) {
                                dir.mkdir();
                            }

                            String newFileName = String.format("%d_%04d.%s", Instant.now().toEpochMilli(), atomicIndex.getAndIncrement(), ext);
                            String targetPath = savePath + newFileName;

                            try {
                                File targetFile = new File(targetPath);
                                targetFile = targetFile.getAbsoluteFile();
                                sourceFile.transferTo(targetFile);

                                return Result.ok(targetPath);
                            } catch (IOException e) {
                                e.printStackTrace();
                                log.error("[上传文件]文件传输异常", e);
                                return Result.error(UploadError.IO_EXCEPTION);
                            }
                        }
                    }

                    return Result.error(UploadError.NO_ALLOW_EXT);
                }
            }
        }

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UploadLimit {
        private Long minSize;
        private Long maxSize;
        private String savePath;
        private List<String> allowExt;
    }
}
