package database.dynamic.routing.dynamic.routing.datasource.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import database.dynamic.routing.dynamic.routing.context.DatabaseEnvironment;
import database.dynamic.routing.dynamic.routing.properties.PrimaryDBHikariWriteProperties;
import database.dynamic.routing.dynamic.routing.properties.SecondaryDBHikariReadProperties;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static database.dynamic.routing.dynamic.routing.DynamicRoutingApplication.MODEL_PACKAGE;

/**
 * @author shubhamkumar
 */

@Configuration
@EnableJpaRepositories(
  entityManagerFactoryRef = "entityManagerFactory",
  transactionManagerRef = "transactionManagerWrite",
  basePackages = {"database.dynamic.routing.dynamic.routing.repository"}
)
@EnableTransactionManagement
public class DataSourceConfig {
  @Value("${jdbc.master.url}")
  private String mstUrl;

  @Value("${jdbc.master.username}")
  private String mstUsername;

  @Value("${jdbc.master.password}")
  private String mstPassword;

  @Value("${jdbc.slave.url}")
  private String slaveUrl;

  @Value("${jdbc.slave.username}")
  private String slaveUsername;

  @Value("${jdbc.slave.password}")
  private String slavePassword;

  @Autowired
  private SecondaryDBHikariReadProperties hikariReadProperties;

  @Autowired
  private PrimaryDBHikariWriteProperties hikariWriteProperties;



  @Bean
  public DataSource dataSource(){
    MasterSlaveRoutingDataSource masterSlaveRoutingDataSource = new MasterSlaveRoutingDataSource();
    Map<Object, Object> targetDataSources = new HashMap<>();
    targetDataSources.put(DatabaseEnvironment.UPDATABLE, masterDataSource());
    targetDataSources.put(DatabaseEnvironment.READONLY, slaveDataSource());
    masterSlaveRoutingDataSource.setTargetDataSources(targetDataSources);

    // Set as all transaction point to master
    masterSlaveRoutingDataSource.setDefaultTargetDataSource(masterDataSource());
    return masterSlaveRoutingDataSource;
  }


  protected DataSource slaveDataSource() {
    HikariDataSource hikariDataSource = new HikariDataSource();
    hikariDataSource.setJdbcUrl(slaveUrl);
    hikariDataSource.setUsername(slaveUsername);
    hikariDataSource.setPassword(slavePassword);
    hikariDataSource.setDriverClassName("org.postgresql.Driver");
    hikariDataSource.setPoolName(hikariReadProperties.getPoolName());
    hikariDataSource.setMinimumIdle(hikariReadProperties.getMinimumIdle());
    hikariDataSource.setMaximumPoolSize(hikariReadProperties.getMaximumPoolSize());
    hikariDataSource.setIdleTimeout(hikariReadProperties.getIdleTimeout());
    return hikariDataSource;
  }

  public DataSource masterDataSource() {
    HikariDataSource hikariDataSource = new HikariDataSource();
    hikariDataSource.setJdbcUrl(mstUrl);
    hikariDataSource.setUsername(mstUsername);
    hikariDataSource.setPassword(mstPassword);
    hikariDataSource.setDriverClassName("org.postgresql.Driver");
    hikariDataSource.setPoolName(hikariWriteProperties.getPoolName());
    hikariDataSource.setMinimumIdle(hikariWriteProperties.getMinimumIdle());
    hikariDataSource.setMaximumPoolSize(hikariWriteProperties.getMaximumPoolSize());
    hikariDataSource.setIdleTimeout(hikariWriteProperties.getIdleTimeout());
    return hikariDataSource;
  }

  protected final Properties JPA_PROPERTIES = new Properties() {{
    put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
    put("hibernate.hbm2ddl.auto", "update");
    put("hibernate.ddl-auto", "update");
    put("show-sql", "true");
  }};

  @Bean(name = "entityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
    final DataSource dataSource) {

    return new LocalContainerEntityManagerFactoryBean() {{
      setDataSource(dataSource);
      setPersistenceProviderClass(HibernatePersistenceProvider.class);
      setPersistenceUnitName("PERSISTENCE_UNIT_NAME");
      setPackagesToScan(MODEL_PACKAGE);
      setJpaProperties(JPA_PROPERTIES);
    }};
  }

  @Bean(name = "transactionManagerWrite")
  public PlatformTransactionManager transactionManagerWrite(EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }

}
