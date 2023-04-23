package com.example.team_project;

import com.example.team_project.security.handler.AccessDeniedHandlerException;
import com.example.team_project.security.handler.AuthenticationEntryPointException;
import com.example.team_project.security.handler.AuthenticationSuccess;
import com.example.team_project.security.handler.Custom403Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationSuccess authenticationSuccess;
    private final UserDetailsService userDetailsService;
    private final AuthenticationEntryPointException authenticationEntryException;
    private final AccessDeniedHandlerException accessDeniedHandlerException;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 인증 or 인가에 대한 설정
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .sessionManagement().sessionFixation().none()
                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated();

        http.formLogin() // From 로그인 처리
                .loginPage("/user/login") // 사용자 정의 로그인 페이지
                .defaultSuccessUrl("/") // 로그인 성공 후 이동페이지
                .failureUrl("/login?error=true") // 로그인 실패 후 이동페이지
                .usernameParameter("userId") // 아이디 파라미터명
                .passwordParameter("password") // 비밀번호 파라미터명
                .loginProcessingUrl("/userr/login") // 로그인 Form Action Url
                .permitAll() // .loginPage() 의 경로는 인증 없이 접근 가능
        ;

        http.logout() // 로그아웃 처리
                .logoutUrl("/user/logout") // 로그아웃 처리 URL(기본이 post)
                .logoutSuccessUrl("/") // 로그아웃 성공 URL
                .invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID") // 로그아웃 성공 시 제거할 쿠키명
        ;

        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());

        http.rememberMe() // 사용자 저장
                .rememberMeParameter("idMaintain") // default 파라미터는 remember-me
                .tokenValiditySeconds(604800) // 7일로 설정(default 14일)
                .alwaysRemember(false)
                .userDetailsService(userDetailsService)
        ;

        http.sessionManagement()
                .maximumSessions(1) // -1 무제한
                .maxSessionsPreventsLogin(false) // true:로그인 제한 false(default):기존 세션 만료
                .expiredUrl("/member/login") // 세션 만료
        ;

        http.sessionManagement()
                .sessionFixation().changeSessionId() // default 세션 공격 보호
        ;

        http.exceptionHandling() // Exception 처리
                .authenticationEntryPoint(authenticationEntryException) // 인증 예외
                .accessDeniedHandler(accessDeniedHandlerException) // 인가 예외
        ;

        return http.build();
    }



    /**
     * 정적 파일 Security 적용 제외
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .antMatchers("/", "/img/**", "/lib/**", "/user/**");
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new Custom403Handler();
    }
}