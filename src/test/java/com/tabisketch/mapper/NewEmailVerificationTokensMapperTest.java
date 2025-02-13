package com.tabisketch.mapper;

import com.tabisketch.bean.entity.ExampleNewEmailVerificartionToken;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NewEmailVerificationTokensMapperTest {
    @Autowired
    private INewEmailVerificationTokensMapper mapper;

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql"})
    public void testInsert() {
        final var newEmailVerificationToken = ExampleNewEmailVerificartionToken.gen();
        assert this.mapper.insert(newEmailVerificationToken) == 1;
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql", "classpath:/sql/InsertExampleNewEmailVerificationToken.sql"})
    public void testSelectByUUID() {
        final var newEmailVerificationToken = ExampleNewEmailVerificartionToken.gen();
        assert this.mapper.selectByUUID(newEmailVerificationToken.getUuid()) != null;
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql", "classpath:/sql/InsertExampleNewEmailVerificationToken.sql"})
    public void testDelete() {
        final var newEmailVerificationToken = ExampleNewEmailVerificartionToken.gen();
        assert this.mapper.delete(newEmailVerificationToken.getUuid()) == 1;
    }
}
