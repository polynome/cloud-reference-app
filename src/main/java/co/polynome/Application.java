package co.polynome;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
public class Application {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        final DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        return dataSourceBuilder.build();
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/")
    @ResponseBody
    String home() throws SQLException {
        List<Map<String, Object>> result =  jdbcTemplate.queryForList("SELECT 1 + 2 as total");
        return "Hello World: " + result.get(0).get("total");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
