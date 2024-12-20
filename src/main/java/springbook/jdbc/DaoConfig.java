package springbook.jdbc;

import com.mysql.cj.jdbc.Driver;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
public class DaoConfig {

    @Bean
    public UserDao userDao() {
        return new UserDao(jdbcContext());
    }

    private JdbcContext jdbcContext() {
        return new JdbcContext(dataSource());
    }

    private DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:3307/springbook");
        dataSource.setUsername("spring");
        dataSource.setPassword("book");

        return dataSource;
    }
}
