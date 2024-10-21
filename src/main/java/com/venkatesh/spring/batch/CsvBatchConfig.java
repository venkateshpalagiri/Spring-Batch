package com.venkatesh.spring.batch;

import com.venkatesh.spring.batch.entity.Employees;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class CsvBatchConfig {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    //    create Reader
    public FlatFileItemReader<Employees> employeesReader(){
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
    @Bean
    public RepositoryItemWriter<Employees> repositoryItemWriter(){
        RepositoryItemWriter<Employees> employeesRepositoryItemWriter=new RepositoryItemWriter<>();
        employeesRepositoryItemWriter.setRepository(employeeRepository);
        employeesRepositoryItemWriter.setMethodName("save");

        return employeesRepositoryItemWriter;

    }

    //    create Step
    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step-1").<Employees,Employees>chunk(10)
                .reader(employeesReader())
                .processor(employeesProcessor())
                .writer(repositoryItemWriter())
                .build();
    }

    //    create Job
    @Bean
    public Job job(){
        return jobBuilderFactory.get("employees-job")
                .flow(step1())
                .end()
                .build();
    }
}

