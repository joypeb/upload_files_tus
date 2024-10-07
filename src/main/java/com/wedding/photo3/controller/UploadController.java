package com.wedding.photo3.controller;

import com.wedding.photo3.service.UploadService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UploadController {
    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @RequestMapping(value = {"/upload", "/upload/**"},
            method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.HEAD, RequestMethod.DELETE})
    public ResponseEntity<String> tusUpload(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(uploadService.tusUpload(request, response));
    }
}
