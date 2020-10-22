package org.yanwen.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yanwen.model.User;
import org.yanwen.service.JWTService;
import org.yanwen.service.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;
    @Autowired private JWTService jwtService;

    private String errorMsg = "The email or password is not correct.";
    private String tokenKeyword = "Authorization";
    private String tokenType = "Bearer";

//    http://localhost:8080/auth
//    {
//        "email":"wendy@gmail.com",
//            "password":"123456789"
//    }
    @RequestMapping(value = "",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public String userLogin(@RequestBody User user){
        logger.debug(("username is" + user.getEmail() + "password is" + user.getPassword()));
        try{
            User u = userService.getUserByCredentials(user.getEmail(), user.getPassword());
            return jwtService.generateToken(u);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
