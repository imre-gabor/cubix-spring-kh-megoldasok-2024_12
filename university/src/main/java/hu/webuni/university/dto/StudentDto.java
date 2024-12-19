package hu.webuni.university.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class StudentDto {

	private int id;
	private String name;
	private int semester;
	private LocalDate birthdate;
}
