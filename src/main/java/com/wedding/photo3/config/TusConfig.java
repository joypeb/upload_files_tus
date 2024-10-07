package com.wedding.photo3.config;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import me.desair.tus.server.TusFileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class TusConfig {

    @Value("${tus.upload.path}")
    private String tusDataPath;

    @Value("${tus.upload.expiration}")
    Long tusDataExpiration;

    private final ServletContext servletContext;

    public TusConfig(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Bean
    public TusFileUploadService tus() {
        return new TusFileUploadService()
                .withUploadUri(servletContext.getContextPath() + "/" + "upload")
                .withStoragePath(tusDataPath)
                .withDownloadFeature()
                .withUploadExpirationPeriod(tusDataExpiration)
                .withThreadLocalCache(true);
    }

    @PostConstruct
    private void createDirectoriesIfNotExist() {
        Path tusDataDirectory = Path.of(tusDataPath);
        Path locksDirectory = tusDataDirectory.resolve("locks");

        try {
            Files.createDirectories(tusDataDirectory);
            Files.createDirectories(locksDirectory);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create necessary directories for TUS server", e);
        }
    }
}
