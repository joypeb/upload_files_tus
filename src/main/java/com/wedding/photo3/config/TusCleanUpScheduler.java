package com.wedding.photo3.config;

import me.desair.tus.server.TusFileUploadService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@EnableScheduling
public class TusCleanUpScheduler {

//    private final TusFileUploadService tusFileUploadService;
//
//    public TusCleanUpScheduler(TusFileUploadService tusFileUploadService) {
//        this.tusFileUploadService = tusFileUploadService;
//    }
//
//    @Scheduled(fixedDelayString = "PT12H")
//    public void cleanup() throws IOException {
//        tusFileUploadService.cleanup();
//    }
}
