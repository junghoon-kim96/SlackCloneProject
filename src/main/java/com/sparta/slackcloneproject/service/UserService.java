package com.sparta.slackcloneproject.service;

import com.sparta.slackcloneproject.dto.LoginRequestDto;
import com.sparta.slackcloneproject.dto.ResponseDto;
import com.sparta.slackcloneproject.dto.SignUpRequestDto;
import com.sparta.slackcloneproject.dto.UserListDto;
import com.sparta.slackcloneproject.model.User;
import com.sparta.slackcloneproject.repository.UserRepository;
import com.sparta.slackcloneproject.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    public ResponseDto<?> signup(SignUpRequestDto requestDto) {
        // 중복체크
        if(userRepository.existsByUsername(requestDto.getUsername())) {
            throw new IllegalArgumentException("중복된 아이디입니다.");
        }
        if(userRepository.existsByNickname(requestDto.getNickname())) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }
        // 비밀번호 1 과 비밀번호 2가 같은지 확인하기.
        if(!requestDto.getPassword().equals(requestDto.getPasswordCheck())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호 암호화 해서 저장하기.
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        userRepository.save(new User(requestDto));
        return new ResponseDto<>(true, "회원가입 성공");
    }

    public ResponseDto<?> login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        );

        if (!passwordEncoder.matches(requestDto.getPassword(),user.getPassword())) {
            throw new IllegalArgumentException("비밀번호를 확인해 주세요");
        }
        UserListDto userinfo = new UserListDto(user.getId(), user.getUsername(), user.getNickname(), user.getIconUrl());
        jwtTokenProvider.createToken(requestDto.getUsername());
        return new ResponseDto<>(true, userinfo,"로그인 성공");
    }

    public ResponseDto<?> userinfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다."));

        return new ResponseDto<>(true, userId, user.getUsername(), user.getNickname(), user.getIconUrl());
    }
}
