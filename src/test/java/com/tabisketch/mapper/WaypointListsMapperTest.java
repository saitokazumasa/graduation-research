package com.tabisketch.mapper;

import com.tabisketch.bean.entity.ExampleWaypointList;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WaypointListsMapperTest {
    @Autowired
    private IWaypointListsMapper mapper;

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql", "classpath:/sql/InsertExamplePlan.sql"})
    public void testInsert() {
        final var entity = ExampleWaypointList.gen();
        entity.setId(-1);
        assert this.mapper.insert(entity) == 1;
        assert entity.getId() != -1;
    }
}
