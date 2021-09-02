package com.amazon.proserve.account.statements.interfaces.rest.transform;

import com.amazon.proserve.account.statements.domain.model.queries.GetStatementsSummaryQuery;
import com.amazon.proserve.account.statements.interfaces.rest.dto.GetStatementsSummaryResource;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@SpringBootTest
class GetStatementsSummaryQueryDTOAssemblerTest {

    @Mock
    private GetStatementsSummaryResource getStatementsSummaryResource;

    @Test
    public void testObject() {
        assertNotNull(new GetStatementsSummaryQueryDTOAssembler());
    }

    @Test
    public void testToQueryFromDTO() {

        final String from = "20210630";
        final String to = "20210731";

        when(getStatementsSummaryResource.getTransactionFrom()).thenReturn(from);
        when(getStatementsSummaryResource.getTransactionTo()).thenReturn(to);

        final GetStatementsSummaryQuery query = GetStatementsSummaryQueryDTOAssembler.toQueryFromDTO(getStatementsSummaryResource);

        assertSame(query.getTransactionFrom(), from);
        assertSame(query.getTransactionTo(), to);
    }
}