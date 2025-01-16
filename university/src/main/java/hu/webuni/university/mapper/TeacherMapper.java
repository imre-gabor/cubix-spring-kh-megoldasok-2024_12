package hu.webuni.university.mapper;

import org.mapstruct.Mapper;

import hu.webuni.university.api.model.TeacherDto;
import hu.webuni.university.model.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
	
	TeacherDto teacherToDto(Teacher teacher);
	
}
