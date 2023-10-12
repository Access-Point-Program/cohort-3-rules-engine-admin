CREATE TABLE ruleset (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
	creation_date TIMESTAMP NOT NULL
);
-- Manual insert
--INSERT INTO public.ruleset VALUES
--	(DEFAULT, 'Test2');