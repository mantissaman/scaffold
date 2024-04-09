package com.bnp.arch.api.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Value
@Table("user_roles")
public class UserRole {
    private @Id
    Long id;
    private String name;
}
