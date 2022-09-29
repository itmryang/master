package com.skt.upload.service.Impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.skt.upload.service.UploadService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {
    private static final List<String> CONTENT_TYPES=
            Arrays.asList("image/jpeg","image/gif","image/png");
    private static final Logger LOGGER =
            LoggerFactory.getLogger(UploadServiceImpl.class);
    @Autowired
    private FastFileStorageClient storageClient;
        /**
         * 上传图片
         * @param file
         * @return
         */
        @Override
    public String upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        //检验文件类型
        String contentType = file.getContentType();
        //文件类型不合法，返回null
        if(!CONTENT_TYPES.contains(contentType)){
            LOGGER.info("文件类型不合法:{}",originalFilename);
            return null;
        }
        //检验文件内容
            try {
                BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
                if(bufferedImage == null){
                    LOGGER.info("文件内容不合法:{}",originalFilename);
                    return null;
                }
                //保存到服务器
                //file.transferTo(new File("E:\\project\\images\\"+randomName));
                String ext = StringUtils.substringAfterLast(originalFilename,".");
                StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(), ext, null);
                //成url地址，返回
                return "http://image.skt.com/"+storePath.getFullPath();
            } catch (IOException e) {
                LOGGER.info("服务器内部错误:{}",originalFilename);
                e.printStackTrace();
            }
        return null;
    }
}
