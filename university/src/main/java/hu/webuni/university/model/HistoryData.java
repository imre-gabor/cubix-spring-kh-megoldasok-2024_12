package hu.webuni.university.model;

import java.util.Date;

import org.hibernate.envers.RevisionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryData<T> {

	private T data;
	private RevisionType revType;
	private int revision;
	private Date date;
}
