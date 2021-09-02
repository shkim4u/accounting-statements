package com.amazon.proserve.account.statements.application.internal.outboundservices.acl;

import com.amazon.proserve.account.statements.domain.model.valueobjects.StatementsDuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ExternalLedgerServiceTest {

    @InjectMocks
    private ExternalLedgerService externalLedgerService;

    @Mock
    private StatementsDuration mockStatementsDuration;

    @Mock
    private RestTemplate mockRestTemplate;

    @Test
    @DisplayName("Test getStatementsExport() method")
    public void getStatementsExport() {

        final byte[] body = new byte[] { (byte)0xe0, 0x4f, (byte)0xd0 };

        when(mockStatementsDuration.getStatementFrom()).thenReturn("20210630");
        when(mockStatementsDuration.getStatementTo()).thenReturn("20210731");

        when(mockRestTemplate.exchange(
                externalLedgerService.getAclFullUriLedgersStatementExport(),
                HttpMethod.POST,
                externalLedgerService.getEntity(mockStatementsDuration),
                byte[].class)
        ).thenReturn(new ResponseEntity<>(body, HttpStatus.ACCEPTED));

        // inject the mocked rest template to ExternalLedgerService
        final Field field = ReflectionUtils.findField(ExternalLedgerService.class, "restTemplateForExcel");
        if (!ObjectUtils.isEmpty(field)) {
            field.setAccessible(Boolean.TRUE);
            ReflectionUtils.setField(field, externalLedgerService, mockRestTemplate);
        }

        final ResponseEntity<byte[]> response = externalLedgerService.getStatementsExport(mockStatementsDuration);

        assertNotNull(response);
        assertSame(response.getStatusCode(), HttpStatus.ACCEPTED);
        assertArrayEquals(response.getBody(), body);
    }

    @Test
    @DisplayName("Test getStatementsExport() method when failed to get response")
    public void failToGetStatementsExport() {

        when(mockStatementsDuration.getStatementFrom()).thenReturn("20210630");
        when(mockStatementsDuration.getStatementTo()).thenReturn("20210731");

        when(mockRestTemplate.exchange(
                externalLedgerService.getAclFullUriLedgersStatementExport(),
                HttpMethod.POST,
                externalLedgerService.getEntity(mockStatementsDuration),
                byte[].class)
        ).thenReturn(new ResponseEntity<>(HttpStatus.ACCEPTED));

        // inject the mocked rest template to ExternalLedgerService
        final Field field = ReflectionUtils.findField(ExternalLedgerService.class, "restTemplateForExcel");
        if (!ObjectUtils.isEmpty(field)) {
            field.setAccessible(Boolean.TRUE);
            ReflectionUtils.setField(field, externalLedgerService, mockRestTemplate);
        }

        final ResponseEntity<byte[]> response = externalLedgerService.getStatementsExport(mockStatementsDuration);
        assertNotNull(response);
        assertNull(response.getBody());
    }
}