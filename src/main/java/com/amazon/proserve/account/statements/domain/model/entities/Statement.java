package com.amazon.proserve.account.statements.domain.model.entities;

import com.amazon.proserve.account.statements.domain.model.valueobjects.StatementDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "statement")
public class Statement {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "statement_id", unique = true)
    @JsonProperty("statement_id")
    private int statementId;

    @Column(name = "statements_id")
    @JsonProperty("statements_id")
    private int statementsId;

    @Column(name = "journal_id")
    @JsonProperty("journal_id")
    private String journalId;

    @Transient
    @Column(insertable = false, updatable = false)
    @JsonProperty("details")
    private List<StatementDetail> details = new ArrayList<>();;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Statement) {
            final Statement statement = (Statement) obj;

            return this.statementId == statement.getStatementId() &&
                    this.statementsId == statement.getStatementsId() &&
                    (this.journalId != null ? this.journalId.equals(statement.getJournalId()) : statement.getJournalId() == null);
        } else {
            return false;
        }
    }
}

