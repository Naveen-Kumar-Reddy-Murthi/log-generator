package com.bt.gen.service;

import com.bt.gen.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
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

import java.net.URI;
import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class LogGenServiceTest {

    private static final String AUDIT_API_URL = "AUDIT_API_URL";
    private static final String CLIENT_ID = "CLIENT_ID";
    private static final String CLIENT_SECRET = "CLIENT_SECRET";
    private static final String EMAIL = "EMAIL";
    private static final String ORG_ID = "ORG_ID";
    private static final String TOKEN_API_URL = "TOKEN_API_URL";
    private static final String LOG_FILE_PATH = "LOG_FILE_PATH";

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
    public void testRetrieveLogs() throws Exception {
        RestTemplate objRestTemplate = mock(RestTemplate.class);
        ReflectionTestUtils.setField(underTest, "tokenApiUrl", tokenApiUrl);
        ReflectionTestUtils.setField(underTest, "clientId", clientId);
        ReflectionTestUtils.setField(underTest, "clientSecret", clientSecret);
        ReflectionTestUtils.setField(underTest, "email", email);
        ReflectionTestUtils.setField(underTest, "orgId", orgId);
        ReflectionTestUtils.setField(underTest, "auditApiUrl", auditApiUrl);
        ReflectionTestUtils.setField(underTest, "logFilePath", logFilePath);
        tokenResponse = TokenResponse.builder()
                .accessToken(UUID.randomUUID().toString())
                .expiresIn(Date.from(Instant.now().plusMillis(50000)).toInstant().toEpochMilli()).build();
        apiResponse = mapper.readValue(dummy, ApiResponse.class);
        UserManagement userManagement = UserManagement.builder().allExceptPresenceUpdated(true).build();
        ActionTypes actionTypes = ActionTypes.builder().userManagement(userManagement).build();
        apiRequest = ApiRequest.builder().starts("2021-02-25T00:00:00Z").actionTypes(actionTypes).build();
        doReturn(tokenResponse).when(restTemplate).postForObject(any(), any(), any());
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenResponse.getAccessToken());
        final HttpEntity<ApiRequest> entity = new HttpEntity<>(apiRequest, headers);

       /* doReturn(ResponseEntity.ok(apiResponse)).
                when(restTemplate).exchange("http://localhost:8080/oauth/v1/core/token", HttpMethod.POST, entity, ApiResponse.class);*/

        doReturn(ResponseEntity.ok(apiResponse)).
                when(restTemplate).exchange(any(), any(), any(), (Class<Object>) any(Object.class));


        underTest.retrieveLogs();
        verify(restTemplate, times(1))
                .postForObject(any(), any(), any());
        verify(restTemplate, times(1))
                .exchange(any(), any(), any(), (Class<Object>) any(Object.class));
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

    @Test
    public void testCreateLogFile() throws Exception {

        /*try {
            Method method = LogGenService.getClass().getMethod("createLogFile", ApiResponse.class);
            method.setAccessible(true);
            method.invoke( < Object >, <Parameters >);
        } catch (NoSuchMethodException e) {
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }*/
    }

    @Test
    public void testGetApiResponse() throws Exception {
        /*try {
            Method method = LogGenService.getClass().getMethod("getApiResponse", TokenResponse.class, ApiRequest.class);
            method.setAccessible(true);
            method.invoke( < Object >, <Parameters >);
        } catch (NoSuchMethodException e) {
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }*/
    }


    @Test
    public void testGetTokenResponse() throws Exception {

        /*try {
            Method method = LogGenService.getClass().getMethod("getTokenResponse");
            method.setAccessible(true);
            method.invoke( < Object >, <Parameters >);
        } catch (NoSuchMethodException e) {
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }*/
    }


    @Test
    public void testCreateAPIRequest() throws Exception {

       /* try {
            Method method = LogGenService.getClass().getMethod("createAPIRequest");
            method.setAccessible(true);
            method.invoke( < Object >, <Parameters >);
        } catch (NoSuchMethodException e) {
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }*/
    }

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
