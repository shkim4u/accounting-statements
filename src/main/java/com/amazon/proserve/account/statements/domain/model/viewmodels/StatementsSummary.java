package com.amazon.proserve.account.statements.domain.model.viewmodels;

import com.amazon.proserve.account.statements.domain.model.valueobjects.StatementsSummaryDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "statements_summary")
public class StatementsSummary {
    @Column(name = "account_code", unique = true)
    private String accountCode;

    @Column(insertable = false, updatable = false)
//    private List<StatementsSummaryDetail> statementsSummaryDetails = new ArrayList<>();
    private StatementsSummaryDetail statementsSummaryDetail;
}
