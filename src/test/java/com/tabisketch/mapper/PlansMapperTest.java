package com.tabisketch.mapper;

import com.tabisketch.bean.entity.ExamplePlan;
import com.tabisketch.bean.entity.ExampleUser;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlansMapperTest {
    @Autowired
    private IPlansMapper mapper;

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql"})
    public void testInsert() {
        final var entity = ExamplePlan.gen();
        final var uuid = UUID.randomUUID();
        entity.setId(-1);
        entity.setUuid(uuid);
        assert this.mapper.insert(entity) == 1;
        assert entity.getId() != -1;
        assert !entity.getUuid().equals(uuid);
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql", "classpath:/sql/InsertExamplePlan.sql"})
    public void testSelectByUserId() {
        final var userId = ExampleUser.gen().getId();
        assert this.mapper.selectByUserId(userId) != null;
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql", "classpath:/sql/InsertExamplePlan.sql"})
    public void testDelete() {
        final var uuid = ExamplePlan.gen().getUuid();
        assert this.mapper.delete(uuid) == 1;
    }
}
