package hu.webuni.university.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.university.api.model.TimeTableItemDto;
import hu.webuni.university.model.TimeTableItem;

@Mapper(componentModel = "spring")
public interface TimeTableMapper {

	@Mapping(target = "courseName", source="course.name")
	public TimeTableItemDto timeTableItemToDto(TimeTableItem item);
	
	public List<TimeTableItemDto> timeTableItemsToDtos(List<TimeTableItem> items);

}
