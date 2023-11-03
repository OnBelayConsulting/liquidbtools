package com.onbelay.liquidbtools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EntityScan(basePackages = {"com.onbelay.*"})
@SpringBootApplication(scanBasePackages = {"com.onbelay.*"})
public class DbScriptsApplication  {
	private static final Logger logger = LogManager.getLogger();
	
	
	public static void main(String[] args) {

		new SpringApplicationBuilder(DbScriptsApplication.class)
				.run(args);

	}

}
