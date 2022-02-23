package com.bt.gen.service;

import com.bt.gen.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringJoiner;

@Slf4j
@Service
public class LogGenService {

    private static final String HEADER = "deviceServiceName|appID|RefName|device_HostName|eventTime|eventAction|sourceUserName|WorkstationAddress|WorkstationName";
    private static final String PIPE = "|";
    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String LOG_FILE_DATE_FMT = "ddMMyyyhhmmss";

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

    public void retrieveLogs() {
        TokenResponse tokenResponse = getTokenResponse();
        ApiRequest request = createAPIRequest();
        ApiResponse apiResponse = getApiResponse(tokenResponse, request);

        try {
            createLogFile(apiResponse);
        } catch (IOException e) {
            log.error("Error while creating log file",e);
        }

    }

    private void createLogFile(ApiResponse apiResponse) throws IOException {
        if (null != apiResponse) {

            String logFileName = "AppName-siem-" + DateTimeFormatter.ofPattern(LOG_FILE_DATE_FMT).format(LocalDateTime.now()) + ".log";
            File logFile = new File(logFilePath + logFileName);

            try (FileWriter logFileWriter = new FileWriter(logFile)) {
                logFileWriter.write(HEADER + NEW_LINE);
                String userId = apiResponse.getData().getActor().getUserId();

                if (null != apiResponse && null != apiResponse.getData() && null != apiResponse.getData().getActivities()) {

                    List<Activity> activityList = apiResponse.getData().getActivities();
                    for (Activity activity : activityList) {
                        StringJoiner logs = new StringJoiner(PIPE, "", NEW_LINE);
                        logs.add("CCT");
                        logs.add("PAR12345");
                        logs.add("SIEM");
                        logs.add(activity.getClientPublicIp());
                        logs.add(activity.getCreatedTime().toString());
                        logs.add(activity.getActionGroupId());
                        logs.add(userId);
                        logs.add(activity.getClientPublicIp());
                        logs.add(activity.getClientPublicIp());
                        logFileWriter.write(logs.toString());
                    }
                }
            }
        } else {
            log.warn("Api response is null. Logs were not written");
        }
    }

    private ApiResponse getApiResponse(TokenResponse tokenResponse, ApiRequest request) {

        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenResponse.getAccessToken());
        final HttpEntity<ApiRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<ApiResponse> response = restTemplate.exchange(auditApiUrl, HttpMethod.POST, entity, ApiResponse.class);
        log.info(response.getStatusCode().toString());
        log.info(response.getBody().toString());
        return response.getBody();

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

    private ApiRequest createAPIRequest() {
        UserManagement userManagement = UserManagement.builder().allExceptPresenceUpdated(true).build();
        ActionTypes actionTypes = ActionTypes.builder().userManagement(userManagement).build();
        return ApiRequest.builder().starts("2021-02-25T00:00:00Z").actionTypes(actionTypes).build();
    }
}
