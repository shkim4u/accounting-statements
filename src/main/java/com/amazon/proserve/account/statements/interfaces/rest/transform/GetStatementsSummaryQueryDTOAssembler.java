package com.amazon.proserve.account.statements.interfaces.rest.transform;

import com.amazon.proserve.account.statements.domain.model.queries.GetStatementsSummaryQuery;
import com.amazon.proserve.account.statements.interfaces.rest.dto.GetStatementsSummaryResource;

public class GetStatementsSummaryQueryDTOAssembler {
    public static GetStatementsSummaryQuery toQueryFromDTO(GetStatementsSummaryResource getStatementsSummaryResource) {
        return new GetStatementsSummaryQuery(
            getStatementsSummaryResource.getTransactionFrom(),
            getStatementsSummaryResource.getTransactionTo()
        );
    }
}
