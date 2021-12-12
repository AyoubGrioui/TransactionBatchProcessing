package com.tp.bank.batchproccessing;

import com.tp.bank.dto.TransactionDto;
import com.tp.bank.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfiguration {

    private final Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);


    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobLauncher jobLauncher;


    @Scheduled(cron = "0 0 0 0 1 0")
    public void launchJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        logger.debug("scheduler starts ");

        JobExecution jobExecution = jobLauncher.run(readCSVFile(), new JobParametersBuilder()
                .addDate("launchDate", new Date())
                .toJobParameters()
        );
        logger.debug("Batch job ends with status as " + jobExecution.getStatus());
        logger.info("scheduler end");
    }


    @Bean
    public FlatFileItemReader<TransactionDto> reader() {
        return new FlatFileItemReaderBuilder<TransactionDto>()
                .name("transactionItemReader")
                .resource(new ClassPathResource("journal.csv"))
                .linesToSkip(1)
                .delimited()
                .names("idTransaction", "idCompte", "montant", "dateTransaction")
                .fieldSetMapper(new CustomBeanMapper<>() {{
                    setTargetType(TransactionDto.class);
                }})
                .build();
    }

    @Bean
    public TransactionItemProcessor processor() {
        return new TransactionItemProcessor();
    }

    @Bean
    public TransactionItemWriter writer() {
        return new TransactionItemWriter();
    }

    @Bean
    public Job readCSVFile() {
        return jobBuilderFactory.get("readCSVFile")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory
                .get("step")
                .<TransactionDto, Transaction>chunk(4)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

}
