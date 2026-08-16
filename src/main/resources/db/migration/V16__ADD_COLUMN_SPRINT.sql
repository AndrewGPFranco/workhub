ALTER TABLE demands
    ADD sprint VARCHAR(10);

UPDATE demands
SET sprint = CASE
                 WHEN created_at < CURRENT_DATE - INTERVAL '16 days' THEN 'PAST'
                 ELSE 'CURRENT'
    END;


ALTER TABLE demands
    ALTER COLUMN sprint SET NOT NULL;