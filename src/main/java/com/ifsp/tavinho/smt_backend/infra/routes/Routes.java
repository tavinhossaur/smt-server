package com.ifsp.tavinho.smt_backend.infra.routes;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Routes {

    public static final String API                  = "/api";
    public static final String V1                   = "/v1";

    public static final String BY_ID                = "/{id}";
    
    public static final String ADMIN                = "/admin";
    public static final String PROFILE              = "/profile";
    public static final String DASHBOARD            = "/dashboard";

    public static final String CLASSROOMS           = "/classrooms";
    public static final String COURSES              = "/courses";
    public static final String DISCIPLINES          = "/disciplines";
    public static final String EVENTS               = "/events";
    public static final String PROFESSORS           = "/professors";
    public static final String USERS                = "/users";
    public static final String FAVORITES            = "/favorites";
    public static final String PASSWORD             = "/password";
    public static final String PHOTO                = "/photo";

    public static final String LOGIN                = "/login";
    public static final String HEALTH               = "/health";

    public static final String BASE_API_ROUTE       = API + V1;

    public static final String ADMIN_ROUTE          = BASE_API_ROUTE + ADMIN;
    public static final String PROFILE_ROUTE        = BASE_API_ROUTE + PROFILE;
    public static final String DASHBOARD_ROUTE      = BASE_API_ROUTE + DASHBOARD;

    public static final String ADMIN_CLASSROOMS     = ADMIN_ROUTE + CLASSROOMS;
    public static final String ADMIN_COURSES        = ADMIN_ROUTE + COURSES;
    public static final String ADMIN_DISCIPLINES    = ADMIN_ROUTE + DISCIPLINES;
    public static final String ADMIN_EVENTS         = ADMIN_ROUTE + EVENTS;
    public static final String ADMIN_PROFESSORS     = ADMIN_ROUTE + PROFESSORS;
    public static final String ADMIN_USERS          = ADMIN_ROUTE + USERS;

    public static final String[] SWAGGER_ROUTES     = {"/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/favicon.ico"};
}
