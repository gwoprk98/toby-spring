package springbook.jdbctemplate.supporter;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SqlFineder {

    private final Map<String, String> properties = new HashMap<>();

    public SqlFineder(final String resource) {
        init(resource);
    }

    private void init(final String resource) {
        Properties properties = new Properties();

        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
            properties.load(inputStream);

            Enumeration<String> enums = (Enumeration<String>) properties.propertyNames();

            while (enums.hasMoreElements()) {
                String key = enums.nextElement();
                String value = properties.getProperty(key);
                this.properties.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get(final String key) {
        return properties.get(key);
    }
}
