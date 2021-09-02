package com.amazon.proserve.account.statements.shareddomain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SharedDomainObjectTest {

    @Test
    public void testStatementDetailFromLedgerObject() {
        StatementDetailFromLedger obj = new StatementDetailFromLedger();
        assertNotNull(obj);
    }

    @Test
    public void testStatementsFromLedgerObject() {
        StatementsFromLedger obj = new StatementsFromLedger();
        assertNotNull(obj);
    }

    @Test
    public void testStatementFromLedgerObject() {
        StatementFromLedger obj = new StatementFromLedger();
        assertNotNull(obj);
    }
}