package com.amazon.proserve.account.statements.interfaces.rest.transform;

import com.amazon.proserve.account.statements.domain.model.queries.GetStatementsExportQuery;
import com.amazon.proserve.account.statements.domain.model.queries.GetStatementsQuery;
import com.amazon.proserve.account.statements.interfaces.rest.dto.GetStatementsExportResource;
import com.amazon.proserve.account.statements.interfaces.rest.dto.GetStatementsResource;

public class GetStatementsExportQueryDTOAssembler {
    public static GetStatementsExportQuery toQueryFromDTO(GetStatementsExportResource getStatementsExportResource) {
        return new GetStatementsExportQuery(
            getStatementsExportResource.getTransactionFrom(),
            getStatementsExportResource.getTransactionTo()
        );
    }
}
