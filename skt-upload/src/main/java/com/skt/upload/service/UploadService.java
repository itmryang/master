package com.skt.upload.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    /**
     * 上传图片
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
