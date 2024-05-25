package com.smatech.backendapiservice.service.api;


import com.smatech.backendapiservice.common.response.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentsUploadsService {
    CommonResponse storeFiles(MultipartFile[] files);
}
