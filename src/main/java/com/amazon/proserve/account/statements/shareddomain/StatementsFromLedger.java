package com.amazon.proserve.account.statements.shareddomain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class StatementsFromLedger {
    @JsonProperty("statement_from")
    private String statementFrom;

    @JsonProperty("statement_to")
    private String statementTo;

    @JsonProperty("total_debit")
    private float totalDebit;

    @JsonProperty("total_credit")
    private float totalCredit;

    @JsonProperty("statements")
    private List<StatementFromLedger> statements = new ArrayList<>();;
}
