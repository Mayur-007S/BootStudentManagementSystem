package com.api.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import com.api.exceptions.DepartmentNotFoundException;
import com.api.model.Department;
import com.api.model.Students;
import com.api.repository.StudentRepository;
import com.api.service.DepartmentService;
import com.api.service.StudentService;
import com.api.validators.ObjectsValidator;

@Service
@ComponentScan(basePackages = {"com.api.controller"})
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository repository;
	
	@Autowired
	private DepartmentService deptservice;

	private final ObjectsValidator<Students> validator;

	public StudentServiceImpl(ObjectsValidator<Students> validator) {
		super();
		this.validator = validator;
	}

	@Override
	public List<Students> getAllStudents() {
		return repository.findAll();
	}
	
	@Override
	public String addStudent(Students stud) {
		validator.validate(stud);
		System.out.println(""+stud.getdeptid());
		Optional<Department> dept = deptservice.getOne(stud.getdeptid());
		if(dept.isEmpty()) {
			throw new DepartmentNotFoundException("Department with Deptid = " 
					+ stud.getdeptid() + " Not Found in Database");
		}
		return "Student Added with Id : " + repository.save(stud).getSid();
	}

	@CachePut(value = "student", key = "#sid")
	@Override
	public Optional<Students> updateStudent(Students stud, int studentId) {
		stud.setSid(studentId);
		return Optional.of(repository.save(stud));
	}

//	@CacheEvict(value = "student", key = "#sid")
	@Override
	public void deleteStudent(int studentId) {
		repository.deleteById(studentId);
	}

	@Cacheable(value = "student", key = "#sid")
	@Override
	public Optional<Students> getOne(int sid) {
		return repository.findById(sid);
	}
	
	public String throwException() {
		throw new IllegalStateException("Some Exception happened");
	}

	@Override
	public Students getByNameAndMarks(String name, int marks) {
		return repository.getAll(name, marks);
	}

	@Override
	public List<Students> getByName(String name) {
		// TODO Auto-generated method stub
		return repository.getByName(name);
	}

}
