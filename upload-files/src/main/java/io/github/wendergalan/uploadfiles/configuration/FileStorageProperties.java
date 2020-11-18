package io.github.wendergalan.uploadfiles.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The type File storage properties.
 */
@ConfigurationProperties(prefix = "file")
@Data
public class FileStorageProperties {

    private String uploadDir;
}
