package com.bt.gen.service;

import com.bt.gen.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class LogGenServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private LogGenService underTest;

    private ObjectMapper mapper = new ObjectMapper();

    private String tokenApiUrl = "http://localhost:8080/oauth/v1/core/token";
    private String clientId = "YjbcdxyzADU";
    private String clientSecret = "NjakasjdfZDK";
    private String email = "test2134@gmail.com";
    private String orgId = "P4c4KR8DpDzCGbQfwkstDs1";
    private String auditApiUrl = "http://localhost:8080/activity/v1/org/audit";
    private String logFilePath = "src/main/resources/logFiles/";
    private TokenRequest tokenRequest;
    private TokenResponse tokenResponse;
    private ApiRequest apiRequest;
    private ApiResponse apiResponse;

    public LogGenServiceTest() {
    }


    @Test
    public void testRetrieveLogsHappyPath() throws Exception {
        ReflectionTestUtils.setField(underTest, "tokenApiUrl", tokenApiUrl);
        ReflectionTestUtils.setField(underTest, "clientId", clientId);
        ReflectionTestUtils.setField(underTest, "clientSecret", clientSecret);
        ReflectionTestUtils.setField(underTest, "email", email);
        ReflectionTestUtils.setField(underTest, "orgId", orgId);
        ReflectionTestUtils.setField(underTest, "auditApiUrl", auditApiUrl);
        ReflectionTestUtils.setField(underTest, "logFilePath", logFilePath);
        String token = UUID.randomUUID().toString();
        tokenResponse = TokenResponse.builder()
                .accessToken(token)
                .expiresIn(Date.from(Instant.now().plusMillis(50000)).toInstant().toEpochMilli()).build();
        apiResponse = mapper.readValue(dummy, ApiResponse.class);
        UserManagement userManagement = UserManagement.builder().allExceptPresenceUpdated(true).build();
        ActionTypes actionTypes = ActionTypes.builder().userManagement(userManagement).build();
        apiRequest = ApiRequest.builder().starts("2021-02-25T00:00:00Z").actionTypes(actionTypes).build();
        doReturn(tokenResponse).when(restTemplate).postForObject(any(), any(), any());
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenResponse.getAccessToken());
        final HttpEntity<ApiRequest> entity = new HttpEntity<>(apiRequest, headers);

        doReturn(ResponseEntity.ok(apiResponse)).
                when(restTemplate).exchange(auditApiUrl, HttpMethod.POST, entity, ApiResponse.class);

        underTest.retrieveLogs();
        verify(restTemplate, times(1))
                .postForObject(any(), any(), any());
        verify(restTemplate, times(1))
                .exchange(auditApiUrl, HttpMethod.POST, entity, ApiResponse.class);
    }

    @Test
    public void testRetrieveLogsUnHappyPath() throws Exception {
        ReflectionTestUtils.setField(underTest, "tokenApiUrl", tokenApiUrl);
        ReflectionTestUtils.setField(underTest, "clientId", clientId);
        ReflectionTestUtils.setField(underTest, "clientSecret", clientSecret);
        ReflectionTestUtils.setField(underTest, "email", email);
        ReflectionTestUtils.setField(underTest, "orgId", orgId);
        ReflectionTestUtils.setField(underTest, "auditApiUrl", auditApiUrl);
        ReflectionTestUtils.setField(underTest, "logFilePath", logFilePath);
        String token = UUID.randomUUID().toString();
        tokenResponse = TokenResponse.builder()
                .accessToken(token)
                .expiresIn(Date.from(Instant.now().plusMillis(50000)).toInstant().toEpochMilli()).build();
        UserManagement userManagement = UserManagement.builder().allExceptPresenceUpdated(true).build();
        ActionTypes actionTypes = ActionTypes.builder().userManagement(userManagement).build();
        apiRequest = ApiRequest.builder().starts("2021-02-25T00:00:00Z").actionTypes(actionTypes).build();
        doReturn(tokenResponse).when(restTemplate).postForObject(any(), any(), any());
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenResponse.getAccessToken());
        final HttpEntity<ApiRequest> entity = new HttpEntity<>(apiRequest, headers);

        doReturn(ResponseEntity.notFound().build()).
                when(restTemplate).exchange(auditApiUrl, HttpMethod.POST, entity, ApiResponse.class);

        underTest.retrieveLogs();
        verify(restTemplate, times(1))
                .postForObject(any(), any(), any());
        verify(restTemplate, times(1))
                .exchange(auditApiUrl, HttpMethod.POST, entity, ApiResponse.class);
    }

    private static final String dummy = "{\n" +
            "  \"code\": \"RESPONSE_SUCCESS\",\n" +
            "  \"data\": {\n" +
            "    \"actor\": {\n" +
            "      \"objectType\": \"person\",\n" +
            "      \"org_id\": \"P4C4k\",\n" +
            "      \"user_id\": \"UUG1234\",\n" +
            "      \"email\": \"test123@gmail.com\",\n" +
            "      \"user_type\": \"Internal\",\n" +
            "      \"phone_umber\": \"\",\n" +
            "      \"name\": \"\",\n" +
            "      \"roles\": [\n" +
            "        {\n" +
            "          \"role_id\": 4,\n" +
            "          \"role_name\": \"Internal\",\n" +
            "          \"role_type\": \"ROlE_TYPE_NORMAL\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"is_admin\": true,\n" +
            "      \"display_id\": \"\",\n" +
            "      \"is_deleted\": true,\n" +
            "      \"disabled\": true\n" +
            "    },\n" +
            "    \"activities\": [\n" +
            "      {\n" +
            "        \"objectType\": \"user\",\n" +
            "        \"action_group_id\": \"USER_MANAGEMENT\",\n" +
            "        \"action_type_id\": \"USER_DELETED\",\n" +
            "        \"client_public_ip\": \"10.4.8.13\",\n" +
            "        \"client_version\": \"7002001\",\n" +
            "        \"platform\": \"web\",\n" +
            "        \"browser\": \"Chrome\",\n" +
            "        \"device_model\": \"Windows\",\n" +
            "        \"created_time\": 16114248618267,\n" +
            "        \"published\": \"2021-02-25T10:23:38Z\",\n" +
            "        \"users\": [\n" +
            "          {\n" +
            "            \"name\": \"RK\"\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      {\n" +
            "        \"objectType\": \"user\",\n" +
            "        \"action_group_id\": \"USER_MANAGEMENT\",\n" +
            "        \"action_type_id\": \"USER_CREATED\",\n" +
            "        \"client_public_ip\": \"10.4.8.13\",\n" +
            "        \"client_version\": \"7002001\",\n" +
            "        \"platform\": \"web\",\n" +
            "        \"browser\": \"Chrome\",\n" +
            "        \"device_model\": \"Windows\",\n" +
            "        \"created_time\": 16114248618267,\n" +
            "        \"published\": \"2021-02-25T10:23:38Z\",\n" +
            "        \"users\": [\n" +
            "          {\n" +
            "            \"name\": \"DRK\"\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";

    @TestConfiguration
    static class LogGenServiceTestContextConfiguration {
        @Bean
        public LogGenService logGenService() {
            return new LogGenService() {
                // implement methods
            };
        }
    }

}
