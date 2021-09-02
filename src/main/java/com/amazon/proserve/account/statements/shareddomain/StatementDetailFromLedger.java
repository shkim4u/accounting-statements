package com.amazon.proserve.account.statements.shareddomain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatementDetailFromLedger {
    @JsonProperty("line")
    private int line;

    @JsonProperty("account_code")
    private int accountCode;

    @JsonProperty("account_name")
    private String accountName;

    @JsonProperty("debit")
    private float debit;

    @JsonProperty("credit")
    private float credit;

}
