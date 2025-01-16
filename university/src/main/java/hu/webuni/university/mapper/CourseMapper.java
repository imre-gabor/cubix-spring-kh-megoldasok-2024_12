package hu.webuni.university.mapper;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.university.api.model.CourseDto;
import hu.webuni.university.api.model.HistoryDataCourseDto;
import hu.webuni.university.model.Course;
import hu.webuni.university.model.HistoryData;

@Mapper(componentModel = "spring")
public interface CourseMapper {

	CourseDto courseToDto(Course course);
	
	@Named("summary")
	@Mapping(ignore = true, target = "teachers")
	@Mapping(ignore = true, target = "students")
	CourseDto courseSummaryToDto(Course course);

	Course dtoToCourse(CourseDto courseDto);

	List<CourseDto> coursesToDtos(Iterable<Course> courses);

	@IterableMapping(qualifiedByName = "summary")
	List<CourseDto> courseSummariesToDtos(Iterable<Course> findAll);
	List<HistoryData<CourseDto>> coursesHistoryToDtos(List<HistoryData<Course>> history);

	List<HistoryDataCourseDto> coursesHistoryToHistoryDataCourseDtos(List<HistoryData<Course>> history);
	
	default OffsetDateTime dateToOffsetDateTime(Date date) {
		return OffsetDateTime.ofInstant(date.toInstant(), ZoneId.of("Z"));
	}

}
