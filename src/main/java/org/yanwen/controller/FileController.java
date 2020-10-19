package org.yanwen.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.yanwen.service.AWSS3Service;

import java.io.IOException;

@RestController
@RequestMapping(value = {"/files"})
public class FileController {
    @Autowired
    private AWSS3Service awss3Service;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam("file") MultipartFile file){
        logger.info("test file name:" + file.getOriginalFilename());
        try{
            return awss3Service.uploadFile("wendy-home-bucket1",file);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getObject(@RequestParam("fileName") String s3Key){
        return awss3Service.getUrl(s3Key);
    }

}
