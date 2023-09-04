package ssgssak.ssgpointappevent.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ssgssak.ssgpointappevent.global.common.exception.NotSignedUpMemberException;
import ssgssak.ssgpointappevent.global.common.exception.TokenExpiredException;
import ssgssak.ssgpointappevent.global.common.exception.TokenInvalidException;

import java.io.IOException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    /**
     * filter단에서 발생하는 에러는, 서블릿이 실행되기 전에 발생하므로 controller에서 잡지 못한다.
     * 따라서 Error를 처리할 filter를 만들어서 사용한다
     */


    //Filter단에서 발생하는 exception을 처리하는 필터
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException e) {
            logger.error("액세스 토큰이 만료되었습니다");
            setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, 401, "4000");
        } catch (TokenInvalidException e) {
            logger.error("액세스 토큰이 유효하지 않습니다");
            setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, 401, "4001");
        } catch (NotSignedUpMemberException e) {
            logger.error("등록되지 않은 사용자입니다");
            setErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, 403, "4002");
        }

    }

    //Error를 Json으로 바꿔서 클라이언트에 전달
    private void setErrorResponse(HttpServletResponse response,
                                  int status,
                                  int errorCode,
                                  String message) {
        // 직렬화 하기위한 object mapper
        ObjectMapper objectMapper = new ObjectMapper();
        // response의 status, contentType, errorCode, message 등을 설정
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse errorResponse = new ErrorResponse(errorCode, message);
        // response를 설정
        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 에러 메시지 설정
    public record ErrorResponse(int code, String message) {}

}
