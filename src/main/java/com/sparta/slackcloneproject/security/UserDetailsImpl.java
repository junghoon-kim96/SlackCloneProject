package com.sparta.slackcloneproject.security;


import com.sparta.slackcloneproject.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    // isAccountNonExpired 만료되었니??
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 이 계정 잠겼니?
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 이 계정의 비밀번호가 너무 오래되었니?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 이 계정이 활성화 되어있니?
    @Override
    public boolean isEnabled() {
        return true;
    }
}
