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
    private IUsersMapper mapper;

    @Test
    public void testInsert() {
        final var entity = ExampleUser.gen();
        entity.setId(-1);
        assert this.mapper.insert(entity) == 1;
        assert entity.getId() != -1;
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql"})
    public void testSelectById() {
        final var entity = ExampleUser.gen();
        assert this.mapper.selectById(entity.getId()) != null;
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql"})
    public void testSelectByEmail() {
        final var entity = ExampleUser.gen();
        assert this.mapper.selectByEmail(entity.getEmail()) != null;
    }
}
