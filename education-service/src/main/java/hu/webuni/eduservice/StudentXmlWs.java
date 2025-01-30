package hu.webuni.eduservice;

import jakarta.jws.WebService;

@WebService
public interface StudentXmlWs {

	int getNumFreeSemestersForStudent(int eduId);

}