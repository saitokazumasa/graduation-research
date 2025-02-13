package com.tabisketch.mapper;

import com.tabisketch.bean.entity.ExampleEmailVerificationToken;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmailVerificationTokensMapperTest {
    @Autowired
    private IEmailVerificationTokensMapper emailVerificationTokensMapper;

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql"})
    public void testInsert() {
        final var emailVerificationToken = ExampleEmailVerificationToken.gen();
        assert this.emailVerificationTokensMapper.insert(emailVerificationToken) == 1;
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql", "classpath:/sql/InsertExampleEmailVerificationToken.sql"})
    public void testSelectByUUID() {
        final var emailVerificationToken = ExampleEmailVerificationToken.gen();
        assert this.emailVerificationTokensMapper.selectByUUID(emailVerificationToken.getUuid()) != null;
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql", "classpath:/sql/InsertExampleEmailVerificationToken.sql"})
    public void testDelete() {
        final var emailVerificationToken = ExampleEmailVerificationToken.gen();
        assert this.emailVerificationTokensMapper.delete(emailVerificationToken.getUuid()) == 1;
    }
}
