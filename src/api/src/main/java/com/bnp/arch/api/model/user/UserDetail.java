package com.bnp.arch.api.model.user;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetail {
    private User user;
    private List<UserRoleRef> roles;
    private List<UserAuthorization> auths;
}
