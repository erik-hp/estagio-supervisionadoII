package com.turmab.helpdesk;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class HelpdeskturmabApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(HelpdeskturmabApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		System.out.println("******************");
		System.out.println("   Aplicação Help Desk Iniciada com sucesso!      ");
		System.out.println("******************");
	}
}