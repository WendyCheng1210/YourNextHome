package org.yanwen.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;


@Service
public class AWSS3Service {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public AWSS3Service(@Autowired AmazonS3 amazonS3){
        this.amazonS3 = amazonS3;
    }

    public void uploadFile(File f) throws IOException{
        PutObjectRequest request = new PutObjectRequest(bucketName, f.getName(),f);
        amazonS3.putObject(request);
    }

    public String getUrl(String fileName){
        return amazonS3.getUrl(bucketName,fileName).toExternalForm();
    }

    public String getUrl(String bucketName, String fileName){
        LocalDateTime expiration = LocalDateTime.now().plusDays(1);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName,fileName);
        generatePresignedUrlRequest.withMethod(HttpMethod.GET);
        generatePresignedUrlRequest.withExpiration(Date.from(expiration.toInstant(ZoneOffset.UTC)));
        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    public Bucket createBucket(String bucketName) {
        Bucket bucket = null;
        if(!amazonS3.doesBucketExistV2(bucketName)) {
            bucket = amazonS3.createBucket(bucketName);
        } else {
            logger.info("bucket name: {} is not available."
                    + " Try again with a different Bucket name.", bucketName);
        }
        return bucket;
    }

    public String uploadFile(String bucketName, MultipartFile file) throws IOException{
        try{
            String uuid = UUID.randomUUID().toString();
            String originalFilename = file.getOriginalFilename();
            String newFileName = uuid + "." + Files.getFileExtension(originalFilename);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            amazonS3.putObject(bucketName, newFileName, file.getInputStream(), objectMetadata);
            logger.info(String.format("The file name =%s was uploaded to bucket %s", newFileName,bucketName));
            return newFileName;
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }
}

