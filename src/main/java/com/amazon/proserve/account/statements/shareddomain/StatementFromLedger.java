package com.amazon.proserve.account.statements.shareddomain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class StatementFromLedger {
    @JsonProperty("journal_id")
    private String journalId;

    @JsonProperty("details")
    private List<StatementDetailFromLedger> details = new ArrayList<>();;

}
