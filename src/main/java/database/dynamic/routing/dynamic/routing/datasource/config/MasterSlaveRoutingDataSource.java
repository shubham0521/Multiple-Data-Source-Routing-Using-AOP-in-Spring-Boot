package database.dynamic.routing.dynamic.routing.datasource.config;

import database.dynamic.routing.dynamic.routing.context.DatabaseContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author shubhamkumar
 */
public class MasterSlaveRoutingDataSource extends AbstractRoutingDataSource {

  @Override
  protected Object determineCurrentLookupKey() {
    return DatabaseContextHolder.getEnvironment();
  }
}
