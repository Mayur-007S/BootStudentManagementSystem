package com.api.model;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@SequenceGenerator(
	    name = "dept_seq_gen",
	    sequenceName = "dept_seq",
	    initialValue = 100,     // first value Hibernate requests
	    allocationSize = 1       // keep in sync with DB sequence increment
	)
public class Department implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dept_seq_gen")
	private int deptid;

	@NotNull(message = "The Name should not be empty")
	@NotEmpty(message = "The Name should not be empty")
	private String deptname;

	@NotNull(message = "The Location should not be empty")
	@NotEmpty(message = "The Location should not be empty")
	private String location;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dept")
	@JsonManagedReference // To avoid infinite recursion during serialization
	private List<Students> studentlist; 

	public Department() {
		super();
	}
	
	public Department(int deptid,
			String deptname,
			String location,
			List<Students> studentlist) {

		this.deptid = deptid;
		this.deptname = deptname;
		this.location = location;
		this.studentlist = studentlist;
	}

	public int getDeptid() {
		return deptid;
	}

	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Department [deptid=" + deptid + ", deptname=" + deptname + ", location=" + location + ", studentlist="
				+ studentlist + "]";
	}

	
}
