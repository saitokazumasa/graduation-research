package com.tabisketch.mapper;

import com.tabisketch.bean.entity.ExampleUser;
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
        final var waypointList = ExampleWaypointList.gen();
        waypointList.setId(-1);
        assert this.mapper.insert(waypointList) == 1;
        assert waypointList.getId() != -1;
    }

    @Test
    @Sql({
            "classpath:/sql/InsertExampleUser.sql",
            "classpath:/sql/InsertExamplePlan.sql",
            "classpath:/sql/InsertExampleWaypointList.sql"
    })
    public void testSelectById() {
        final var waypointList = ExampleWaypointList.gen();
        final var email = ExampleUser.gen().getEmail();
        assert this.mapper.selectByIdAndEmail(waypointList.getId(), email) != null;
    }
}
