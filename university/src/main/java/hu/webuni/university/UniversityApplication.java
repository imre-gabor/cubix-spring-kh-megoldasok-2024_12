package hu.webuni.university;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import hu.webuni.university.service.InitDbService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableAsync
@SpringBootApplication
public class UniversityApplication implements CommandLineRunner {
	
	private final InitDbService initDbService;

	public static void main(String[] args) {
		SpringApplication.run(UniversityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initDbService.deleteDb();
		initDbService.deleteAudTables();
		initDbService.addInitData();
		initDbService.modifyCourse();
	}

}
