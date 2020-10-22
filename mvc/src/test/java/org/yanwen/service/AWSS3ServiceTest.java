package org.yanwen.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yanwen.ApplicationBootstrap;

import java.io.File;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= ApplicationBootstrap.class)
public class AWSS3ServiceTest {
    @Autowired
    private AWSS3Service awss3Service;

    @Autowired
    private AmazonS3 client;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testCreateBucket() {
        String bucketName = "wendy-home-bucket-2";
        Bucket bucket = awss3Service.createBucket(bucketName);
        Assert.assertNotNull(bucket);
    }

    @Test
    public void uploadFileTest() throws IOException {
        awss3Service.uploadFile(new File(" "));//behavior verification
        verify(client,times(1)).putObject(any(PutObjectRequest.class));
    }
}
