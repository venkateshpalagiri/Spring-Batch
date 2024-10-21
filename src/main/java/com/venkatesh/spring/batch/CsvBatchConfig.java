package com.venkatesh.spring.batch;

import com.venkatesh.spring.batch.entity.Employees;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class CsvBatchConfig {

    //    create Reader
    public FlatFileItemReader<Employees> flatFileItemReader(){
        FlatFileItemReader<Employees> fileItemReader=new FlatFileItemReader<>();
        fileItemReader.setResource(new FileSystemResource("src/main/resource/employees.csv"));
        fileItemReader.setStrict(false);
        fileItemReader.setLineMapper(lineMapper());

        return fileItemReader;
    }

    private LineMapper<Employees> lineMapper() {
        DefaultLineMapper<Employees> lineMapper=new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer=new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames("csv_batch");
        tokenizer.setNames("id","firstName","lastName","email","gender","contactNo","country","dob");

        BeanWrapperFieldSetMapper<Employees> fieldSetMapper=new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Employees.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;

    }

    //    create Processor
    @Bean
    public EmployeesProcessor employeesProcessor(){
        return new EmployeesProcessor();
    }

    //    create Writer

    //    create Step

    //    create Job
}

