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
    private IResetPasswordTokensMapper resetPasswordTokensMapper;

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql"})
    public void testInsert() {
        final var resetPasswordToken = ExampleResetPasswordToken.gen();
        assert this.resetPasswordTokensMapper.insert(resetPasswordToken) == 1;
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql", "classpath:/sql/InsertExampleResetPasswordToken.sql"})
    public void testSelectByUUID() {
        final var resetPasswordToken = ExampleResetPasswordToken.gen();
        assert this.resetPasswordTokensMapper.selectByUUID(resetPasswordToken.getUuid()) != null;
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql", "classpath:/sql/InsertExampleResetPasswordToken.sql"})
    public void testDelete() {
        final var resetPasswordToken = ExampleResetPasswordToken.gen();
        assert this.resetPasswordTokensMapper.delete(resetPasswordToken.getUuid()) == 1;
    }
}
