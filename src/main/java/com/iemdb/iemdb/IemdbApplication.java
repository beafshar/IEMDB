package com.iemdb.iemdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan(basePackages = {"com.iemdb.iemdb.entity"})
public class IemdbApplication {

	public static void main(String[] args) {
		SpringApplication.run(IemdbApplication.class, args);
	}

}
