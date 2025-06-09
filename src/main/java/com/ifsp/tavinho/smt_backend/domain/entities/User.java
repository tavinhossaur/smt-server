package com.ifsp.tavinho.smt_backend.domain.entities;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "users")
@Getter @Setter
public class User implements UserDetails {

    @Id
    private String id;
    private String username;
    private String email;
    private String password;

    @Field(name = "is_admin")
    private Boolean isAdmin = false;

    @Field(name = "profile_photo")
    private String profilePhoto;

    @CreatedDate
    @Field(name = "created_at")
    private Date createdAt;

    @LastModifiedDate
    @Field(name = "updated_at")
    private Date updatedAt;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

}
