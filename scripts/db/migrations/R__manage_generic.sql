
drop function if exists manage_generic(
    varchar,
    varchar,   
    jsonb
);

create or replace
function manage_generic(
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
    if in_query = 'get-generic-data' then 
        select coalesce(json_agg(row_to_json(generic)), '[]') into out_json from generic;
    elsif in_query = 'upsert-generic-data' then
        with record as (
            select 
                coalesce((in_query_params->>'id')::int, 0) id,
                (in_query_params->>'generic_string')::varchar generic_string,
                (in_query_params->>'generic_int')::int generic_int,
                (in_query_params->>'generic_numeric')::numeric generic_numeric,
                (in_query_params->>'generic_bool')::bool generic_bool,
                (in_query_params->>'generic_tag')::varchar generic_tag,
                (in_query_params->>'generic_text')::text generic_text
        ),
        insert_record as (
            INSERT INTO generic
            (
                generic_string,
                generic_int,
                generic_numeric,
                generic_bool,
                generic_tag,
                generic_text
            )
            select
                generic_string,
                generic_int,
                generic_numeric,
                generic_bool,
                generic_tag,
                generic_text
            from record where id=0
            RETURNING *
        ),
        update_record as (
            UPDATE generic tgt
            set
                generic_string = src.generic_string,
                generic_int = src.generic_int,
                generic_numeric= src.generic_numeric,
                generic_bool= src.generic_bool,
                generic_tag = src.generic_tag,
                generic_text = src.generic_text
            from (select * from record where id>0)  src where src.id=tgt.id
            RETURNING tgt.*
        ),
        results as(
            select * from insert_record
            union all
            select * from update_record
        ),
		results_json as (
			SELECT jsonb_agg(t) recs FROM results t
		)
		SELECT coalesce(recs->0, null) into out_json FROM results_json where jsonb_array_length(recs)=1; 
    elsif in_query = 'delete-generic-data' then
        with results as(
            delete from generic where id =coalesce((in_query_params->>'id')::int, 0) returning *
        ),
		results_json as (
			SELECT jsonb_agg(t) recs FROM results t
		)
		SELECT coalesce(recs->0, null) into out_json FROM results_json where jsonb_array_length(recs)=1; 
    end if;

return out_json;
end;

$function$
