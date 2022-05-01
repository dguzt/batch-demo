package or.guzman.batchdemo.config;

import or.guzman.batchdemo.BatchDemoApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BatchDemoApplication.class)
@ActiveProfiles("dev")
class BatchJobConfigurationTest {
    @Autowired
    private Job job;

    @Test
    void avoidTest() {
        Assertions.assertNotNull(job);
        Assertions.assertEquals(Constants.JOB_NAME, job.getName());
    }
}
