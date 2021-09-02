package com.amazon.proserve.account.statements.domain.model.aggregates;

import com.amazon.proserve.account.statements.domain.model.entities.Statement;
import com.amazon.proserve.account.statements.domain.model.valueobjects.StatementDetail;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StatementsTest {

    @Test
    public void testObject() {
        Statements obj1 = new Statements();
        Statements obj2 = new Statements();
        assertNotNull(obj1);
        assertEquals(obj1, obj2);
    }

    @Test
    public void testObjectWithArgs() {

        List<Statement> statementList1 = Arrays.asList(new Statement(), new Statement());
        List<Statement> statementList2 = Arrays.asList(new Statement(), new Statement());

        Statements obj1 = new Statements(
                100,
                "20210630",
                "20210731",
                202.4f,
                101.7f,
                statementList1
        );
        Statements obj2 = new Statements(
                100,
                "20210630",
                "20210731",
                202.4f,
                101.7f,
                statementList2
        );
        assertNotNull(obj1);
        assertEquals(obj1, obj2);
    }

    @Test
    public void testNotEqualObjectWithArgs() {

        List<Statement> statementList1 = Arrays.asList(new Statement(), new Statement());
        List<Statement> statementList2 = Arrays.asList(new Statement(), new Statement(), new Statement());

        Statements obj1 = new Statements(
                100,
                "20210630",
                "20210731",
                202.4f,
                101.7f,
                statementList1
        );
        Statements obj2 = new Statements(
                100,
                "20210630",
                "20210731",
                202.4f,
                101.7f,
                statementList2
        );
        assertNotNull(obj1);
        assertNotEquals(obj1, obj2);
    }

    @Test
    public void testDifferentObject() {
        Statements obj1 = new Statements();
        Object obj2 = new StatementDetail();
        assertNotNull(obj1);
        assertNotEquals(obj1, obj2);
    }
}