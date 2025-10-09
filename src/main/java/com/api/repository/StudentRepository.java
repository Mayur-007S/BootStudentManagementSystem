package com.api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.api.model.Students;

@Repository
public interface StudentRepository extends JpaRepository<Students, Integer> {
	@Query("select s from Students s where s.name=:n and s.marks=:m")
	public Students getAll(@Param("n") String n, @Param("m") int m);

	@Query(value = "SELECT * FROM students WHERE LOWER(name) LIKE LOWER(CONCAT( :n,'%'))", 
			nativeQuery = true)
	List<Students> getByName(@Param("n") String n);

}
