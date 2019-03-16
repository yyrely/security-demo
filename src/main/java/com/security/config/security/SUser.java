package com.security.config.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.security.entity.Users;

/**
 * @author Hu
 * @date 2019/3/8 15:07
 */

/**
 * security使用的User
 */
public class SUser implements UserDetails {

    private String username;

    private String password;

    private Collection<? extends  GrantedAuthority> authorities;

    public SUser(Users users) {
        this.username = users.getUsername();
        this.password = users.getPassword();
        this.authorities = Collections.singleton(new SimpleGrantedAuthority(users.getRole()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
