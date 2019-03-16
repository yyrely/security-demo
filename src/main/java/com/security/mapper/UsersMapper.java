package com.security.mapper;

import com.security.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Hu
 * @date 2019/3/8 14:46
 */

@Mapper
public interface UsersMapper {

    /**
     * 根据用户名获取信息
     * @param username
     * @return
     */
    Users findUserByUsername(String username);

    void save(@Param("user") Users user);
}
