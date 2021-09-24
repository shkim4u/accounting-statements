package com.amazon.proserve.account.statements.interfaces.rest;


import com.amazon.proserve.account.common.featuretoggle.Toggle;
import com.amazon.proserve.account.statements.application.internal.queryservices.StatementsQueryService;
import com.amazon.proserve.account.statements.domain.model.queries.GetStatementsSummaryQuery;
import com.amazon.proserve.account.statements.domain.model.viewmodels.StatementsSummary;
import com.amazon.proserve.account.statements.interfaces.rest.dto.GetStatementsExportResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class StatementsRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private StatementsRestController statementsRestController;

    @Mock
    private StatementsQueryService statementsQueryService;

    @Test
    @DisplayName("Statements 테스트")
    public void getStatements_success() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("transaction_from", "20200101");
        request.put("transaction_to", "20211231");

        String content = objectMapper.writeValueAsString(request);

        // Step 1: Statements 조회.
        MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.post(
    "/statement"
            ).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print()).andReturn();

    }

    @Test
    @DisplayName("StatementsSummary 테스트")
    public void getStatementsSummary_success() throws Exception {
//        when(toggle.isEnabled("statements-legacy-acl")).thenReturn(false);
//
////        statementsQueryService.toggle = toggle;
//
//        when(mockGetStatementSummaryQuery.getTransactionFrom()).thenReturn("20210630");
//        when(mockGetStatementSummaryQuery.getTransactionTo()).thenReturn("20210731");
//
//        StatementsSummary[] response = statementsQueryService.getStatementsSummary(mockGetStatementSummaryQuery);
//        assertNull(response);

        Map<String, String> request = new HashMap<>();
        request.put("transaction_from", "20200101");
        request.put("transaction_to", "20211231");

        String content = objectMapper.writeValueAsString(request);

        // Step 1: Statements 조회.
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post(
                        "/statement/summary"
                ).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print()).andReturn();
    }

    @Test
    @DisplayName("test StatementsRestController::getStatementExport() method")
    public void getStatementsExport_success() throws Exception {

        final ResponseEntity<byte[]> result = new ResponseEntity<>(HttpStatus.ACCEPTED);

        final GetStatementsExportResource serMock = mock(GetStatementsExportResource.class);
        when(serMock.getTransactionFrom()).thenReturn("20210630");
        when(serMock.getTransactionTo()).thenReturn("20210731");

        when(statementsQueryService.getExcelExport(any())).thenReturn(result);

        final ResponseEntity<byte[]> response = statementsRestController.getStatementExport(serMock);

        assertNotNull(response);
        assertSame(response.getStatusCode(), HttpStatus.ACCEPTED);
    }
}
