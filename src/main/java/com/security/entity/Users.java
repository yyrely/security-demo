package com.security.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hu
 * @date 2019/3/8 14:42
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    private Long id;

    private String username;

    private String password;

    private String role;

}
