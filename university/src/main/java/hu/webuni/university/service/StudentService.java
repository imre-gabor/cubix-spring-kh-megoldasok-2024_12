package hu.webuni.university.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.university.model.Student;
import hu.webuni.university.repository.StudentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {
	
	private final StudentRepository studentRepository;
	private final CentralEducationService centralEducationService;
	
	@Value("${university.content.profilePics}")
	private String profilePicsFolder;

	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(Path.of(profilePicsFolder));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void updateFreeSemesters() {
		List<Student> students = studentRepository.findAll();
		
		students.forEach(student -> {
			System.out.format("Get number of free semesters of student %s%n", student.getName());
	
			try {
				Integer eduId = student.getEduId();
				if(eduId != null) {
					int numFreeSemesters = centralEducationService.getNumFreeSemestersForStudent(eduId);
					student.setNumFreeSemesters(numFreeSemesters);
					studentRepository.save(student);
				}
			} catch (Exception e) {
				log.error("Error calling central education service.", e);
			}
		});
	}

	public void saveProfilePicture(Integer id, InputStream is) {
		if(!studentRepository.existsById(id))
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		try {
			Files.copy(is, getProfilePicPathForStudent(id), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private Path getProfilePicPathForStudent(Integer id) {
		return Paths.get(profilePicsFolder, id.toString() + ".jpg");
	}

	public Resource getProfilePicture(Integer studentId) {
		FileSystemResource fileSystemResource = new FileSystemResource(getProfilePicPathForStudent(studentId));
		if(!fileSystemResource.exists())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return fileSystemResource;
	}
}
