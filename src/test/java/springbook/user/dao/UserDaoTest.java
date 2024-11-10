package springbook.user.dao;

import org.junit.jupiter.api.Test;
import springbook.user.domain.User;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
public class UserDaoTest {

    @Test
    public void addAndGet() throws ClassNotFoundException, SQLException {
        UserDao userDao = new NuserDao();
        User user = new User();
        user.setName("test");
        user.setId("test");
        user.setPassword("123");
        userDao.add(user);
        System.out.println("등록성공");

        User result = userDao.get("test");
        System.out.println(result.getId());
        System.out.println(result.getName());
        assertEquals("test", result.getId());



    }
}