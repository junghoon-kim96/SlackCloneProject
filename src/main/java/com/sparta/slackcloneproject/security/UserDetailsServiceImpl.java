package com.sparta.slackcloneproject.security;

import com.sparta.slackcloneproject.model.User;
import com.sparta.slackcloneproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername까지 왔나?");
        User userEntity = userRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("등록되지 않은 유저 입니다.")
        );

        return new UserDetailsImpl(userEntity);
    }
}
