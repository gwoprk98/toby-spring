package springbook.user.dao;

public class DaoFactory {
    public UserDao userDao() {
        NConnectionMaker nConnectionMaker = new NConnectionMaker();
        UserDao userDao = new UserDao(new NConnectionMaker());
        return userDao;
    }
}
