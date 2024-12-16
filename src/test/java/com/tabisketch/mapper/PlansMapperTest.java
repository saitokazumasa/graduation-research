package com.tabisketch.mapper;

import com.tabisketch.bean.entity.Plan;
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
    @Sql("classpath:/sql/CreateUser.sql")
    public void testInsert() {
        final var plan = Plan.generate("", 1);
        final UUID beforeUUID = plan.getUuid();
        this.plansMapper.insert(plan);
        assert plan.getId() != -1;
        assert plan.getUuid() != beforeUUID;
    }

    @Test
    @Sql({
            "classpath:/sql/CreateUser.sql",
            "classpath:/sql/CreatePlan.sql"
    })
    public void testSelect() {
        final int id = 1;
        final var planList = this.plansMapper.selectByUserId(id);
        assert planList != null;
        assert !planList.isEmpty();

        final String mailAddress = "sample@example.com";
        final var planList2 = this.plansMapper.selectByMailAddress(mailAddress);
        assert planList2 != null;
        assert !planList2.isEmpty();
    }
}
