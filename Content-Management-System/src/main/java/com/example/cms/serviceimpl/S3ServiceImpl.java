package com.example.cms.serviceimpl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.cms.service.S3Service;
import com.example.cms.utility.ResponseStructure;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
@Service
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;
    private final ResponseStructure<String> responseStructure;

    private final String bucketName;

    // Constructor injection with @Value
    public S3ServiceImpl(S3Client s3Client, ResponseStructure<String> responseStructure,
                         @Value("${spring.aws.bucket.name}") String bucketName) {
        this.s3Client = s3Client;
        this.responseStructure = responseStructure;
        this.bucketName = bucketName;
    }

    @Override
    public ResponseEntity<ResponseStructure<String>> uploadFile(MultipartFile file) throws IOException {
        String keyName = file.getOriginalFilename();

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(keyName)
                        .contentType(file.getContentType())
                        .build(),
                RequestBody.fromBytes(file.getBytes())
        );

        return ResponseEntity.ok(responseStructure
                .setStatusCode(HttpStatus.OK.value())
                .setMessage("File uploaded successfully to S3 bucket")
                .setData(keyName));
    }

    @Override
    public ResponseEntity<byte[]> downloadFile(String key) {
        ResponseBytes<GetObjectResponse> objectAsBytes = s3Client.getObjectAsBytes(
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build()
        );

        byte[] content = objectAsBytes.asByteArray();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + key + "\"")
                .body(content);
    }

    @Override
    public ResponseEntity<ResponseStructure<String>> deleteFile(String key) {
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build());

        return ResponseEntity.ok(responseStructure
                .setStatusCode(HttpStatus.OK.value())
                .setMessage("File deleted successfully from S3 bucket")
                .setData(key));
    }

    @Override
    public ResponseEntity<ResponseStructure<List<String>>> listFiles() {
        ListObjectsV2Response listResponse = s3Client.listObjectsV2(
                ListObjectsV2Request.builder().bucket(bucketName).build()
        );

        List<String> files = listResponse.contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());

        ResponseStructure<List<String>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Files fetched successfully!");
        response.setData(files);

        return ResponseEntity.ok(response);
    }
}
