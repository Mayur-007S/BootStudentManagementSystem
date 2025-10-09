package com.api.service;

import java.util.List;
import java.util.Optional;
import com.api.model.Students;

public interface StudentService {
	
	public List<Students> getAllStudents();
	
	public String addStudent(Students stud);
	
	public Optional<Students> updateStudent(Students stud, int studentId);	
	
	public void deleteStudent(int studentId);

	public Optional<Students> getOne(int sid);
	
	public Students getByNameAndMarks(String name, int marks);
	
	public List<Students> getByName(String name);
}
