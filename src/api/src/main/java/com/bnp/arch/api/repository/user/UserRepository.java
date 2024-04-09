package com.bnp.arch.api.repository.user;

import java.util.List;

import com.bnp.arch.api.model.user.User;
import com.bnp.arch.api.model.user.UserAuthorization;
import com.bnp.arch.api.model.user.UserRoleRef;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
        @Query("select a.id, a.first_name, a.last_name, a.email, a.password, a.status_id, " +
        "coalesce(a.created_by_id, 0) created_by_id, " + 
        "coalesce(a.updated_by_id, 0) updated_by_id,  to_char(a.created_at, 'DD-MON-YYYY') created_at, " + 
        "to_char(a.updated_at, 'DD-MON-YYYY') updated_at,  to_char(a.last_login_at, 'DD-MON-YYYY') last_login_at " +
        "from users a where a.email =:email ")
        User getUserRecordByEmail(@Param("email") String email);

        @Query("select role_id as roleId from user_authorizations where user_id = :userId")
        List<UserRoleRef> getUserAuthByEmail(@Param("userId") int userId);

        @Query("select a.id, " +
                "b.user_id userId, " +
                "b.role_id roleId, " +
                "a.name roleName, " +
                "a.app_name app " +
                "from user_roles a " +
                "inner join user_authorizations b on a.id = b.role_id and b.user_id = :userId " +
                "order by a.name "
        )
        List<UserAuthorization> getUserAuthorizations(@Param("userId") int userId);

        @Query("select upsert_user(:id, :email, :firstname, :lastname, :statusId, :lUserId, :secret )")
        Integer upsertUser(@Param("id") int id, @Param("email") String email, @Param("firstname") String firstName,
                        @Param("lastname") String lastName, @Param("statusId") int statusId,
                        @Param("lUserId") int lUserId, @Param("secret") String secret);

        @Query(" select upsert_user_auth(:operation, :userid, :roleid)")
        String upsertUserAuth(@Param("operation") String operation, @Param("userid") int userid,
                        @Param("roleid") int roleId);

        @Modifying
        @Query("update users set password = :password, status_id=:statusId where email = :email  ")
        void setNewPassword(@Param("password") String password, @Param("statusId") int statusId,
                        @Param("email") String email);


        @Modifying
        @Query("update users set password = :password where email = :email  ")
        void updatePassword(@Param("password") String password, @Param("email") String email);



}
