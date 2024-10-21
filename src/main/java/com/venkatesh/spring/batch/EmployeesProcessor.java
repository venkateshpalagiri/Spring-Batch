package com.venkatesh.spring.batch;

import com.venkatesh.spring.batch.entity.Employees;
import org.springframework.batch.item.ItemProcessor;

public class EmployeesProcessor implements ItemProcessor<Employees, Employees> {
    @Override
    public Employees process(Employees item) throws Exception {
        return null;
    }
}
