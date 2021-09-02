package com.amazon.proserve.account.statements.domain.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "statement_detail")
public class StatementDetail {

    @Id
    @Column(name = "statement_detail_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("statement_detail_id")
    private int statementDetailId;

    @Column(name = "statement_id")
    @JsonProperty("statement_id")
    private int statementId;

    @Column(name = "line")
    @JsonProperty("line")
    private int line;

    @Column(name = "account_code")
    @JsonProperty("account_code")
    private int accountCode;

    @Column(name = "account_name")
    @JsonProperty("account_name")
    private String accountName;

    @Column(name = "debit")
    @JsonProperty("debit")
    private float debit;

    @Column(name = "credit")
    @JsonProperty("credit")
    private float credit;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StatementDetail) {
            final StatementDetail statementDetail = (StatementDetail) obj;

            return this.statementDetailId == statementDetail.getStatementDetailId() &&
                    this.statementId == statementDetail.getStatementId() &&
                    this.line == statementDetail.getLine() &&
                    this.accountCode == statementDetail.getAccountCode() &&
                    (this.accountName != null ? this.accountName.equals(statementDetail.getAccountName()) : statementDetail.getAccountName() == null ) &&
                    this.credit == statementDetail.getCredit() &&
                    this.debit == statementDetail.getDebit();
        } else {
            return false;
        }
    }
}
