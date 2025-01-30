package hu.webuni.university.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.university.api.TimeTableControllerApi;
import hu.webuni.university.api.model.TimeTableItemDto;
import hu.webuni.university.mapper.TimeTableMapper;
import hu.webuni.university.model.TimeTableItem;
import hu.webuni.university.service.TimeTableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TimeTableController implements TimeTableControllerApi {

	private final TimeTableService timeTableService;
	private final TimeTableMapper timeTableMapper;

	@Override
	public ResponseEntity<List<TimeTableItemDto>> getTimetable(@Valid Integer studentId, @Valid Integer teacherId,
			@Valid LocalDate from, @Valid LocalDate until) {

		try {
			if (studentId != null) {
				ArrayList<TimeTableItemDto> result = new ArrayList<>();
				
				Map<LocalDate, List<TimeTableItem>> timeTableForStudent = timeTableService
						.getTimeTableForStudent(studentId, from, until);
				
				for (Map.Entry<LocalDate, List<TimeTableItem>> entry : timeTableForStudent.entrySet()) {
					LocalDate day = entry.getKey();
					List<TimeTableItem> items = entry.getValue();
					List<TimeTableItemDto> itemDtos = timeTableMapper.timeTableItemsToDtos(items);
					itemDtos.forEach(i -> i.setDay(day));
					result.addAll(itemDtos);
				}
				return ResponseEntity.ok(result);
			} 
			//TODO: similar for teacher
			
			else
				return ResponseEntity.badRequest().build();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}

	@Override
	public ResponseEntity<TimeTableItemDto> searchTimeTable(@Valid Integer studentId, @Valid Integer teacherId,
			@Valid LocalDate from, @Valid String course) {
		
		Entry<LocalDate, TimeTableItem> foundTimeTableEntry = timeTableService.searchTimeTableOfStudent(studentId, from, course);
		if(foundTimeTableEntry == null)	
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		

		TimeTableItemDto timeTableItemDto = timeTableMapper.timeTableItemToDto(foundTimeTableEntry.getValue());
		timeTableItemDto.setDay(foundTimeTableEntry.getKey());
		
		return ResponseEntity.ok(timeTableItemDto);
	}

	
	
}
