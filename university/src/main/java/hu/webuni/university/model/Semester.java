package hu.webuni.university.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Semester {

	public enum SemesterType{
		SPRING, FALL
	}
	
	@EqualsAndHashCode.Include
	private int year;

	@EqualsAndHashCode.Include
	private SemesterType semesterType;
	
	@Transient private LocalDate semesterStart;
	@Transient private LocalDate semesterEnd;
	
	public Semester(int year, SemesterType semester) {
		super();
		this.year = year;
		this.semesterType = semester;
	}
	
	public static Semester fromMidSemesterDay(LocalDate midSemesterDay) {
		Semester semester = new Semester();
		semester.year = midSemesterDay.getYear();
		int monthNumber = midSemesterDay.getMonth().getValue();
		
		if(monthNumber >=2 && monthNumber <=5) {
			semester.semesterType = SemesterType.SPRING;
		} else if (monthNumber >=9 && monthNumber <=12) {
			semester.semesterType = SemesterType.FALL;
		} else {
			throw new IllegalArgumentException("Timetable start date is not inside of the semester");
		}
		
		semester.semesterStart = getSemesterStart(semester.year, semester.semesterType);
		semester.semesterEnd = semester.semesterStart.plusWeeks(14).minusDays(1);
		return semester;
	}
	
	private static LocalDate getSemesterStart(int year, SemesterType semester) {
		LocalDate localDate = LocalDate.of(year, semester == SemesterType.FALL ? 8 : 1, 31);
		localDate = localDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
		return localDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
	}

}
