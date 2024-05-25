package com.smatech.backendapiservice.service.impl;

import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.service.api.DocumentsUploadsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Slf4j
@Service
public class DocumentsUploadsServiceImpl implements DocumentsUploadsService {

    @Value("${file.upload-dir}")
    private Path fileStorageLocation;
    @Override
    public CommonResponse storeFiles(MultipartFile[] files) {
        log.info("in DocumentsUploadsServiceImpl received");
        for (MultipartFile file : files) {

            //Normalize file name
            String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String fileName = "";
            try {
                // Check if the file's name contains invalid characters
                if (originalFileName.contains("..")) {
                    return new CommonResponse().buildErrorResponse("Sorry! Filename contains invalid path sequence"
                            .concat(originalFileName));
                }
                String fileExtension = "";
                try {
                    fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                    if (!fileExtension.equalsIgnoreCase(".png")) {
                        return new CommonResponse().buildErrorResponse("Invalid image extension, upload a PNG image format");
                    }

                } catch (Exception e) {
                    log.info("Invalid file extension", e.getStackTrace());
                    return new CommonResponse().buildErrorResponse("Error! Invalid file extension" + e);
                }

                //Copy file to the target location (Replacing existing file with the same name)
                Path targetLocation = this.fileStorageLocation.resolve(fileName.trim().replace(" ", "_"));
                log.info("targeted location-----: {}", targetLocation);
                Files.copy(file.getInputStream(), targetLocation.resolve(originalFileName), StandardCopyOption.REPLACE_EXISTING);

            } catch (IOException e) {
                log.info("Could not upload file: " + fileName, e.getStackTrace());
                return new CommonResponse().buildErrorResponse("Could not upload file: " + fileName + ". Please try again!");
            }
        }
        return new CommonResponse().buildSuccessResponse("Files uploaded successfully");
    }
}
