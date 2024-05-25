package com.smatech.backendapiservice.controller;

import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.service.api.DocumentsUploadsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/api/documents")
@Slf4j
public class DocumentsRestController {
    DocumentsUploadsService documentsUploadsService;

    @PostMapping(value ="/uploadFiles", consumes={"multipart/form-data"})
    public CommonResponse uploadFile(@RequestParam("files") MultipartFile[] files) {
        log.info("Received Documents size-----: {}", files.length);
        return documentsUploadsService.storeFiles(files);
    }
}
