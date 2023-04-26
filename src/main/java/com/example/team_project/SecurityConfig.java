package com.example.team_project;

import com.example.team_project.domain.domain.user.service.auth.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final UserLoginService userLoginService;

    private final UserDetailsService userDetailsService;


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 인증 or 인가에 대한 설정
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .formLogin().disable() // FormLogin 사용 X
                .httpBasic().disable() // httpBasic 사용 X
                .csrf().disable() // csrf 보안 사용 X
                .sessionManagement().sessionFixation().none()
                .and()
                .headers().frameOptions().disable()
                .and()

                // 세션 사용하지 않으므로 STATELESS로 설정
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()

                //== URL별 권한 관리 옵션 ==//
                .authorizeRequests()

                // 아이콘, css, js 관련
                // 기본 페이지, css, image, js 하위 폴더에 있는 자료들은 모두 접근 가능, h2-console에 접근 가능
                .antMatchers("/**").permitAll()
                .antMatchers("/","/css/**","/images/**","/js/**","/favicon.ico","/h2-console/**").permitAll()
                .antMatchers("/signup").permitAll() // 회원가입 접근 가능
                .anyRequest().authenticated() // 위의 경로 이외에는 모두 인증된 사용자만 접근 가능
                .and()
                //== 소셜 로그인 설정 ==//
                .oauth2Login();



        http.logout() // 로그아웃 처리
                .logoutUrl("/user/logout") // 로그아웃 처리 URL(기본이 post)
                .logoutSuccessUrl("/main") // 로그아웃 성공 URL
                .invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID") // 로그아웃 성공 시 제거할 쿠키명
        ;

        http.rememberMe() // 사용자 저장
                .rememberMeParameter("idMaintain") // default 파라미터는 remember-me
                .tokenValiditySeconds(604800) // 7일로 설정(default 14일)
                .alwaysRemember(false)
                .userDetailsService(userDetailsService)
        ;

        http.sessionManagement()
                .maximumSessions(1) // -1 무제한
                .maxSessionsPreventsLogin(false) // true:로그인 제한 false(default):기존 세션 만료
                .expiredUrl("/user/login") // 세션 만료
        ;

        http.sessionManagement()
                .sessionFixation().changeSessionId() // default 세션 공격 보호
        ;



        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * AuthenticationManager 설정 후 등록
     * PasswordEncoder를 사용하는 AuthenticationProvider 지정 (PasswordEncoder는 위에서 등록한 PasswordEncoder 사용)
     * FormLogin(기존 스프링 시큐리티 로그인)과 동일하게 DaoAuthenticationProvider 사용
     * UserDetailsService는 커스텀 LoginService로 등록
     * 또한, FormLogin과 동일하게 AuthenticationManager로는 구현체인 ProviderManager 사용(return ProviderManager)
     *
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userLoginService);
        return new ProviderManager(provider);
    }


    /**
     * CustomJsonUsernamePasswordAuthenticationFilter 빈 등록
     * 커스텀 필터를 사용하기 위해 만든 커스텀 필터를 Bean으로 등록
     * setAuthenticationManager(authenticationManager())로 위에서 등록한 AuthenticationManager(ProviderManager) 설정
     * 로그인 성공 시 호출할 handler, 실패 시 호출할 handler로 위에서 등록한 handler 설정
     */

}