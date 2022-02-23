package com.bt.gen.items;

import com.bt.gen.dto.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RestApiItemReader implements ItemReader<ApiResponse>, StepExecutionListener {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.audit.api.url}")
    private String auditApiUrl;
    @Value("${app.token.api.client.id}")
    private String clientId;
    @Value("${app.token.api.client.secret}")
    private String clientSecret;
    @Value("${app.token.api.email}")
    private String email;
    @Value("${app.token.api.org.id}")
    private String orgId;
    @Value("${app.token.api.url}")
    private String tokenApiUrl;
    @Value("${app.audit.file.location}")
    private String logFilePath;

    private boolean jobState = false;

    @Override
    public ApiResponse read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if(!jobState) {
            TokenResponse tokenResponse = getTokenResponse();
            ApiRequest request = createAPIRequest();

            final HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", tokenResponse.getAccessToken());

            final HttpEntity<ApiRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<ApiResponse> response = restTemplate.exchange(auditApiUrl, HttpMethod.POST, entity, ApiResponse.class);

            log.info(response.getStatusCode().toString());
            log.info(response.getBody().toString());
            jobState=true;
            return response.getBody();
        }
        return null;
    }

    private ApiRequest createAPIRequest() {
        UserManagement userManagement = UserManagement.builder().allExceptPresenceUpdated(true).build();
        ActionTypes actionTypes = ActionTypes.builder().userManagement(userManagement).build();
        return ApiRequest.builder().starts("2021-02-25T00:00:00Z").actionTypes(actionTypes).build();
    }

    private TokenResponse getTokenResponse() {
        TokenRequest tokenRequest = TokenRequest.builder()
                .clientId(clientId)
                .email(email)
                .clientSecret(clientSecret)
                .orgId(orgId)
                .build();

        TokenResponse tokenResponse = restTemplate
                .postForObject(URI.create(tokenApiUrl), tokenRequest, TokenResponse.class);
        return tokenResponse;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        jobState = false;
        return stepExecution.getExitStatus();
    }
}
