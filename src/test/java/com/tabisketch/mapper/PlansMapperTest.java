package com.tabisketch.mapper;

import com.tabisketch.bean.entity.ExampleUser;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlansMapperTest {
    @Autowired
    private IPlansMapper mapper;

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql", "classpath:/sql/InsertExamplePlan.sql"})
    public void testSelectByUserId() {
        final var userId = ExampleUser.gen().getId();
        assert this.mapper.selectByUserId(userId) != null;
    }
}
