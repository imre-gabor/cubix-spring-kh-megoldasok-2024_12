package hu.webuni.eduservice;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class StudentXmlWsImpl implements StudentXmlWs {

	private Random random = new Random();

	@Override
	public int getNumFreeSemestersForStudent(int eduId) {
		return random.nextInt(0, 10);
	}
}
