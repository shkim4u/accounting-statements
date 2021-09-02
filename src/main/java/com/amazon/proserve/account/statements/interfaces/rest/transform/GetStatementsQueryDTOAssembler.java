package com.amazon.proserve.account.statements.interfaces.rest.transform;

import com.amazon.proserve.account.statements.domain.model.queries.GetStatementsQuery;
import com.amazon.proserve.account.statements.interfaces.rest.dto.GetStatementsResource;

public class GetStatementsQueryDTOAssembler {
    public static GetStatementsQuery toQueryFromDTO(GetStatementsResource getStatementsResource) {
        return new GetStatementsQuery(
            getStatementsResource.getTransactionFrom(),
            getStatementsResource.getTransactionTo()
        );
    }
}
