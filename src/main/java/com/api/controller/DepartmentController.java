package com.api.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.exceptions.DepartmentNotFoundException;
import com.api.exceptions.StudentNotFoundException;
import com.api.model.Department;
import com.api.service.DepartmentService;
import com.api.service.StudentService;

@RestController
@RequestMapping("/api")
public class DepartmentController {

	@Autowired
	private DepartmentService service;


	@GetMapping("/dept")
	public List<Department> getAll() throws DepartmentNotFoundException {
		List<Department> listofdepartments = service.getAllDepartment();
		if (!listofdepartments.isEmpty()) {
			return listofdepartments;
		} else {
			throw new DepartmentNotFoundException("No Department Found in Database");
		}
	}

	@GetMapping("/onedept")
	public Department getOneDepartment(@RequestParam(value = "deptid", required = true) int deptid)
			throws StudentNotFoundException {
		Optional<Department> s1 = service.getOne(deptid);

		if (s1.isPresent()) {
			return s1.get();
		} else {
			throw new DepartmentNotFoundException("Department with DeptId = " + deptid + " Not Found in Database");
		}
	}

	@PostMapping("/dept")
	public String addDepartment(@RequestBody Department dept) {
		return service.addDepartment(dept);
	}

}
