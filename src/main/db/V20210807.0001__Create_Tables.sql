-- DROP TABLE IF EXISTS statements;
CREATE TABLE IF NOT EXISTS statements
(
    statements_id serial,
    statement_from varchar(30),
    statement_to varchar(30),
    total_debit float,
    total_credit float
);

-- DROP TABLE IF EXISTS statement;
CREATE TABLE IF NOT EXISTS statement
(
    statement_id serial,
    statements_id integer,
    journal_id varchar(30)
);

-- DROP TABLE IF EXISTS statement_detail;
CREATE TABLE IF NOT EXISTS statement_detail
(
    statement_detail_id serial,
    statement_id integer,
    line integer,
    account_code integer,
    account_name varchar(30),
    debit float,
    credit float
);
