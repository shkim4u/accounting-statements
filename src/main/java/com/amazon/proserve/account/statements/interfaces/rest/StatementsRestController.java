package com.amazon.proserve.account.statements.interfaces.rest;

import com.amazon.proserve.account.statements.application.internal.queryservices.StatementsQueryService;
import com.amazon.proserve.account.statements.domain.model.aggregates.Statements;
import com.amazon.proserve.account.statements.domain.model.valueobjects.StatementsSummaryDetail;
import com.amazon.proserve.account.statements.domain.model.viewmodels.StatementsSummary;
import com.amazon.proserve.account.statements.interfaces.rest.dto.GetStatementsExportResource;
import com.amazon.proserve.account.statements.interfaces.rest.dto.GetStatementsResource;
import com.amazon.proserve.account.statements.interfaces.rest.dto.GetStatementsSummaryResource;
import com.amazon.proserve.account.statements.interfaces.rest.transform.GetStatementsExportQueryDTOAssembler;
import com.amazon.proserve.account.statements.interfaces.rest.transform.GetStatementsQueryDTOAssembler;
import com.amazon.proserve.account.statements.interfaces.rest.transform.GetStatementsSummaryQueryDTOAssembler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@RestController
@RequestMapping("/statement")
@Validated
public class StatementsRestController {

    private StatementsQueryService statementsQueryService;

    private StatementsSummary[] getDefaultStatementSummary() {
        return new StatementsSummary[] {
            new StatementsSummary("", new StatementsSummaryDetail(0.f, 0.f))
        };
    }

    public StatementsRestController(StatementsQueryService statementsQueryService) {
        this.statementsQueryService = statementsQueryService;
    }

    /**
     * [2021/07/30]
     * Get statements.
     *
     * 기존 레거시 코드는 text/html 형태로 반환하는데 아래처럼 Object 형태로 반환하면 JSON 형식으로 반환됨.
     * 이를 "/statement/summary" (getStatementsSummary)와 같이 JsonObject로 직접 Text 형식을 만들어 반환하게 할 수 있음.
     */
    @PostMapping
    @ResponseBody
    public Statements getStatements(@RequestBody GetStatementsResource getStatementsResource) {
        log.info("[StatementRestController] getStatements().");
        return this.statementsQueryService.getStatements(
            GetStatementsQueryDTOAssembler.toQueryFromDTO(getStatementsResource)
        );
    }

    /**
     * [2021/07/30]
     * Get statement summary.
     */
    @PostMapping("/summary")
    @ResponseBody
    public String getStatementSummary(@RequestBody GetStatementsSummaryResource getStatementsSummaryResource) {
        log.info("[StatementRestController] getStatementsSummary().");

        /**
         * [2021/09/23]
         * Nullish Coalesce.
         */
        StatementsSummary[] statementsSummaries = Optional.ofNullable(
            this.statementsQueryService.getStatementsSummary(
                GetStatementsSummaryQueryDTOAssembler.toQueryFromDTO(getStatementsSummaryResource)
            )
        ).orElseGet(this::getDefaultStatementSummary);

        JsonObject jsonObj = new JsonObject();
            for (StatementsSummary statementsSummary : statementsSummaries) {
                JsonObject summaryData = new JsonObject();
                summaryData.addProperty("debit", Float.toString(statementsSummary.getStatementsSummaryDetail().getDebit()));
                summaryData.addProperty("credit", Float.toString(statementsSummary.getStatementsSummaryDetail().getCredit()));
                jsonObj.add(statementsSummary.getAccountCode(), summaryData);
            }

        return jsonObj.toString();
    }

    @PostMapping("/export")
    @ResponseBody
    public ResponseEntity<byte[]> getStatementExport(@RequestBody GetStatementsExportResource getStatementsExportResource) throws JsonProcessingException {
        log.info("[StatementRestController] getStatementExport().");
        return this.statementsQueryService.getExcelExport(
                GetStatementsExportQueryDTOAssembler.toQueryFromDTO(getStatementsExportResource)
        );
    }
}