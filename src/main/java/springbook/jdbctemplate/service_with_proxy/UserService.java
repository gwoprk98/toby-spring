package springbook.jdbctemplate.service_with_proxy;

import springbook.jdbctemplate.domain.User;

import java.util.List;

public interface UserService {
    void upgradeLevels(List<User> users);
    void upgradeLevel(final User user);
}
