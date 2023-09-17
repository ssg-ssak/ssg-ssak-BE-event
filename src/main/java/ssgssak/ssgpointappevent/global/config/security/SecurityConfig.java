package ssgssak.ssgpointappevent.global.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ssgssak.ssgpointappevent.global.config.jwt.JwtAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    /**
     * Security Config
     *   기존의 WebSecurityConfigurerAdapter를 extends받는 방식에서
     *   SecurityFilterChain을 Bean등록해서 사용하는 방식으로 바뀜
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Restful API를 사용하므로, csrf는 사용할 필요가 없다
                .csrf(csrf -> csrf.disable())
                // 토큰 방식을 사용하므로, 서버에서 session을 관리하지 않음. 따라서 STATELESS로 설정
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 인증 절차에 대한 설정을 시작 : 필터를 적용시키지 않을 url과, 적용시킬 url을 구분
                .authorizeHttpRequests(authorizeHttpRequest -> authorizeHttpRequest
                        .requestMatchers(org.springframework.web.cors.CorsUtils::isPreFlightRequest)
                        .permitAll()
                        .requestMatchers("/api/v1/auth/sign-up","/api/v1/auth/log-in", "/swagger-ui/**", "/swagger-resources/**", "/api-docs/**")
                        .permitAll() // 위의 url은 모두 filter를 거치지 않음
                        .anyRequest().authenticated()) // 위의 url을 제외한 모든 url은 필터를 거쳐야함
                // 폼 로그인 사용 안함
                .formLogin(formLogin -> formLogin.disable())
                // 토큰 유효성을 검사하고 토큰을 생성하는 필터를 추가. 필터 추가 순서를 꼭 지켜야한다.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // filter단에서 발생하는 에러를 처리할 ExceptionHandlerFilter를 OAuth2필터 앞에 추가한다
                .addFilterBefore(exceptionHandlerFilter, OAuth2AuthorizationRequestRedirectFilter.class);
        return http.build();
    }

    // security에서 cors설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true); // 쿠키사용 허용
        configuration.setAllowedOriginPatterns(List.of("*")); // 출처 허용
        configuration.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name())); // 메소드 허용
        configuration.setAllowedHeaders(List.of("*")); // 요청헤더 허용
        configuration.setExposedHeaders(List.of("*")); // 응답헤더 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
