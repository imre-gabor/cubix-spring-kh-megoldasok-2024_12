package hu.webuni.university.xmlws;

import java.time.LocalDate;
import java.util.List;

import hu.webuni.university.api.model.TimeTableItemDto;
import io.github.threetenjaxb.core.LocalDateXmlAdapter;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@WebService
public interface TimetableWs {

	public List<TimeTableItemDto> getTimetableForStudent(Integer studentId, LocalDate from, LocalDate until);

}
