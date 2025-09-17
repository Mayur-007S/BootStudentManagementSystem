package com.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.exceptions.DepartmentNotFoundException;
import com.api.exceptions.StudentNotFoundException;
import com.api.model.Department;
import com.api.model.Students;
import com.api.service.DepartmentService;
import com.api.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class StudentController {

	@Autowired
	private StudentService service;

	
	@GetMapping("/student")
	public List<Students> getAll() throws StudentNotFoundException {
		List<Students> listofstudents = service.getAllStudents();
		if (!listofstudents.isEmpty()) {
			return listofstudents;
		} else {
			throw new StudentNotFoundException("No Student Found in Database");
		}
	}
	
	@GetMapping("/studentFilter")
	public List<Students> getByRole() throws StudentNotFoundException {
		List<Students> listofstudents = service.getAllStudents();
		listofstudents = listofstudents
				.stream()
				.filter(s -> s.getdeptid() == 100 && Integer.parseInt(s.getMarks()) >= 90)
//				.sorted((i,j) -> Integer.compare(i.getMarks(), j.getMarks()))
				.collect(Collectors.toList());
		if (!listofstudents.isEmpty()) {
			return listofstudents;
		} else {
			throw new StudentNotFoundException("No Student Found in Database");
		}
	}

	@GetMapping("/onestudent")
	public Students getOneStudent(@RequestParam int sid) throws StudentNotFoundException {
		Optional<Students> s1 = service.getOne(sid);
		if (s1.isPresent()) {
			return s1.get();
		} else {
			throw new StudentNotFoundException("Student with Sid = " + sid + " Not Found in Database");
		}
	}

//	@PostMapping("/student")
//	public String addStudet(
//			@RequestBody @Valid Students stud, 
//			BindingResult bindingResult
//			) {
//		
//		Optional<Department> dept = 
//				Optional.ofNullable(deptservice.getOne(stud.getDeptId())
//		.orElseThrow(() -> 
//		new DepartmentNotFoundException("Department with Deptid = " + stud.getDeptId() + " Not Found in Database")));
//		
//		if (bindingResult.hasErrors()) {
//			return bindingResult.getAllErrors()
//					.stream()
//					.map(ObjectError::getDefaultMessage)
//					.collect(Collectors.joining());
//		}
//		stud.setDept(dept.get());
//		String student = service.addStudent(stud);
//		return student.toString();
//	}

	@PostMapping("/student")
	public String addStudet(@RequestBody Students stud) {
		return service.addStudent(stud);
	}

	@PutMapping("/student/{sid}")
	public Students updateStudent(@RequestBody Students stud, @PathVariable int sid) {
		Optional<Students> stud1 = service.updateStudent(stud, sid);
		if (stud1.isPresent()) {
			return stud1.get();
		} else
			throw new RuntimeException("Student Not Updated Due to Some Internal Error");
	}

	@PostMapping("/student/{sid}")
	public String deleteStudent(@PathVariable int sid) {

		Optional<Students> s = service.getOne(sid);
		if (s.isPresent()) {
			try {
				service.deleteStudent(sid);
				return "Deleted: Student with Sid = " + sid + " has deleted successfully!";
			} catch (Exception e) {
				e.printStackTrace();
				return "Student Not Deleted Due to Some Internal Error";
			}
		}
		return "Error: Student with Sid = " + sid + " has Not Found!";
	}

	@GetMapping("/error")
	public String throwException() {
		return service.throwException();
	}

	@GetMapping("/studDept")
	public Students getAllStudWithDept(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "marks", required = true) int marks) {
		System.out.println("Name: " + name + " Marks: " + marks);
		Students listofstudent = service.getByNameAndMarks(name, marks);

		if (listofstudent == null) {
			throw new StudentNotFoundException("Student not found");
		} else {
			return listofstudent;
		}
	}

	@GetMapping("/studentName")
	public List<Students> getAllByName(@RequestParam(value = "name", required = true) String name) {
		System.out.println("Name: " + name);
		List<Students> listofstudent = service.getByName(name);

		if (listofstudent.isEmpty()) {
			throw new StudentNotFoundException("Student not found");
		} else {
			return listofstudent;
		}
	}

}
