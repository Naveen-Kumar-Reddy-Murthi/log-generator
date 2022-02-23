package com.bt.gen.controller;

import com.bt.gen.dto.ApiRequest;
import com.bt.gen.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        ApiResponse apiResponse = mapper.readValue(new File("src/main/resources/api-response.json"), ApiResponse.class);
        return ResponseEntity.ok(apiResponse);
    }

}
