package com.amazon.proserve.account.statements.domain.model.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetStatementsSummaryQuery {
    private String transactionFrom;
    private String transactionTo;
}
