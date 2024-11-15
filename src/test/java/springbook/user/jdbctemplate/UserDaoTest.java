package springbook.user.jdbctemplate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.user.domain.User;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp() {
        // 데이터베이스 초기화 및 사용자 객체 생성
        userDao.deleteAll();

        this.user1 = new User("id1", "name1", "1234");
        this.user2 = new User("id2", "name2", "1234");
        this.user3 = new User("id3", "name3", "1234");
    }

    @DisplayName(value = "사용자 조회하기")
    @Test
    void findById() {
        // given
        User user = new User("id", "name", "1234");
        userDao.add(user);

        // when
        User actual = userDao.findById(user.getId());

        // then
        assertThat(actual).isEqualTo(user);
    }

    @DisplayName(value = "사용자 수 조회하기")
    @Test
    void count() {
        // given
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);

        // when
        int actual = userDao.count();

        // then
        assertThat(actual).isEqualTo(3);
    }

    @DisplayName(value = "모든 사용자 조회하기")
    @Test
    void findAll() {
        // given
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);

        // when
        List<User> actual = userDao.findAll();

        // then
        assertThat(actual).hasSize(3);  // 3명의 사용자가 반환되어야 함
    }
}