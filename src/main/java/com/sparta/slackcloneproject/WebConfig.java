package com.sparta.slackcloneproject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(WebSecurity web) {
        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable();
        // 서버에서 인증은 JWT로 인증하기 때문에 Session의 생성을 막습니다.
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);



        http.authorizeRequests()
                // .mvcMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .anyRequest()
                .permitAll()
        ;
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("http://127.0.0.1:3000");
        // configuration.addAllowedOrigin("http://127.0.0.1:3000");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.validateAllowCredentials();
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
