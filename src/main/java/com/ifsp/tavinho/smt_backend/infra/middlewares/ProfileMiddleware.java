package com.ifsp.tavinho.smt_backend.infra.middlewares;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ifsp.tavinho.smt_backend.domain.entities.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.PROFILE_ROUTE;

@Component
public class ProfileMiddleware extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().startsWith(PROFILE_ROUTE);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if (authentication.getPrincipal() instanceof User user) {
            String id = request.getParameter("id");

            if (id.isBlank() || id == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("User id is required.");
                return;
            }

            if (user.getId().equals(request.getParameter("id"))) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("You are not allowed to modify this user.");
    }
}
