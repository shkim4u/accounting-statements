package com.amazon.proserve.account.statements.domain.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@NoArgsConstructor
@Getter
@Embeddable
public class StatementsDuration {
    @Column(name = "statement_from")
    @JsonProperty("statement_from")
    private String statementFrom;

    @Column(name = "statement_to")
    @JsonProperty("statement_to")
    private String statementTo;

    public StatementsDuration(String statementFrom, String statementTo) {
        this.statementFrom = statementFrom;
        this.statementTo = statementTo;
    }
}
