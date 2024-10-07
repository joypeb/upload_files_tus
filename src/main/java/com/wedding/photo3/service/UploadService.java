package com.wedding.photo3.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.upload.UploadInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class UploadService {
    private final TusFileUploadService tusFileUploadService;

    @Value("${tus.upload.path}")
    private String tusDataPath;

    public UploadService(TusFileUploadService tusFileUploadService) {
        this.tusFileUploadService = tusFileUploadService;
    }

    public String tusUpload(HttpServletRequest request, HttpServletResponse response) {
        try {
            tusFileUploadService.process(request, response);

            String requestURI = request.getRequestURI();
            UploadInfo uploadInfo = tusFileUploadService.getUploadInfo(requestURI);

            if (uploadInfo != null) {
                if (!uploadInfo.isUploadInProgress()) {
                    File file = new File(tusDataPath, uploadInfo.getMetadata().get("name") + "_" + getVodName(uploadInfo.getFileName()));

                    try (InputStream inputStream = tusFileUploadService.getUploadedBytes(requestURI)) {
                        FileUtils.copyInputStreamToFile(inputStream, file);
                    }

                    tusFileUploadService.deleteUpload(requestURI);

                    return "success";
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "fail";
    }

    private String getVodName(String filename) {
        String[] split = filename.split("\\.");
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid + "." + split[split.length - 1];
    }
}
