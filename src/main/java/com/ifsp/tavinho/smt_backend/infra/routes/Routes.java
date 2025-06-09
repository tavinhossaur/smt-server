package com.ifsp.tavinho.smt_backend.infra.routes;

public interface Routes {

    String API                  = "/api";
    String V1                   = "/v1";

    String BY_ID                = "/{id}";
    
    String CLASSROOMS           = "/classrooms";
    String COURSES              = "/courses";
    String DISCIPLINES          = "/disciplines";
    String EVENTS               = "/events";
    String PROFESSORS           = "/professors";
    String USERS                = "/users";
    String FAVORITES            = "/favorites";
    String PASSWORD             = "/password";
    String PHOTO                = "/photo";

    String LOGIN                = "/login";
    
    String ADMIN                = "/admin";
    String PROFILE              = "/profile";
    String DASHBOARD            = "/dashboard";

    String BASE_API_ROUTE       = API + V1;

    String ADMIN_ROUTE          = BASE_API_ROUTE + ADMIN;
    String PROFILE_ROUTE        = BASE_API_ROUTE + PROFILE;
    String DASHBOARD_ROUTE      = BASE_API_ROUTE + DASHBOARD;

    String ADMIN_CLASSROOMS     = ADMIN_ROUTE + CLASSROOMS;
    String ADMIN_COURSES        = ADMIN_ROUTE + COURSES;
    String ADMIN_DISCIPLINES    = ADMIN_ROUTE + DISCIPLINES;
    String ADMIN_EVENTS         = ADMIN_ROUTE + EVENTS;
    String ADMIN_PROFESSORS     = ADMIN_ROUTE + PROFESSORS;
    String ADMIN_USERS          = ADMIN_ROUTE + USERS;

    String PROFILE_FAVORITES    = BY_ID + FAVORITES;
    String PROFILE_PASSWORD     = BY_ID + PASSWORD;
    String PROFILE_PHOTO        = BY_ID + PHOTO;

}
