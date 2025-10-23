package com.example.cms.service;

import java.io.IOException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import com.example.cms.utility.ResponseStructure;

public interface S3Service {

    ResponseEntity<ResponseStructure<String>> uploadFile(MultipartFile file) throws IOException;

    ResponseEntity<byte[]> downloadFile(String key);

    ResponseEntity<ResponseStructure<List<String>>> listFiles();

    ResponseEntity<ResponseStructure<String>> deleteFile(String key);
}
