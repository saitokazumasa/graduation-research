package com.tabisketch.mapper;

import com.tabisketch.bean.entity.ExampleUser;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsersMapperTest {
    @Autowired
    private IUsersMapper usersMapper;

    @Test
    public void testInsert() {
        final var user = ExampleUser.gen();
        user.setId(-1);
        assert this.usersMapper.insert(user) == 1;
        assert user.getId() != -1;
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql"})
    public void testSelectById() {
        final var user = ExampleUser.gen();
        assert this.usersMapper.selectById(user.getId()) != null;
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql"})
    public void testSelectByEmail() {
        final var user = ExampleUser.gen();
        assert this.usersMapper.selectByEmail(user.getEmail()) != null;
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql"})
    public void testUpdatePassword() {
        final var user = ExampleUser.gen();
        assert this.usersMapper.updatePassword(user.getId(), "$2a$10$FFbAunp0hfeWTCune.XqwO/P/61fqWlbruV/8wqzrhM3Pw0VuXxpa") == 1;
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql"})
    public void testUpdateEmailVerified() {
        final var user = ExampleUser.gen();
        assert this.usersMapper.updateEmailVerified(user.getId(), !user.isEmailVerified()) == 1;
    }
}
