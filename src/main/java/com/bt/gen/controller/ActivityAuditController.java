package com.bt.gen.controller;

import com.bt.gen.dto.ApiRequest;
import com.bt.gen.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/activity/v1/org")
public class ActivityAuditController {

    @Autowired
    private ObjectMapper mapper;

    @PostMapping("/audit")
    public ResponseEntity<ApiResponse> getToken(@RequestBody ApiRequest apiRequest, @RequestHeader(value = "Authorization", required = true) String authorization) throws IOException {
        log.info(authorization);
        log.info(apiRequest.toString());
        ApiResponse apiResponse = mapper.readValue(dummy, ApiResponse.class);
        return ResponseEntity.ok(apiResponse);
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

}
