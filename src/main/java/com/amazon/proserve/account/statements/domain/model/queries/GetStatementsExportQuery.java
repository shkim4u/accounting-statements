package com.amazon.proserve.account.statements.domain.model.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetStatementsExportQuery {
    private String transactionFrom;
    private String transactionTo;
}
