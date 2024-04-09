package com.bnp.arch.api.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Data
@AllArgsConstructor
@Builder
public class UserAuthorization {
    private @Id
    Long id;
    @Column(value = "userId")
    private int userId;
    @Column(value = "roleId")
    private int roleId;
    @Column(value = "roleName")
    private String roleName;
    @Column(value = "app")
    private String app;
}