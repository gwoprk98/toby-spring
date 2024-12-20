package springbook.jdbctemplate.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Proxy;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.jdbctemplate.DataSourceConfig;
import springbook.jdbctemplate.domain.Level;
import springbook.jdbctemplate.TransactionHandler;
import springbook.jdbctemplate.domain.User;
import springbook.jdbctemplate.dao.UserDao;
import springbook.jdbctemplate.dao.UserDaoImpl;
import springbook.jdbctemplate.supporter.SqlFinder;


class UserServiceWithMockTest {

    @DisplayName(value = "UserDao 모킹 테스트")
    @Test
    void verify_upgrade() {
        // given
        UserDao userDao = mock(UserDao.class);
        UserService userService = new UserServiceImpl(userDao);

        User user = new User("gundam-98", "건담데브", "1234", Level.BASIC);

        // when
        userService.upgradeLevel(user);

        // then
        verify(userDao).update(any(User.class));
    }

    @ExtendWith(SpringExtension.class)
    @ContextConfiguration(classes = {DataSourceConfig.class, SqlFinder.class})
    static class UserServiceTest {

        @Autowired
        private DataSource dataSource;

        @Autowired
        private SqlFinder sqlFinder;

        private UserDao userDao;
        private UserService userService;

        @BeforeEach
        void init() {
            userDao = new UserDaoImpl(sqlFinder, dataSource);
            UserService userService = new UserServiceImpl(userDao);
            JdbcTransactionManager transactionManager = new JdbcTransactionManager(dataSource);
            TransactionHandler transactionHandler = new TransactionHandler(userService, transactionManager);

            this.userService = (UserService) Proxy.newProxyInstance(
                    getClass().getClassLoader(),
                    new Class[]{UserService.class},
                    transactionHandler
            );

            userDao.deleteAll();
        }

        @DisplayName(value = "여러명의 사용자 레벨 정상 업그레이드")
        @Test
        void upgradeLevels_succeed() {
            // given
            User user1 = new User("id1", "name", "password", Level.BASIC);
            User user2 = new User("id2", "name", "password", Level.BASIC);
            User user3 = new User("id3", "name", "password", Level.SILVER);
            List<User> users = List.of(user1, user2, user3);
            saveAll(users);

            // when
            userService.upgradeLevels(users);

            // then
            User actual1 = userDao.findById(user1.getId());
            User actual2 = userDao.findById(user2.getId());
            User actual3 = userDao.findById(user3.getId());

            assertThat(actual1.getLevel()).isEqualTo(Level.SILVER);
            assertThat(actual2.getLevel()).isEqualTo(Level.SILVER);
            assertThat(actual3.getLevel()).isEqualTo(Level.GOLD);
        }

        @DisplayName(value = "하나라도 레벨 정상 업그레이드 되지 않으면 전부 롤백")
        @Test
        void upgradeLevels_failed() {
            // given
            User user1 = new User("id1", "name", "password", Level.BASIC);
            User user2 = new User("id2", "name", "password", Level.BASIC);
            User user3 = new User("id3", "name", "password", Level.GOLD);
            List<User> users = List.of(user1, user2, user3);
            saveAll(users);

            // when & then
            assertThatThrownBy(() -> userService.upgradeLevels(users))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("더 업그레이드할 수 없는 레벨입니다.");

            User actual1 = userDao.findById(user1.getId());
            User actual2 = userDao.findById(user2.getId());
            User actual3 = userDao.findById(user3.getId());

            assertThat(actual1.getLevel()).isEqualTo(Level.BASIC);
            assertThat(actual2.getLevel()).isEqualTo(Level.BASIC);
            assertThat(actual3.getLevel()).isEqualTo(Level.GOLD);
        }

        private void saveAll(final List<User> users) {
            for (User user : users) {
                userDao.save(user);
            }
        }
    }
}
