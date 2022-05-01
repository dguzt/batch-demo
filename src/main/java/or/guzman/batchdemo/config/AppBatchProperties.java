package or.guzman.batchdemo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter @Setter
@ConfigurationProperties(prefix = "application.batch", ignoreUnknownFields = false)
public class AppBatchProperties {
    private String inputPath;
}
