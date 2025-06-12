package com.ifsp.tavinho.smt_backend.infra.middlewares;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifsp.tavinho.smt_backend.domain.enums.Status;
import com.ifsp.tavinho.smt_backend.infra.services.AuthUserDetailsService;
import com.ifsp.tavinho.smt_backend.infra.services.JwtService;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.BASE_API_ROUTE;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.LOGIN;

@Component
@AllArgsConstructor
public class AuthenticationMiddleware extends OncePerRequestFilter {
    
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final AuthUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith(BASE_API_ROUTE + LOGIN) || request.getRequestURI().startsWith(BASE_API_ROUTE + "/register");
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .status(Status.ERROR)
                .message("Token is missing or invalid.")
                .build();

            response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
            response.getWriter().flush();
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            final String email = jwtService.extractEmail(jwt);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (email != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }

}
