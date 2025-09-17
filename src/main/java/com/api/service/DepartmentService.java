package com.api.service;

import java.util.List;
import java.util.Optional;

import com.api.model.Department;
import com.api.model.Students;


public interface DepartmentService {
	
	public List<Department> getAllDepartment();
	
	public String addDepartment(Department dept);
	
	public Optional<Department> updateDepartment(Department stud, int deptid);	
	
	public void deleteDepartment(int deptid);

	public Optional<Department> getOne(int deptid);
	
}
