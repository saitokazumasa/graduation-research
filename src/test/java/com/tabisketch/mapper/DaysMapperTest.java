package com.tabisketch.mapper;

import com.tabisketch.bean.entity.ExampleDay;
import com.tabisketch.bean.entity.ExamplePlan;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DaysMapperTest {
    @Autowired
    private IDaysMapper daysMapper;

    @Test
    @Sql({
            "classpath:/sql/CreateUser.sql",
            "classpath:/sql/CreatePlan.sql",
    })
    public void testInsert() {
        final var day = ExampleDay.generate();
        assert this.daysMapper.insert(day) == 1;
        assert day.getId() != -1;
    }

    @Test
    @Sql({
            "classpath:/sql/CreateUser.sql",
            "classpath:/sql/CreatePlan.sql",
            "classpath:/sql/CreateDay.sql",
    })
    public void testSelect() {
        final int planId = ExampleDay.generate().getPlanId();
        final var dayList = this.daysMapper.selectByPlanId(planId);
        assert dayList != null;
        assert !dayList.isEmpty();
    }

    @Test
    @Sql({
            "classpath:/sql/CreateUser.sql",
            "classpath:/sql/CreatePlan.sql",
            "classpath:/sql/CreateDay.sql",
    })
    public void testDeleteById() {
        final var getId = ExamplePlan.generate().getId();
        assert this.daysMapper.deleteById(getId) == 1;
    }

    @Test
    @Sql({
            "classpath:/sql/CreateUser.sql",
            "classpath:/sql/CreatePlan.sql",
            "classpath:/sql/CreateDay.sql",
    })
    public void testDeleteByPlanUUID() {
        final var uuid = ExamplePlan.generate().getUuid();
        assert this.daysMapper.deleteByPlanUUID(uuid) == 1;
    }
}
