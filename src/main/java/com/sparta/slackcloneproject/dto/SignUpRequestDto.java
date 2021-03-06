package com.sparta.slackcloneproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
public class SignUpRequestDto {
    @NotNull(message = "아이디는 공백이 될 수 없습니다.")
    @Pattern(regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?", message = "아이디는 이메일 형식이여야 합니다.")
    private String username;

    @NotNull(message = "비밀번호는 공백이 될 수 없습니다.")
    @Pattern(regexp ="^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{8,20}$", message = "비밀번호는 영문,숫자 8~20자리여야 합니다")
    private String password;

    @NotNull(message = "비밀번호체크는 공백이 될 수 없습니다.")
    @Pattern(regexp ="^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{8,20}$", message = "비밀번호체크는 영문,숫자 8~20자리여야 합니다")
    private String passwordCheck;

    @NotNull(message = "닉네임은 공백이 될 수 없습니다.")
    @Pattern(regexp = "^[0-9A-Za-z가-힣]{4,20}$", message = "닉네임은 한글,영문,숫자 4~20자리여야 합니다.")
    private String nickname;

    private String iconUrl;

}
