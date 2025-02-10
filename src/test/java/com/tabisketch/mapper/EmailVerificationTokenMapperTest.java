package com.tabisketch.mapper;

import com.tabisketch.bean.entity.ExampleEmailVerificationToken;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmailVerificationTokenMapperTest {
    @Autowired
    private IEmailVerificationTokensMapper mapper;

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql"})
    public void testInsert() {
        final var entity = ExampleEmailVerificationToken.gen();
        assert this.mapper.insert(entity) == 1;
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql", "classpath:/sql/InsertExampleEmailVerificationToken.sql"})
    public void testSelectByUUID() {
        final var entity = ExampleEmailVerificationToken.gen();
        assert this.mapper.selectByUUID(entity.getUuid()) != null;
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql", "classpath:/sql/InsertExampleEmailVerificationToken.sql"})
    public void testDelete() {
        final var entity = ExampleEmailVerificationToken.gen();
        assert this.mapper.delete(entity.getUuid()) == 1;
    }
}
