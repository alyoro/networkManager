package com.prototype.networkManager;

import com.prototype.networkManager.controllers.ApiCalls;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class NetworkManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetworkManagerApplication.class, args);
	}

}

