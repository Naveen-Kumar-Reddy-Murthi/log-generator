package com.bt.gen.jobconfig;

import com.bt.gen.dto.ApiResponse;
import com.bt.gen.items.RestApiItemReader;
import com.bt.gen.items.RestApiItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringBatchJobConfig {

    /*private static final String PROPERTY_REST_API_URL = "rest.api.url";

    @Bean
    public ItemReader<ApiResponse> itemReader(Environment environment, RestTemplate restTemplate) {
        return new RestApiItemReader(*//*environment.getRequiredProperty(PROPERTY_REST_API_URL), restTemplate*//*);
    }

    @Bean
    public ItemWriter<ApiResponse> itemWriter() {
        return new RestApiItemWriter();
    }*/

    /**
     * Creates a bean that represents the only step of our batch job.
     * @param reader
     * @param writer
     * @param stepBuilderFactory
     * @return
     */
    @Bean
    public Step logGenJobStep(ItemReader<ApiResponse> reader,
                               ItemWriter<ApiResponse> writer,
                               StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("logGenJobStep")
                .<ApiResponse, ApiResponse>chunk(1)
                .reader(reader)
                .writer(writer)
                .build();
    }

    /**
     * Creates a bean that represents our batch job.
     * @param logGenJobStep
     * @param jobBuilderFactory
     * @return
     */
    @Bean
    public Job logGenJob(Step logGenJobStep,
                          JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory.get("logGenJob")
                .incrementer(new RunIdIncrementer())
                .flow(logGenJobStep)
                .end()
                .build();
    }
}
