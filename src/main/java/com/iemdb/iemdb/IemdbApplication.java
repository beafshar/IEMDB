package com.iemdb.iemdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.RestController;

@ServletComponentScan
@SpringBootApplication
public class IemdbApplication {

	public static void main(String[] args) {
		SpringApplication.run(IemdbApplication.class, args);
	}

}
