package com.bnp.arch.api.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private @Id int id;
    @Column(value = "first_name")
    private String firstName;
    @Column(value = "last_name")
    private String lastName;
    @Column(value = "email")
    private String email;
    @Column(value = "password")
    private String password;
    @Column(value = "status_id")
    private int statusId;
    @Column(value = "created_by_id")
    private int createdById;
    @Column(value = "updated_by_id")
    private int updatedById;
    @Column(value = "created_at")
    private String createdAt;
    @Column(value = "updated_at")
    private String updatedAt;
    @Column(value = "last_login_at")
    private String lastLoginAt;
    @Column(value = "roles")
    private String roles;
    @Column(value = "status")
    private String status;
    @Column(value = "mfa_enabled")
    private boolean mfaEnabled;
    @Column(value = "mfa_required")
    private boolean mfaRequired;
    @Column(value = "mfa_secret")
    private String mfaSecret;
    private String dataUri;
    

}
