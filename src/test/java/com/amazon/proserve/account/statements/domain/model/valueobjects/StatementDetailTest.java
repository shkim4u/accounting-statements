package com.amazon.proserve.account.statements.domain.model.valueobjects;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class StatementDetailTest {

    private final int STATEMENT_DETAIL_ID = 1001;
    private final int STATEMENT_ID = 100;
    private final int LINE = 5;
    private final int ACCOUNT_CODE = 2222;
    private final String ACCOUNT_NAME = "MockAccountName";
    private final float DEBIT = 15.1f;
    private final float CREDIT = 24.7f;

    @Mock
    private StatementDetail mocksStatementDetail;

    @Before
    public void setupMock() {

        mocksStatementDetail = mock(StatementDetail.class);
        when(mocksStatementDetail.getStatementDetailId()).thenReturn(STATEMENT_DETAIL_ID);
        when(mocksStatementDetail.getStatementId()).thenReturn(STATEMENT_ID);
        when(mocksStatementDetail.getLine()).thenReturn(LINE);
        when(mocksStatementDetail.getAccountCode()).thenReturn(ACCOUNT_CODE);
        when(mocksStatementDetail.getAccountName()).thenReturn(ACCOUNT_NAME);
        when(mocksStatementDetail.getDebit()).thenReturn(DEBIT);
        when(mocksStatementDetail.getCredit()).thenReturn(CREDIT);
    }

    @Test
    public void testObject() {
        StatementDetail obj1 = new StatementDetail();
        StatementDetail obj2 = new StatementDetail();
        assertNotNull(obj1);
        assertEquals(obj1, obj2);
    }

    @Test
    public void testObjectWithArgs() {
        StatementDetail obj1 = new StatementDetail(
                STATEMENT_DETAIL_ID,
                STATEMENT_ID,
                LINE,
                ACCOUNT_CODE,
                ACCOUNT_NAME,
                DEBIT,
                CREDIT);
        StatementDetail obj2 = new StatementDetail(
                STATEMENT_DETAIL_ID,
                STATEMENT_ID,
                LINE,
                ACCOUNT_CODE,
                ACCOUNT_NAME,
                DEBIT,
                CREDIT);
        assertNotNull(obj1);
        assertEquals(obj1, obj2);
    }

    @Test
    public void testDifferentObjectWithArgs() {
        StatementDetail obj = new StatementDetail(
                STATEMENT_DETAIL_ID,
                STATEMENT_ID,
                LINE,
                ACCOUNT_CODE,
                ACCOUNT_NAME,
                DEBIT,
                CREDIT);
        assertNotEquals(obj, mocksStatementDetail);
    }

    @Test
    public void testNotEqualObjectWithArgs() {
        StatementDetail obj = new StatementDetail(
                STATEMENT_DETAIL_ID,
                STATEMENT_ID,
                LINE,
                0,
                ACCOUNT_NAME,
                DEBIT,
                CREDIT);
        assertNotEquals(obj, mocksStatementDetail);
    }
}