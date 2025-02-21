package hu.webuni.university.xmlws;

import java.time.LocalDate;
import java.util.List;

import hu.webuni.university.api.model.TimeTableItemDto;
import jakarta.jws.WebService;

@WebService
public interface TimetableWs {

	public List<TimeTableItemDto> getTimetableForStudent(Integer studentId, LocalDate from, LocalDate until);

}
