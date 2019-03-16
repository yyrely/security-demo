package com.security.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.entity.Users;
import com.security.mapper.UsersMapper;

/**
 * @author Hu
 * @date 2019/3/8 15:16
 *
 * 根据用户名查询用户
 */

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UsersMapper usersMapper;

    public UserDetailServiceImpl(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersMapper.findUserByUsername(username);
        return new SUser(users);
    }
}
