package com.bt.gen;

import com.bt.gen.service.LogGenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@Slf4j
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class LogGeneratorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LogGeneratorApplication.class, args);
	}

	@Autowired
	public LogGenService logGenService;

	@Override
	public void run(String... args) throws Exception {
		log.info("\n ************ Job started ************ \n");
		logGenService.retrieveLogs();
		log.info("\n ************ Job Completed ************ \n");
		System.exit(-1);
	}
}
