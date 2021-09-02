INSERT INTO statements(statement_from, statement_to, total_debit, total_credit) VALUES ('20210630', '20210728', 50.00, 50.00);

INSERT INTO statement(statements_id, journal_id) VALUES (1, '20210727-0003');

INSERT INTO statement_detail(statement_id, line, account_code, account_name, debit, credit)
VALUES(
        (
            SELECT MAX(statement_id)
            FROM statement
        ),
        1,
        1000,
        'Cash',
        0,
        35.00
      );

INSERT INTO statement_detail(statement_id, line, account_code, account_name, debit, credit)
VALUES(
          (
              SELECT MAX(statement_id)
              FROM statement
          ),
          2,
          1000,
          'Cash',
          35.00,
          0
      );

INSERT INTO statement(statements_id, journal_id) VALUES (1, '20210727-0005');

INSERT INTO statement_detail(statement_id, line, account_code, account_name, debit, credit)
VALUES(
          (
              SELECT MAX(statement_id)
              FROM statement
          ),
          1,
          3420,
          'Expenses - ICT - Broadband',
          0,
          15.00
      );

INSERT INTO statement_detail(statement_id, line, account_code, account_name, debit, credit)
VALUES(
          (
              SELECT MAX(statement_id)
              FROM statement
          ),
          2,
          3420,
          'Expenses - ICT - Broadband',
          15.00,
          0
      );
