package hu.webuni.university.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TeacherDto {

	private int id;
	private String name;
	private LocalDate birthdate;
}
