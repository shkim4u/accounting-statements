package com.amazon.proserve.account.statements.application.internal.queryservices;

import com.amazon.proserve.account.common.featuretoggle.Toggle;
import com.amazon.proserve.account.statements.application.internal.outboundservices.acl.ExternalLedgerService;
import com.amazon.proserve.account.statements.domain.model.aggregates.Statements;
import com.amazon.proserve.account.statements.domain.model.queries.GetStatementsExportQuery;
import com.amazon.proserve.account.statements.domain.model.queries.GetStatementsQuery;
import com.amazon.proserve.account.statements.domain.model.queries.GetStatementsSummaryQuery;
import com.amazon.proserve.account.statements.domain.model.valueobjects.StatementsDuration;
import com.amazon.proserve.account.statements.domain.model.viewmodels.StatementsSummary;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ComponentScan(basePackages={"com.amazon.proserve.account.common.featuretoggle"})
public class StatementsQueryService {

    private static final String TOGGLE_KEY_STAEMENTS_LEGACY_ACL = "statements-legacy-acl";
    private ExternalLedgerService externalLedgerService;

    @Autowired
    Toggle toggle;

    public StatementsQueryService(ExternalLedgerService externalLedgerService) {
        this.externalLedgerService = externalLedgerService;
    }

    public Statements getStatements(GetStatementsQuery getStatementQuery) {
        if (toggle.isEnabled(TOGGLE_KEY_STAEMENTS_LEGACY_ACL)) {
            log.info("[{}] [Feature Toggle Enabled] {}", "getStatement", TOGGLE_KEY_STAEMENTS_LEGACY_ACL);
            return externalLedgerService.getStatements(
                    new StatementsDuration(
                            getStatementQuery.getTransactionFrom(),
                            getStatementQuery.getTransactionTo()
                    )
            );
        } else {
            log.info("[{}] [Feature Toggle Disabled] {}. Returning null.", "getStatement", TOGGLE_KEY_STAEMENTS_LEGACY_ACL);
            return null;
        }
    }

    public StatementsSummary[] getStatementsSummary(GetStatementsSummaryQuery getStatementsSummaryQuery) {
        if (toggle.isEnabled(TOGGLE_KEY_STAEMENTS_LEGACY_ACL)) {
            log.info("[{}] [Feature Toggle Enabled] {}", "getStatementSummary", TOGGLE_KEY_STAEMENTS_LEGACY_ACL);

            return externalLedgerService.getStatementsSummary(
                new StatementsDuration(
                    getStatementsSummaryQuery.getTransactionFrom(),
                    getStatementsSummaryQuery.getTransactionTo()
                )
            );
        } else {
            log.info("[{}] [Feature Toggle Disabled] {}. Returning null.", "getStatementsSummary", TOGGLE_KEY_STAEMENTS_LEGACY_ACL);
            return null;
        }
    }

    public ResponseEntity<byte[]> getExcelExport(GetStatementsExportQuery getStatementExportQuery) {
        if (toggle.isEnabled(TOGGLE_KEY_STAEMENTS_LEGACY_ACL)) {
            log.info("[{}] [Feature Toggle Enabled] {}", "getExcelExport", TOGGLE_KEY_STAEMENTS_LEGACY_ACL);
            return externalLedgerService.getStatementsExport(
                    new StatementsDuration(
                            getStatementExportQuery.getTransactionFrom(),
                            getStatementExportQuery.getTransactionTo()
                    )
            );
        } else {
            log.info("[{}] [Feature Toggle Disabled] {}. Returning null.", "getExcelExport", TOGGLE_KEY_STAEMENTS_LEGACY_ACL);
            return null;
        }
    }
}
