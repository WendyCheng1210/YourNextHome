package org.yanwen.controller;

import com.amazonaws.auth.policy.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yanwen.service.AWSS3Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = {"/files"})
public class FileController {
    @Autowired
    private AWSS3Service awss3Service;

    private Logger logger = LoggerFactory.getLogger(getClass());


//    http://localhost:8080/files
//    Headers -> Authorization, Bearer:
//    Body: form data -> file -> upload file
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam("file") MultipartFile file){
        logger.info("test file name:" + file.getOriginalFilename());
        try{
            return awss3Service.uploadFile("wendy-home-bucket-1",file);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

//    @RequestMapping(value = "/{fileName}",method = RequestMethod.GET)
//    public String getObject(@PathVariable("fileName") String s3Key){
//        return awss3Service.getUrl(s3Key);
//    }

    @RequestMapping(value = "/{fileName}", method = RequestMethod.GET)
    public ResponseEntity<String> getFileUrl(@PathVariable String fileName, HttpServletRequest request){
        Resource resource = null;
        String msg = "The file doesn't exist.";
        ResponseEntity responseEntity;
        try{
            String url = awss3Service.getUrl(fileName);
            logger.debug(msg);
            responseEntity = ResponseEntity.status(HttpServletResponse.SC_OK).body(url);
        }catch (Exception ex){
            responseEntity = ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(ex.getMessage());
            logger.debug(ex.getMessage());
        }
        return responseEntity;
    }
}
