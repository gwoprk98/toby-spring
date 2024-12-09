package springbook.jdbctemplate.supporter;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class SqlReaderImpl implements SqlReader {

    @Override
    public void readSql(SqlRegistry sqlRegistry) throws Exception {
        String resource = "sql-user-properties";
        Properties properties = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resource);
        properties.load(inputStream);

        Enumeration<String> enums = (Enumeration<String>) properties.propertyNames();

        while (enums.hasMoreElements()) {
            String key = enums.nextElement();
            String value = properties.getProperty(key);
            sqlRegistry.registerSql(key, value);
        }
    }
}
