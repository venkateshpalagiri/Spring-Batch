package com.venkatesh.spring.batch;

import com.venkatesh.spring.batch.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employees,Integer> {
}
