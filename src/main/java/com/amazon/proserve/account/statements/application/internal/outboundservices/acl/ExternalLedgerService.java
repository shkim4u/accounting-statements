package com.amazon.proserve.account.statements.application.internal.outboundservices.acl;

import com.amazon.proserve.account.statements.domain.model.aggregates.Statements;
import com.amazon.proserve.account.statements.domain.model.valueobjects.StatementsDuration;
import com.amazon.proserve.account.statements.domain.model.valueobjects.StatementsSummaryDetail;
import com.amazon.proserve.account.statements.domain.model.viewmodels.StatementsSummary;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@Getter
@Setter
@Service
public class ExternalLedgerService {
    @Value("${endpoint.url.acl}")
    private String aclUrl;

    @Value("${endpoint.uri.acl.ledgers.statement}")
    private String aclUriLedgersStatement;
    @Value("${endpoint.uri.acl.ledgers.statement.summary}")
    private String aclUriLedgersStatementSummary;
    @Value("${endpoint.uri.acl.ledgers.statement.export}")
    private String aclUriLedgersStatementExport;

    private String aclFullUriLedgersStatement;
    private String aclFullUriLedgersStatementSummary;
    private String aclFullUriLedgersStatementExport;

    private RestTemplate restTemplate;
    private RestTemplate restTemplateForExcel;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @PostConstruct
    public void init() {
        this.setAclFullUriLedgersStatement(aclUrl + aclUriLedgersStatement);
        this.setAclFullUriLedgersStatementSummary(aclUrl + aclUriLedgersStatementSummary);
        this.setAclFullUriLedgersStatementExport(aclUrl + aclUriLedgersStatementExport);

        /**
         * [2021/07/30]
         * Message converter for date from legacy monolith.
         */
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        //Add the Jackson Message converter
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        /**
         * [2021/08/04]
         * Converters
         * - TEXT_HTML
         * - JSON
         */
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_HTML));
        converter.setSupportedMediaTypes(
                Arrays.asList(
                        new MediaType[] {
                                MediaType.TEXT_HTML,
                                MediaType.APPLICATION_JSON
                        }
                )
        );
        messageConverters.add(converter);

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        // Connection timeout.
        factory.setConnectTimeout(5000);
        // Read timeout.
        factory.setReadTimeout(5000);

        /**
         * [2021/07/30]
         * REST template to hold request params.
         */
        restTemplate = new RestTemplate(factory);
        restTemplate.setMessageConverters(messageConverters);

        restTemplateForExcel = restTemplateBuilder.build();
    }

    public Statements getStatements(StatementsDuration statementDuration) {
        Map<String, Object> params = new HashMap<>();

        params.put("transaction_from", statementDuration.getStatementFrom());
        params.put("transaction_to", statementDuration.getStatementTo());

        log.info("[{}] [Invoking external ACL LedgerService] URI = {}.", "getStatements", this.getAclFullUriLedgersStatement());
        Statements statements = restTemplate.postForObject(
            this.getAclFullUriLedgersStatement(),
            params,
            Statements.class
        );

        return statements;
    }

    public StatementsSummary[] getStatementsSummary(StatementsDuration statementsDuration) {
        Map<String, Object> params = new HashMap<>();

        params.put("transaction_from", statementsDuration.getStatementFrom());
        params.put("transaction_to", statementsDuration.getStatementTo());

        log.info("[{}] [Invoking external ACL LedgerService] URI = {}.", "getStatementsSummary", this.getAclFullUriLedgersStatementSummary());

        ResponseEntity<Object> response = restTemplate.postForEntity(
                this.getAclFullUriLedgersStatementSummary(),
                params,
                Object.class
        );
        Object body = response.getBody();

        /**
         * [2021/08/05]
         * Not stable but quickly feasible for now - Assume the returned response body is LinkedHashMap.
         */
        List<StatementsSummary> list = new ArrayList<>();
        if (body instanceof Map) {
            Map<String, Map<String, String>> bodyMap = (Map<String, Map<String, String>>)body;
            // Loop over the response map.
            for (Map.Entry<String, Map<String, String>> entry: bodyMap.entrySet()) {
                // Account Code.
                String accountCode = entry.getKey();
                Map<String, String> detail = entry.getValue();

                float debit = .0f;
                float credit = .0f;
                for (Map.Entry<String, String> detailEntry: detail.entrySet()) {
                    /*
                     * Debit and Credit values.
                     */
                    String detailItem = detailEntry.getKey();
                    String detailValue = detailEntry.getValue();

                    if (StringUtils.equals(detailItem, "debit")) {
                        debit = Float.parseFloat(detailValue);
                    }

                    if (StringUtils.equals(detailItem, "credit")) {
                        credit = Float.parseFloat(detailValue);
                    }
                }

                list.add(
                    new StatementsSummary(accountCode, new StatementsSummaryDetail(debit, credit))
                );
            }
        }

        return list.toArray(new StatementsSummary[0]);
    }

    public ResponseEntity<byte[]> getStatementsExport(StatementsDuration statementDuration) {

        log.info("[{}] [Invoking external ACL LedgerService] URI = {}.", "getStatementsExport", this.getAclFullUriLedgersStatementExport());
        ResponseEntity<byte[]> response = restTemplateForExcel.exchange(
                this.getAclFullUriLedgersStatementExport(),
                HttpMethod.POST,
                this.getEntity(statementDuration),
                byte[].class
        );

        if (response.getBody() != null) {
            log.info("[{}] [Exporting an excel file] {} bytes transferred.", "getStatementsExport", response.getBody().length);
        } else {
            log.error("[{}] Failed to export an excel file", "getStatementsExport");
        }
        return response;
    }

    HttpEntity<Map<String, Object>> getEntity(StatementsDuration statementDuration) {

        Map<String, Object> params = new HashMap<>();
        params.put("transaction_from", statementDuration.getStatementFrom());
        params.put("transaction_to", statementDuration.getStatementTo());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(
                MediaType.TEXT_HTML,
                MediaType.APPLICATION_JSON,
                MediaType.parseMediaType("application/vnd.ms-excel"),
                MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        ));

        return new HttpEntity<>(params, headers);
    }
}
