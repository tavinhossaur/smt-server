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
import com.ifsp.tavinho.smt_backend.infra.services.JwtService;
import com.ifsp.tavinho.smt_backend.infra.services.UserDetailsLoader;
import com.ifsp.tavinho.smt_backend.shared.responses.ServerApiResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.BASE_API_ROUTE;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.SWAGGER_ROUTES;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.LOGIN;

@Component
@RequiredArgsConstructor
public class AuthenticationMiddleware extends OncePerRequestFilter {
    
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final UserDetailsLoader userDetailsService;
    private final JwtService jwtService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();

        if (uri.startsWith(BASE_API_ROUTE + LOGIN)) return true;

        for (String swaggerRoute : SWAGGER_ROUTES) {
            if (swaggerRoute.endsWith("/**")) {
                String prefix = swaggerRoute.substring(0, swaggerRoute.length() - 3);
                if (uri.startsWith(prefix)) return true;
            } else if (uri.equals(swaggerRoute)) return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
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
