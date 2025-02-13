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
    private IPlansMapper plansMapper;

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql"})
    public void testInsert() {
        final var plan = ExamplePlan.gen();
        final var uuid = UUID.randomUUID();
        plan.setId(-1);
        plan.setUuid(uuid);
        assert this.plansMapper.insert(plan) == 1;
        assert plan.getId() != -1;
        assert !plan.getUuid().equals(uuid);
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql", "classpath:/sql/InsertExamplePlan.sql"})
    public void testSelectByUUIDAndEmail() {
        final var uuid = ExamplePlan.gen().getUuid();
        final var email = ExampleUser.gen().getEmail();
        assert this.plansMapper.selectByUUIDAndEmail(uuid, email) != null;
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql", "classpath:/sql/InsertExamplePlan.sql"})
    public void testSelectByUserId() {
        final var userId = ExampleUser.gen().getId();
        assert this.plansMapper.selectByUserId(userId) != null;
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql", "classpath:/sql/InsertExamplePlan.sql"})
    public void testUpdate() {
        final var plan = ExamplePlan.gen();
        assert this.plansMapper.update(plan) == 1;
    }

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql", "classpath:/sql/InsertExamplePlan.sql"})
    public void testDeleteByUUIDAndEmail() {
        final var uuid = ExamplePlan.gen().getUuid();
        final var email = ExampleUser.gen().getEmail();
        assert this.plansMapper.deleteByUUIDAndEmail(uuid, email) == 1;
    }
}
