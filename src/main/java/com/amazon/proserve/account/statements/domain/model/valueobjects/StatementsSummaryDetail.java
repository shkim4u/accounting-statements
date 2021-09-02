package com.amazon.proserve.account.statements.domain.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatementsSummaryDetail {
    @JsonProperty("debit")
    private float debit;

    @JsonProperty("credit")
    private float credit;
}
