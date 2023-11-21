package com.springboot.securitytest.config;

import com.springboot.securitytest.JwtAuthenticationFilter;
import com.springboot.securitytest.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http.httpBasic(basic->basic.disable()) // UI를 사용하는 것을 기본값으로 가진 시큐리티 설정을 비활성화

                .csrf(csrf->csrf.disable())//

                .sessionManagement(sessionSetting->sessionSetting.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(f->f
                    .requestMatchers("/sign-api/sign-in","/sign-api/sign-up","/sign-api/exception").permitAll()
                    .requestMatchers(HttpMethod.GET,"/products/**").permitAll()
                    .requestMatchers("**exception**").permitAll()
                    .anyRequest().hasRole("ADMIN"))

                .exceptionHandling(eh->eh
                    .accessDeniedHandler(new CustomAccessDeniedHandler())// 인가 과정에서 통과하지 못한 예외 발생 시 전달
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())) // 인증 과정에서 예외 발생 시 전달

                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 인증 인가 과정을 거치지 않도록 처리한 설정 - swagger
     * @return
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers("/v2/api-docs","/swagger-resources/**","/swagger-ui.html"
        ,"/webjars/**","/swagger/**","/sign-api/exception");
    }
}
