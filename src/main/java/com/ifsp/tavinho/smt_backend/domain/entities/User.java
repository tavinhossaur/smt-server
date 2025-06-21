package com.ifsp.tavinho.smt_backend.domain.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.ifsp.tavinho.smt_backend.domain.enums.Authorities;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "users")
@Getter @Setter
public class User extends BaseEntity implements UserDetails {

    private String fullName;
    private String email;
    private String enrollment;

    @JsonIgnore
    private String password;

    @Field(name = "profile_photo")
    @JsonIgnore
    private String profilePhoto;

    private List<Authorities> authorities = new ArrayList<>();

    public User(String fullName, String email, String enrollment, String password) {
        this.fullName = fullName;
        this.email = email;
        this.enrollment = enrollment;
        this.password = password;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.email;
    }

    public List<Authorities> getAuthoritiesList() {
        return this.authorities;
    }

}
