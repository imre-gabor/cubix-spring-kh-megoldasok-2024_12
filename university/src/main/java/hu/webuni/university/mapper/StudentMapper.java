package hu.webuni.university.mapper;

import org.mapstruct.Mapper;

import hu.webuni.university.dto.StudentDto;
import hu.webuni.university.model.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {
	
	StudentDto studentToDto(Student student);
	
}