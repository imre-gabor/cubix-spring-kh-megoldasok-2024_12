package hu.webuni.bonus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.tokenlib.JwtService;

@SpringBootApplication(
		scanBasePackageClasses = {JwtService.class, BonusApplication.class}
		)
public class BonusApplication {

	public static void main(String[] args) {
		SpringApplication.run(BonusApplication.class, args);
	}

}
