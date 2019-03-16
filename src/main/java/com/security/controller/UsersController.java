package com.security.controller;

import com.security.entity.Users;
import com.security.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @author Hu
 * @date 2019/3/8 14:34
 */


@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/lala/{username}")
    public Object getUser(@PathVariable("username") String username) {
        return usersService.findUserByUsername(username);
    }

    @PostMapping("/register")
    public Object registerUser(@RequestBody Users registerUser){
        Users user = new Users();
        user.setUsername(registerUser.getUsername());
        // 记得注册的时候把密码加密一下
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.getPassword()));
        user.setRole("ROLE_USER");
        usersService.save(user);
        return null;
    }

    @GetMapping("/test/{username}")
    public Object test(@PathVariable("username") String username) {
        Users user = usersService.findUserByUsername(username);
        return "hello world :" + user.getUsername();
    }

}
