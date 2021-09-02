package com.amazon.proserve.account.statements.domain.model.aggregates;

import com.amazon.proserve.account.statements.domain.model.entities.Statement;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "statements")
public class Statements {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "statements_id", unique = true)
    @JsonProperty("statements_id")
    private int statementsId;

    @Column(name = "statement_from")
    @JsonProperty("statement_from")
    private String statementFrom;

    @Column(name = "statement_to")
    @JsonProperty("statement_to")
    private String statementTo;

    @Column(name = "total_debit")
    @JsonProperty("total_debit")
    private float totalDebit;

    @Column(name = "total_credit")
    @JsonProperty("total_credit")
    private float totalCredit;

    @Transient
    @Column(insertable = false, updatable = false)
    @JsonProperty("statements")
    private List<Statement> statements = new ArrayList<>();;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Statements) {
            final Statements statements = (Statements) obj;

            return (this.statementsId == statements.getStatementsId() &&
                    (this.statementFrom != null ? this.statementFrom.equals(statements.getStatementFrom()) : statements.getStatementFrom() == null) &&
                    (this.statementTo != null ? this.statementTo.equals(statements.getStatementTo()) : statements.getStatementTo() == null) &&
                    this.totalDebit == statements.getTotalDebit() &&
                    this.totalCredit == statements.getTotalCredit() &&
                    this.statements.equals(statements.getStatements()));
        } else {
            return false;
        }
    }
}
