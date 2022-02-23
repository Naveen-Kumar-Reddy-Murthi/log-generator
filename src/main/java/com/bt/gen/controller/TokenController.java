package com.bt.gen.controller;

import com.bt.gen.dto.TokenRequest;
import com.bt.gen.dto.TokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/oauth/v1/core")
public class TokenController {

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> getToken(@RequestBody TokenRequest tokenRequest){
        log.info(tokenRequest.toString());
        TokenResponse tokenResponse = TokenResponse.builder()
                .accessToken(UUID.randomUUID().toString())
                .expiresIn(Date.from(Instant.now().plusMillis(50000)).toInstant().toEpochMilli()).build();
        return ResponseEntity.ok(tokenResponse);
    }


}
