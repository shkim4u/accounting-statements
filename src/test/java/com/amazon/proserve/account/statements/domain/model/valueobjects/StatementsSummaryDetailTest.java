package com.amazon.proserve.account.statements.domain.model.valueobjects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StatementsSummaryDetailTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getDebit() {
        StatementsSummaryDetail stmtSummaryDetail = new StatementsSummaryDetail();
        stmtSummaryDetail.getDebit();
        assertNotNull(stmtSummaryDetail);
    }

    @Test
    void getCredit() {
        StatementsSummaryDetail stmtSummaryDetail = new StatementsSummaryDetail();
        stmtSummaryDetail.getCredit();
        assertNotNull(stmtSummaryDetail);
    }

    @Test
    void setDebit() {
        StatementsSummaryDetail stmtSummaryDetail = new StatementsSummaryDetail();
        stmtSummaryDetail.setDebit(0.f);
        assertNotNull(stmtSummaryDetail);
    }

    @Test
    void setCredit() {
        StatementsSummaryDetail stmtSummaryDetail = new StatementsSummaryDetail();
        stmtSummaryDetail.setCredit(0.f);
        assertNotNull(stmtSummaryDetail);
    }
}