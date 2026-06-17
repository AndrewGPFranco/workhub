DO
$$
    BEGIN
        IF to_regclass('public.subdomain_resource_mapping_backup') IS NOT NULL THEN
            UPDATE demands demand
            SET subdomain_id = backup.subdomain_id
            FROM subdomain_resource_mapping_backup backup
            WHERE backup.table_name = 'demands'
              AND backup.record_id = demand.id;

            UPDATE daily daily_record
            SET subdomain_id = backup.subdomain_id
            FROM subdomain_resource_mapping_backup backup
            WHERE backup.table_name = 'daily'
              AND backup.record_id = daily_record.id;

            UPDATE feedbacks feedback
            SET subdomain_id = backup.subdomain_id
            FROM subdomain_resource_mapping_backup backup
            WHERE backup.table_name = 'feedbacks'
              AND backup.record_id = feedback.id;
        END IF;
    END
$$;
