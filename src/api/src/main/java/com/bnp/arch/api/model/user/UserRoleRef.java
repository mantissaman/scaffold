package com.bnp.arch.api.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.relational.core.mapping.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleRef {
    @Column(value = "roleId")
    private int roleId;

}
