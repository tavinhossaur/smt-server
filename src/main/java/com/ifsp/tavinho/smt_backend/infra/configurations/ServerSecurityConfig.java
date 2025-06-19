package com.ifsp.tavinho.smt_backend.infra.configurations;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ifsp.tavinho.smt_backend.infra.middlewares.AdminMiddleware;
import com.ifsp.tavinho.smt_backend.infra.middlewares.AuthenticationMiddleware;
import com.ifsp.tavinho.smt_backend.infra.middlewares.ProfileMiddleware;

import lombok.RequiredArgsConstructor;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.BASE_API_ROUTE;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.DASHBOARD_ROUTE;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.PROFILE_ROUTE;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.ADMIN_ROUTE;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ServerSecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationMiddleware authenticationMiddleware;
    private final AdminMiddleware adminMiddleware;
    private final ProfileMiddleware profileMiddleware;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(this.corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(requests -> requests
                .requestMatchers(BASE_API_ROUTE + "/**").permitAll()
                .requestMatchers(DASHBOARD_ROUTE + "/**").authenticated()
                .requestMatchers(PROFILE_ROUTE + "/**").authenticated()
                .requestMatchers(ADMIN_ROUTE + "/**").authenticated())
            .sessionManagement(management -> management
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(this.authenticationProvider)
            .addFilterBefore(this.authenticationMiddleware, UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(this.adminMiddleware, AuthenticationMiddleware.class)
            .addFilterAfter(this.profileMiddleware, AuthenticationMiddleware.class);
        
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("http://localhost:9000"));
        
        configuration.setAllowedMethods(Arrays.asList(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.PATCH.name(),
            HttpMethod.DELETE.name(),
            HttpMethod.OPTIONS.name()
        ));
        
        configuration.setAllowedHeaders(Arrays.asList(
            "Authorization",
            "Content-Type",
            "Accept",
            "Origin",
            "Access-Control-Request-Method",
            "Access-Control-Request-Headers"
        ));
        
        configuration.setExposedHeaders(Arrays.asList(
            "Authorization",
            "Content-Disposition"
        ));
        
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}
