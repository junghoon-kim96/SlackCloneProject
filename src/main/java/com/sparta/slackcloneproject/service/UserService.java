package com.sparta.slackcloneproject.service;

import com.sparta.slackcloneproject.dto.ResponseDto;
import com.sparta.slackcloneproject.dto.SignUpRequestDto;
import com.sparta.slackcloneproject.model.User;
import com.sparta.slackcloneproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
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
        // 유효성검사는 requestDto에서 하고오니까 이제 할것은 유저 만들기
//      User user = new User(requestDto);
        // 비밀번호 암호화 해서 저장하기.
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        userRepository.save(new User(requestDto));
        return new ResponseDto<>(true, "회원가입 성공");
    }
}
