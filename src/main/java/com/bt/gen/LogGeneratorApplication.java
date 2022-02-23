package com.bt.gen;

import com.bt.gen.dto.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@EnableScheduling
@SpringBootApplication
@EnableBatchProcessing
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class LogGeneratorApplication implements CommandLineRunner {

	private static final String HEADER =  "deviceServiceName|appID|RefName|device_HostName|eventTime|eventAction|sourceUserName|WorkstationAddress|WorkstationName";
	private static final String PIPE = "|";
	private static final String NEW_LINE = System.getProperty("line.separator");
	private static final String LOG_FILE_DATE_FMT = "ddMMyyyhhmmss";

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


	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public ObjectMapper getMapper() {
		return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static void main(String[] args) {
		SpringApplication.run(LogGeneratorApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		log.info(Arrays.toString(args));
		RestTemplate restTemplate = getRestTemplate();
		TokenRequest tokenRequest = TokenRequest.builder()
									   .clientId(clientId)
									   .email(email)
				                       .clientSecret(clientSecret)
				                       .orgId(orgId)
				                       .build();

		TokenResponse tokenResponse = restTemplate
										.postForObject(URI.create(tokenApiUrl), tokenRequest, TokenResponse.class);

		/*Map<String,String> headers = new HashMap<>();
		headers.put("Authorization",tokenResponse.getAccessToken());

		ApiResponse apiResponse = restTemplate.postForObject(auditApiUrl, request, ApiResponse.class, headers);*/
		ApiRequest request = createAPIRequest();
		final HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", tokenResponse.getAccessToken());

		final HttpEntity<ApiRequest> entity = new HttpEntity<>(request,headers);

		ResponseEntity<ApiResponse> response = restTemplate.exchange(auditApiUrl, HttpMethod.POST, entity, ApiResponse.class);
		ApiResponse apiResponse = response.getBody();
		log.info(response.getStatusCode().toString());
		log.info(getMapper().writeValueAsString(apiResponse));


		String logFileName = "AppName-siem-"+ DateTimeFormatter.ofPattern(LOG_FILE_DATE_FMT).format(LocalDateTime.now())+".log";
		File logFile = new File(logFilePath+logFileName);

		try (FileWriter logFileWriter = new FileWriter(logFile)) {
			logFileWriter.write(HEADER+NEW_LINE);
			String userId = apiResponse.getData().getActor().getUserId();

			if(null != apiResponse && null != apiResponse.getData() && null != apiResponse.getData().getActivities()){

				List<Activity> activityList = apiResponse.getData().getActivities();

				for(Activity activity : activityList){
					StringJoiner logs = new StringJoiner(PIPE,"",NEW_LINE);
					logs.add("CCT");
					logs.add("PAR12345");
					logs.add("SIEM");
					logs.add(activity.getClientPublicIp());
					logs.add(activity.getCreatedTime().toString());
					logs.add(activity.getActionGroupId());
					logs.add(userId);
					logs.add(activity.getClientPublicIp());
					logs.add(activity.getClientPublicIp());
					System.out.println(logs.toString());
					logFileWriter.write(logs.toString());
				}
			}

		}

	}

	private ApiRequest createAPIRequest() {
		UserManagement userManagement = UserManagement.builder().allExceptPresenceUpdated(true).build();
		ActionTypes actionTypes = ActionTypes.builder().userManagement(userManagement).build();
		return ApiRequest.builder().starts("2021-02-25T00:00:00Z").actionTypes(actionTypes).build();
	}
}
