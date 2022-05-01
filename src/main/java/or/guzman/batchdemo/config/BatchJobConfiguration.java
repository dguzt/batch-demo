package or.guzman.batchdemo.config;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class BatchJobConfiguration {
    /**
     * Factory for getting the type of builder required for job configuration.
     */
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private AppBatchProperties appBatchProperties;

    @Bean
    public Job job(Step step) throws Exception {
        return this.jobBuilderFactory
                .get(Constants.JOB_NAME)
                .validator(validator())
                .start(step)
                .build();
    }

    @Bean
    public Step step() throws Exception {
        return this.stepBuilderFactory
                .get(Constants.STEP_NAME)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("hehe");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public JobParametersValidator validator() {
        return new JobParametersValidator() {
            @Override
            public void validate(JobParameters parameters) throws JobParametersInvalidException {
                var filename = parameters.getString(Constants.JOB_PARAM_FILENAME);
                if (StringUtils.hasText(filename)) {
                    throw new JobParametersInvalidException("filename must not be null or blank");
                }

                try {
                    var file = Paths.get(appBatchProperties.getInputPath(), filename);
                    if (Files.notExists(file) || !Files.isReadable(file)) {
                        throw new Exception("File did not exist or was not readable");
                    }
                } catch (Exception e) {
                    throw new JobParametersInvalidException("The input path filename parameter needs to be a valid file location.");
                }
            }
        };
    }
}
