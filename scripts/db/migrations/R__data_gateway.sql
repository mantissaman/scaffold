drop function if exists data_gateway(
    varchar,
    varchar,   
    jsonb
);

create or replace
function data_gateway(
    in_query varchar,
    in_user_email varchar,
    in_query_params jsonb
) returns JSONB
language plpgsql
as $function$

declare 
	out_json jsonb;
begin
	out_json := '[]';
    if in_query in ('get-generic-data', 'upsert-generic-data', 'delete-generic-data') then 
        select * into out_json from manage_generic(in_query, in_user_email, in_query_params);
    end if;

return out_json;
end;

$function$