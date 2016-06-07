package co.polynome;

import com.google.common.collect.ImmutableSet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@SpringBootApplication
@ComponentScan(excludeFilters = @ComponentScan.Filter(value=WorkerApplication.class,type=FilterType.ASSIGNABLE_TYPE)) // TODO hacky
public class Application {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        final DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        return dataSourceBuilder.build();
    }

    public static void main(String[] args) {
        final SpringApplication app = new SpringApplication();
        app.setWebEnvironment(true);
        app.setMainApplicationClass(Application.class);
        app.setSources(ImmutableSet.of(Application.class)); // TODO why not automatic?
        app.run(args);
    }
}
