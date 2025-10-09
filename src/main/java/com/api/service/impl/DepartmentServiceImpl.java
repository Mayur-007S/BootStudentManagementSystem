package com.api.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.exceptions.DepartmentNotFoundException;
import com.api.model.Department;
import com.api.repository.DepartmentRepository;
import com.api.service.DepartmentService;
import com.api.validators.ObjectsValidator;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository repository;
	
	@Autowired
	private ObjectsValidator<Department> validator;

	@Override
	public List<Department> getAllDepartment() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public String addDepartment(Department dept) {
		validator.validate(dept);
		return "Department Added with Id : " + repository.save(dept).getDeptid();
	}

	@Override
	public Optional<Department> updateDepartment(Department stud, int deptid) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void deleteDepartment(int deptid) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<Department> getOne(int deptid) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(repository.findById(deptid)
				.orElseThrow(() -> 
				new DepartmentNotFoundException("Department with id "+ deptid + " does not exists")));
	}

}
