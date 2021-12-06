package com.tp.bank.batchproccessing;

import com.tp.bank.dto.TransactionDto;
import com.tp.bank.model.Transaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

//    @Bean
//    public ConversionService conversionService(){
//        DefaultConversionService conversionService = new DefaultConversionService();
//        DefaultConversionService.addDefaultConverters(conversionService);
//        conversionService.addConverter(new Converter<String, LocalDateTime>(){
//            @Override
//            public LocalDateTime convert(String value) {
//                return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
//            }
//        });
//        return conversionService;
//    }
//
//    @Bean
//    public FieldSetMapper<TransactionDto> transactionDtoRowMapper() {
//        BeanWrapperFieldSetMapper<TransactionDto> mapper = new BeanWrapperFieldSetMapper<>();
//        mapper.setConversionService(conversionService());
//        mapper.setTargetType(TransactionDto.class);
//        return mapper;
//    }

    @Bean
    public FlatFileItemReader<TransactionDto> reader() {
        return new FlatFileItemReaderBuilder<TransactionDto>()
                .name("transactionItemReader")
                .resource(new ClassPathResource("journal.csv"))
                .linesToSkip(1)
                .delimited()
                .names("idTransaction", "idCompte", "montant", "dateTransaction")
                .fieldSetMapper(new CustomBeanMapper() {{
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
