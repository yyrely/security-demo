package com.security.service;

import com.security.entity.Users;

/**
 * @author Hu
 * @date 2019/3/8 14:55
 */

public interface UsersService {

    Users findUserByUsername(String username);

    void save(Users user);
}
