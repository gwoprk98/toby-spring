package springbook.jdbctemplate.supporter;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class SqlFinder implements InitializingBean {

    private final Map<String, String> properties = new HashMap<>();

    public String get(final String key) {
        return properties.get(key);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String resource = "sql-user.properties";
        Properties properties = new Properties();

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
        properties.load(inputStream);

        Enumeration<String> enums = (Enumeration<String>) properties.propertyNames();

        while (enums.hasMoreElements()) {
            String key = enums.nextElement();
            String value = properties.getProperty(key);
            this.properties.put(key, value);
        }
    }
}
