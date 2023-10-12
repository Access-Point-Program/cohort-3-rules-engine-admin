-- CTRL ALT SHIFT H

CREATE TABLE ruleset (
    PRIMARY KEY id BIGSERIAL,
    name TEXT NOT NULL,
	creation_date TIMESTAMP NOT NULL
);

CREATE TYPE EventType AS ENUM('FORWARD', 'RIGHT', 'LEFT');

CREATE TABLE rule (
    id BIGSERIAL PRIMARY KEY,
    ruleset_id BIGSERIAL NOT NULL,
	FOREIGN KEY(ruleset_id) REFERENCES ruleset(id),
    priority DOUBLE PRECISION NOT NULL,
	event_type EventType NOT NULL
);

CREATE TYPE FactType AS ENUM('FRONT', 'RIGHT', 'LEFT', 'BEHIND');
CREATE TYPE ValueType AS ENUM('WALL', 'EMPTY', 'END');

CREATE TABLE condition (
    id BIGSERIAL PRIMARY KEY,
    rule_id BIGSERIAL NOT NULL,
	FOREIGN KEY(rule_id) REFERENCES rule(id),
	fact_type FactType NOT NULL,
	value_type ValueType NOT NULL
);