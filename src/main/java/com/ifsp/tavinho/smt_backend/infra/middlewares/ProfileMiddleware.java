package com.ifsp.tavinho.smt_backend.infra.middlewares;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.enums.Status;
import com.ifsp.tavinho.smt_backend.shared.responses.ServerApiResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.PROFILE_ROUTE;

@Component
public class ProfileMiddleware extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return !request.getRequestURI().startsWith(PROFILE_ROUTE);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            ServerApiResponse<Void> apiResponse = ServerApiResponse.<Void>builder()
                .status(Status.ERROR)
                .message("Token is missing or invalid.")
                .build();

            response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
            response.getWriter().flush();
            return;
        }

        response.setContentType("application/json");

        if (authentication.getPrincipal() instanceof User user) {
            String id = user.getId();

            if (id.isBlank() || id == null) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);

                ServerApiResponse<Void> apiResponse = ServerApiResponse.<Void>builder()
                    .status(Status.ERROR)
                    .message("Profile not found.")
                    .build();

                response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
                response.getWriter().flush();
                return;
            }

            filterChain.doFilter(request, response);
            return;
        }

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ServerApiResponse<Void> apiResponse = ServerApiResponse.<Void>builder()
            .status(Status.ERROR)
            .message("You are not allowed to modify this user.")
            .build();

        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
        response.getWriter().flush();
    }
}
