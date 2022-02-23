package com.bt.gen;

import com.bt.gen.dto.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@SpringBootTest
class LogGeneratorApplicationTests {
	private static String json = "{\n" +
			"\t\"code\": \"RESPONSE_SUCCESS\",\n" +
			"\t\"data\": {\n" +
			"\t\t\"actor\": {\n" +
			"\t\t\t\"objectType\": \"person\",\n" +
			"\t\t\t\"org_id\": \"P4C4k\",\n" +
			"\t\t\t\"user_id\": \"UUG1234\",\n" +
			"\t\t\t\"email\": \"test123@gmail.com\",\n" +
			"\t\t\t\"user_type\": \"Internal\",\n" +
			"\t\t\t\"phone_umber\": \"\",\n" +
			"\t\t\t\"name\": \"\",\n" +
			"\t\t\t\"roles\": [\n" +
			"\t\t\t\t{\n" +
			"\t\t\t\t\t\"role_id\": 4,\n" +
			"\t\t\t\t\t\"role_name\": \"Internal\",\n" +
			"\t\t\t\t\t\"role_type\": \"ROlE_TYPE_NORMAL\"\n" +
			"\t\t\t\t}\n" +
			"\t\t\t],\n" +
			"\t\t\t\"is_admin\": true,\n" +
			"\t\t\t\"display_id\": \"\",\n" +
			"\t\t\t\"is_deleted\": true,\n" +
			"\t\t\t\"disabled\": true\n" +
			"\t\t},\n" +
			"\t\t\"activities\": [\n" +
			"\t\t\t{\n" +
			"\t\t\t\t\"objectType\": \"user\",\n" +
			"\t\t\t\t\"action_group_id\": \"USER_MANAGEMENT\",\n" +
			"\t\t\t\t\"action_type_id\": \"USER_DELETED\",\n" +
			"\t\t\t\t\"client_public_ip\": \"10.4.8.13\",\n" +
			"\t\t\t\t\"client_version\": \"7002001\",\n" +
			"\t\t\t\t\"platform\": \"web\",\n" +
			"\t\t\t\t\"browser\": \"Chrome\",\n" +
			"\t\t\t\t\"device_model\": \"Windows\",\n" +
			"\t\t\t\t\"created_time\": 16114248618267,\n" +
			"\t\t\t\t\"published\": \"2021-02-25T10:23:38Z\",\n" +
			"\t\t\t\t\"users\": [\n" +
			"\t\t\t\t\t{\n" +
			"\t\t\t\t\t\t\"user\": {\n" +
			"\t\t\t\t\t\t\t\"name\": \"RK\"\n" +
			"\t\t\t\t\t\t}\n" +
			"\t\t\t\t\t}\n" +
			"\t\t\t\t]\n" +
			"\t\t\t},\n" +
			"\t\t\t{\n" +
			"\t\t\t\t\"objectType\": \"user\",\n" +
			"\t\t\t\t\"action_group_id\": \"USER_MANAGEMENT\",\n" +
			"\t\t\t\t\"action_type_id\": \"USER_CREATED\",\n" +
			"\t\t\t\t\"client_public_ip\": \"10.4.8.13\",\n" +
			"\t\t\t\t\"client_version\": \"7002001\",\n" +
			"\t\t\t\t\"platform\": \"web\",\n" +
			"\t\t\t\t\"browser\": \"Chrome\",\n" +
			"\t\t\t\t\"device_model\": \"Windows\",\n" +
			"\t\t\t\t\"created_time\": 16114248618267,\n" +
			"\t\t\t\t\"published\": \"2021-02-25T10:23:38Z\",\n" +
			"\t\t\t\t\"users\": [\n" +
			"\t\t\t\t\t{\n" +
			"\t\t\t\t\t\t\"user\": {\n" +
			"\t\t\t\t\t\t\t\"name\": \"DRK\"\n" +
			"\t\t\t\t\t\t}\n" +
			"\t\t\t\t\t}\n" +
			"\t\t\t\t]\n" +
			"\t\t\t}\n" +
			"\t\t]\n" +
			"\t}\n" +
			"}";

	private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	@Ignore
	@Test
	void contextLoads() throws JsonProcessingException {

		//ApiResponse response = mapper.readValue(json, ApiResponse.class);
		//System.out.println(response);
		////System.out.println(mapper.writeValueAsString(response));
		String dateFormat = "ddMMyyyhhmmss";
		String logFile = "AppName-siem-"+ DateTimeFormatter.ofPattern(dateFormat).format(LocalDateTime.now())+".log";
		System.out.println(logFile);
		//2021-02-25T00:00:00Z

		System.out.println( new Date());
		System.out.println(System.getProperty("java.io.tmpdir"));

	}

}
