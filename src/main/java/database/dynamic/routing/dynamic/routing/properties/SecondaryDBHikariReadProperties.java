package database.dynamic.routing.dynamic.routing.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author shubhamkumar
 */

@Data
@ConfigurationProperties("spring.datasource-read.hikari")
public class SecondaryDBHikariReadProperties {
  private String poolName;

  private int minimumIdle;

  private int maximumPoolSize;

  private int idleTimeout;
}
