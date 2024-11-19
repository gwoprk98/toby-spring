package springbook.jdbctemplate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;


public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(User user) {
        String sql = "insert into users(id, name, password, level) values(?,?,?,?)";
        jdbcTemplate.update(sql, user.getId(), user.getName(), user.getPassword(), user.getLevel().getValue());
    }

    public User findById(String id) {
        String sql = "select id, name, password, level from users where id = ?";
        RowMapper<User> rowMapper = (rs, rowNum) -> new User(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("password"),
                Level.of(rs.getInt("level"))
        );

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }

    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    public List<User> findAll() {
        String sql = "select id, name, password, level from users order by id";
        RowMapper<User> rowMapper = (rs, rowNum) -> new User(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("password"),
                Level.of(rs.getInt("level"))
        );

        return jdbcTemplate.query(sql, rowMapper);
    }

}
