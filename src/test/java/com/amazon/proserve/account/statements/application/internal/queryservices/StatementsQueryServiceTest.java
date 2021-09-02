package com.amazon.proserve.account.statements.application.internal.queryservices;

import com.amazon.proserve.account.common.featuretoggle.Toggle;
import com.amazon.proserve.account.statements.application.internal.outboundservices.acl.ExternalLedgerService;
import com.amazon.proserve.account.statements.domain.model.aggregates.Statements;
import com.amazon.proserve.account.statements.domain.model.queries.GetStatementsExportQuery;
import com.amazon.proserve.account.statements.domain.model.queries.GetStatementsQuery;
import com.amazon.proserve.account.statements.domain.model.queries.GetStatementsSummaryQuery;
import com.amazon.proserve.account.statements.domain.model.viewmodels.StatementsSummary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class StatementsQueryServiceTest {

    @InjectMocks
    private StatementsQueryService statementsQueryService;

    @Mock
    private GetStatementsExportQuery mockGetStatementExportQuery;

    @Mock
    private GetStatementsSummaryQuery mockGetStatementSummaryQuery;

    @Mock
    private GetStatementsQuery mockGetStatementsQuery;

    @Mock
    private ExternalLedgerService externalLedgerService;

    @Mock
    private Toggle toggle;

    @Test
    @DisplayName("Test the toggle not set : getStatementsSummary() method")
    public void getStatementsSummaryToggleNotSet() {

        assertNotNull(statementsQueryService);
        when(toggle.isEnabled("statements-legacy-acl")).thenReturn(false);
        statementsQueryService.toggle = toggle;

        when(mockGetStatementSummaryQuery.getTransactionFrom()).thenReturn("20210630");
        when(mockGetStatementSummaryQuery.getTransactionTo()).thenReturn("20210731");

        StatementsSummary[] response = statementsQueryService.getStatementsSummary(mockGetStatementSummaryQuery);
        assertNull(response);
    }

    @Test
    @DisplayName("Test the toggle not set : getStatements() method")
    public void getStatementsToggleNotSet() {

        assertNotNull(statementsQueryService);
        when(toggle.isEnabled("statements-legacy-acl")).thenReturn(false);
        statementsQueryService.toggle = toggle;

        when(mockGetStatementsQuery.getTransactionFrom()).thenReturn("20210630");
        when(mockGetStatementsQuery.getTransactionTo()).thenReturn("20210731");

        Statements response = statementsQueryService.getStatements(mockGetStatementsQuery);
        assertNull(response);
    }

    @Test
    @DisplayName("Test the toggle not set : getExcelExport() method")
    public void getExcelExportToggleNotSet() {

        assertNotNull(statementsQueryService);
        when(toggle.isEnabled("statements-legacy-acl")).thenReturn(false);
        statementsQueryService.toggle = toggle;

        when(mockGetStatementExportQuery.getTransactionFrom()).thenReturn("20210630");
        when(mockGetStatementExportQuery.getTransactionTo()).thenReturn("20210731");

        ResponseEntity<byte[]> response = statementsQueryService.getExcelExport(mockGetStatementExportQuery);
        assertNull(response);
    }

    @Test
    @DisplayName("Test the toggle not set : getExcelExport() method")
    public void getExcelExportToggleSet() {

        assertNotNull(statementsQueryService);
        when(toggle.isEnabled("statements-legacy-acl")).thenReturn(true);
        statementsQueryService.toggle = toggle;

        when(mockGetStatementExportQuery.getTransactionFrom()).thenReturn("20210630");
        when(mockGetStatementExportQuery.getTransactionTo()).thenReturn("20210731");

        final ResponseEntity<byte[]> result = new ResponseEntity<>(HttpStatus.ACCEPTED);

        when(externalLedgerService.getStatementsExport(any())).thenReturn(result);

        ResponseEntity<byte[]> response = statementsQueryService.getExcelExport(mockGetStatementExportQuery);
        assertNotNull(response);
        assertEquals(response, result);
    }
}