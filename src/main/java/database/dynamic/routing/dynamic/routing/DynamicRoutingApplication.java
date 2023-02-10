package database.dynamic.routing.dynamic.routing;

import database.dynamic.routing.dynamic.routing.properties.PrimaryDBHikariWriteProperties;
import database.dynamic.routing.dynamic.routing.properties.SecondaryDBHikariReadProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties({PrimaryDBHikariWriteProperties.class, SecondaryDBHikariReadProperties.class})
public class DynamicRoutingApplication {

	public final static String MODEL_PACKAGE = "database.dynamic.routing.dynamic.routing.model";

	public static void main(String[] args) {
		SpringApplication.run(DynamicRoutingApplication.class, args);
	}

}
