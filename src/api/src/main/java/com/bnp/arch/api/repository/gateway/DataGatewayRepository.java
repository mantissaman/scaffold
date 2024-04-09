package com.bnp.arch.api.repository.gateway;


import com.bnp.arch.api.model.user.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
public interface DataGatewayRepository extends CrudRepository<User, Long> {
    @Query("select jsonb(data_gateway(in_query=> :inQuery, in_user_email=> :inUserEmail, in_query_params=> (:inQueryParams)::jsonb))")
    String dataGatewayCall(@Param("inQuery") String inQuery, @Param("inQueryParams") String inQueryParams, @Param("inUserEmail") String inUserEmail);
}
