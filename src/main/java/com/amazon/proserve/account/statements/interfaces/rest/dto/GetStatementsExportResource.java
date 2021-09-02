package com.amazon.proserve.account.statements.interfaces.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetStatementsExportResource {
    @JsonProperty("transaction_from")
    private String transactionFrom;
    @JsonProperty("transaction_to")
    private String transactionTo;
}
