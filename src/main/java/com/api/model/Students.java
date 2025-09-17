package com.api.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Students implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int sid;

	@NotNull(message = "The Name should not be empty")
	@NotEmpty(message = "The Name should not be empty")
	private String name;

	@NotNull(message = "The marks should not be empty")
	@NotEmpty(message = "The marks should not be empty")
	private String marks;

	@NotNull(message = "The marks should not be empty")
	@NotEmpty(message = "The marks should not be empty")
	private String role;

	@ManyToOne // Many students can belong to one department
	@JoinColumn(name = "deptid") // Foreign key column in Students table
	@JsonBackReference // To avoid infinite recursion during serialization
	private Department dept;

	public Students() {
		super();
	}

	public Students(int sid, String name, String marks, Department dept, String role) {
		this.sid = sid;
		this.name = name;
		this.marks = marks;
		this.dept = dept;
		this.role = role;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMarks() {
		return marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public void setdeptid(Department dept) {
		this.dept = dept;
	}

	public int getdeptid() {
		return dept.getDeptid();
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Students [sid=" + sid + ", name=" + name + ", marks=" + marks + ", role=" + role + ", deptid=" + dept.getDeptid() + "]";
	}

}
