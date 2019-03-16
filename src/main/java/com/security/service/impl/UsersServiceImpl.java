package com.security.service.impl;

import com.security.entity.Users;
import com.security.mapper.UsersMapper;
import com.security.service.UsersService;
import org.springframework.stereotype.Service;

/**
 * @author Hu
 * @date 2019/3/8 14:55
 */

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersMapper usersMapper;

    public UsersServiceImpl(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    @Override
    public Users findUserByUsername(String username) {
        return usersMapper.findUserByUsername(username);
    }

    @Override
    public void save(Users user) {
        usersMapper.save(user);
    }
}
