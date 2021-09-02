package com.amazon.proserve.account.statements.interfaces.rest.transform;

import com.amazon.proserve.account.statements.domain.model.queries.GetStatementsQuery;
import com.amazon.proserve.account.statements.interfaces.rest.dto.GetStatementsResource;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@SpringBootTest
class GetStatementsQueryDTOAssemblerTest {

    @Mock
    private GetStatementsResource getStatementsResource;

    @Test
    public void testObject() {
        assertNotNull(new GetStatementsQueryDTOAssembler());
    }

    @Test
    public void testToQueryFromDTO() {

        final String from = "20210630";
        final String to = "20210731";

        when(getStatementsResource.getTransactionFrom()).thenReturn(from);
        when(getStatementsResource.getTransactionTo()).thenReturn(to);

        final GetStatementsQuery query = GetStatementsQueryDTOAssembler.toQueryFromDTO(getStatementsResource);

        assertSame(query.getTransactionFrom(), from);
        assertSame(query.getTransactionTo(), to);
    }
}