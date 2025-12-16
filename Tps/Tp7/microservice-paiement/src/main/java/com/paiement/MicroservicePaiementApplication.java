package com.paiement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.paiement")
public class MicroservicePaiementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicePaiementApplication.class, args);
	}

}
