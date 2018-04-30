package com.greenmile.batch;

/**
 * @author Juciel Almeida
 * 
 * @class BatchConfiguration
 * 
 *        Classe responsável pela configuração de Batch.
 * 
 */

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.greenmile.batch.listener.JobProjetoCompletionListener;
import com.greenmile.batch.processor.ProjetoItemProcessor;
import com.greenmile.batch.to.ProjetoTO;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    public DataSource dataSource;

    @Bean
    public FlatFileItemReader<ProjetoTO> reader() {
        FlatFileItemReader<ProjetoTO> reader = new FlatFileItemReader<ProjetoTO>();
        reader.setResource(new ClassPathResource("gm-challenge.csv"));
        reader.setLineMapper(new DefaultLineMapper<ProjetoTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "name", "start","end","projectManager","projectManagerEmail","projectManagerSkill","employeeName","employeeEmail","employeeTeam","employeeSkill" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<ProjetoTO>() {{
                setTargetType(ProjetoTO.class);
            }});
        }});
        return reader;
    }
    
    @Bean
    public ProjetoItemProcessor processor() {
        return new ProjetoItemProcessor();
    }

    @Bean
    public Job importUserJob(JobProjetoCompletionListener listener) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<ProjetoTO, ProjetoTO> chunk(1)
                .reader(reader())
                .processor(processor())
                .build();
    }

}
