package hu.webuni.university.web;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.university.model.CourseStat;
import hu.webuni.university.repository.CourseRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

	private final CourseRepository courseRepository;
	
	@GetMapping("/averageSemestersPerCourse")
	@Async
	public CompletableFuture<List<CourseStat>> getSemesterReport() {
		System.out.println("ReportController.getSemesterReport called at thread " + Thread.currentThread().getName());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		
		return CompletableFuture.completedFuture(
				Collections.emptyList() //TODO: repository metódus hívása
				);
	}
}
