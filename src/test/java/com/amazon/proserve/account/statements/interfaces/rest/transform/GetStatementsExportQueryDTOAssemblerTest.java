package com.amazon.proserve.account.statements.interfaces.rest.transform;

import com.amazon.proserve.account.statements.domain.model.queries.GetStatementsExportQuery;
import com.amazon.proserve.account.statements.interfaces.rest.dto.GetStatementsExportResource;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@SpringBootTest
class GetStatementsExportQueryDTOAssemblerTest {

    @Mock
    private GetStatementsExportResource getStatementsExportResource;

    @Test
    public void testObject() {
        assertNotNull(new GetStatementsExportQueryDTOAssembler());
    }

    @Test
    public void testToQueryFromDTO() {

        final String from = "20210630";
        final String to = "20210731";

        when(getStatementsExportResource.getTransactionFrom()).thenReturn(from);
        when(getStatementsExportResource.getTransactionTo()).thenReturn(to);

        final GetStatementsExportQuery query = GetStatementsExportQueryDTOAssembler.toQueryFromDTO(getStatementsExportResource);

        assertSame(query.getTransactionFrom(), from);
        assertSame(query.getTransactionTo(), to);
    }
}