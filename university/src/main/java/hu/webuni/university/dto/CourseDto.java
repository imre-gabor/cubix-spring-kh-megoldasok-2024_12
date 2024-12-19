package hu.webuni.university.dto;

import java.util.List;

import lombok.Data;

@Data
public class CourseDto {

	private int id;
	private String name;
	private List<TeacherDto> teachers;
	private List<StudentDto> students;
}
