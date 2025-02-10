package com.tabisketch.mapper;

import com.tabisketch.bean.entity.ExampleResetPasswordToken;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ResetPasswordTokensMapperTest {
    @Autowired
    private IResetPasswordTokensMapper mapper;

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql"})
    public void testInsert() {
        final var entity = ExampleResetPasswordToken.gen();
        assert this.mapper.insert(entity) == 1;
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql", "classpath:/sql/InsertExampleResetPasswordToken.sql"})
    public void testSelectByUUID() {
        final var entity = ExampleResetPasswordToken.gen();
        assert this.mapper.selectByUuid(entity.getUuid()) != null;
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql", "classpath:/sql/InsertExampleResetPasswordToken.sql"})
    public void testDelete() {
        final var entity = ExampleResetPasswordToken.gen();
        assert this.mapper.delete(entity.getUuid()) == 1;
    }
}
