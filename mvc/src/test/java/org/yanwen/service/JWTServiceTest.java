package org.yanwen.service;
import io.jsonwebtoken.Claims;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yanwen.ApplicationBootstrap;
import org.yanwen.model.User;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= ApplicationBootstrap.class)
public class JWTServiceTest {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;

    @Test
    public void generateTokenTest(){
        User user = userService.getUserByEmail("wendy@gmail.com");
        String token = jwtService.generateToken(user);
        //assertion
        assertNotNull(token);
        String[] tArray = token.split("\\.");
        assertEquals(tArray.length, 3);
    }


    @Test
    public void decryptJwtTokenTest(){
        User user = userService.getByID(1L);
//        user.setId(1L);
//        user.setUser_name("wendycheng");
        String token = jwtService.generateToken(user);
        Claims c = jwtService.decryptJwtToken(token);

        Assert.assertNotNull(c);
        Assert.assertEquals(c.getSubject(),"wendycheng");
    }
}
