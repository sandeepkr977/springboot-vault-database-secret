package com.vault.springboot.vaultspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.vault.springboot.controller","com.vault.springboot.service","com.vault.springboot.config"})
public class VaultSpringbootApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(VaultSpringbootApplication.class, args);
	}

}
