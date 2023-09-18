package ssgssak.ssgpointappevent.global.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ssgssak.ssgpointappevent.global.common.exception.TokenExpiredException;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); // 토큰을 받아오는 필드는, request 헤더에서 "Authorization"임
        final String jwt;  // header에서 가져올 jwt 토큰값임. jwt 토큰값은 Header, Payload, Signature가 '.' 으로 구분된 문자열 값임
        final String uuid; // uuid를 토큰에서 가져올것임

        // preflight request라면 필터를 거치지 않도록 함
        if (request.getMethod().equals(HttpMethod.OPTIONS.name()) &&
            request.getHeader("Access-Control-Request-Method") != null &&
            request.getHeader("Origin") != null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 헤더가 null이거나, "Bearer"로 시작하지 않는다면 토큰 인증을 진행하지 않음
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.info("토큰이 없습니다 : " + authHeader);
            throw new TokenExpiredException();
        }

        // 토큰 인증 진행
        jwt = authHeader.substring(7); // "Bearer " 뒷부분이 모두 jwt 토큰임
        uuid = jwtTokenProvider.getUUID(jwt);    // 토큰에서 uuid를 추출

        // 만약 loginId가 null이 아니고, SecurityContextHolder에 authentication이 없고, 토큰이 유효성 검사를 통과했다면 authentication 생성
        if (uuid != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 여기서 credentials는 필요하다면 추가적으로 인증을 설정할 요소이다. credentials를 사용하지 않더라도 null을 입력해주어야한다
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(uuid, null, null);
            // 해당 ip에서 의심스러운 활동이 감지되면, 해당 ip의 모든 요청을 차단하도록 정책을 설정할 수 있음!
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // SecurityContextHolder에 authentication을 집어넣음으로써, 추후에 로그인한 유저의 정보에 접근할 수 있음
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        }
    }
}
