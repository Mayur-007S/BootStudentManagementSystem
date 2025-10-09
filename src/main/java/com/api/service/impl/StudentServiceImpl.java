package com.api.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.api.exceptions.DepartmentNotFoundException;
import com.api.model.Department;
import com.api.model.Students;
import com.api.repository.StudentRepository;
import com.api.service.DepartmentService;
import com.api.service.StudentService;
import com.api.validators.ObjectsValidator;

import jakarta.annotation.PostConstruct;

@Service
@ComponentScan(basePackages = { "com.api.controller" })
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository repository;

	@Autowired
	private DepartmentService deptservice;

	private final ObjectsValidator<Students> validator;

	private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
	
	public StudentServiceImpl(ObjectsValidator<Students> validator) {
		super();
		this.validator = validator;
	}
	
	@Scheduled(cron = "0 0 10 0 0 0") 
	@Cacheable(value = "student", key = "#sid")
	public void updateData() {
		logger.info("Inside updateData Method");
		repository.findAll();
		logger.info("Exit From updateData Method");
	}
	
	@PostConstruct
	public void init() {
		logger.info("Inside init Method");
		updateData();
		logger.info("Exit From init Method");
	}

	@Override
	public List<Students> getAllStudents() {
		logger.info("Inside Get All Student Method");
		logger.info("Fetch All Student Data!");
		return repository.findAll();
	}

	@Override
	public String addStudent(Students stud) {
		logger.info("inside add student method");
		validator.validate(stud);
		System.out.println("" + stud.getdeptid());
		Optional<Department> dept = deptservice.getOne(stud.getdeptid());
		if (dept.isEmpty()) {
			throw new DepartmentNotFoundException(
					"Department with Deptid = " + stud.getdeptid() 
					+ " Not Found in Database");
		}
		logger.info("Student Department ID: %s"+ stud.getdeptid());
		logger.info("Exit from add student method");
		return "Student Added with Id : " + repository.save(stud).getSid();
	}

//	@CachePut(value = "student", key = "#sid")
	@Override
	public Optional<Students> updateStudent(Students stud, int studentId) {
		logger.info("Inside Update Student Method");
		stud.setSid(studentId);
		logger.info("Exit from add student method");
		return Optional.of(repository.save(stud));
	}

//	@CacheEvict(value = "student", key = "#sid")
	@Override
	public void deleteStudent(int studentId) {
		logger.info("Inside Delete Student Method");
		logger.info("Student ID: %s"+ studentId);
		repository.deleteById(studentId);
		logger.info("Exit from Delete student method");
		
	}

	@Override
	public Optional<Students> getOne(int sid) {
		logger.info("Inside Get One student Method");
		logger.info("Student ID: %s"+ sid);
		logger.info("Exit from Get One student method");
		return repository.findById(sid);
	}

	@Override
	public Students getByNameAndMarks(String name, int marks) {
		logger.info("Inside Get By Name And Marks student Method");
		logger.info("Student Name: %s"+ name +" Marks: "+marks);
		logger.info("Exit from Get By Name And Marks student method");
		return repository.getAll(name, marks);
	}

	@Override
	public List<Students> getByName(String name) {
		logger.info("Inside Get By Name student Method");
		logger.info("Student Name: %s"+ name);
		logger.info("Exit from Get By Name student method");
		return repository.getByName(name);
	}

}
