package com.enigma.loan.security.jwt;

import com.enigma.loan.dto.response.CommonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        log.error("Unauthorized error : " + accessDeniedException.getMessage());

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        CommonResponse<Object> errorResponse = CommonResponse.builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .message("Forbidden + " + accessDeniedException.getMessage())
                .build();

        final ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(response.getOutputStream(), errorResponse);
    };


}
