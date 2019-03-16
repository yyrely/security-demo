package com.security;

import java.io.IOException;

import com.security.config.security.SUser;
import com.security.entity.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void contextLoads() throws IOException {

        Users users = new Users();
        users.setUsername("111");
        users.setPassword("111");
        users.setRole("ROLE_ADMIN");
        SUser sUser = new SUser(users);

        String string = objectMapper.writeValueAsString(sUser);
        System.out.println(string);

        SUser sUser1 = objectMapper.readValue(string, SUser.class);
        System.out.println(sUser1);
    }

}
