package com.tabisketch.mapper;

import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.bean.entity.ExampleWaypoint;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WaypointsMapperTest {
    @Autowired
    private IWaypointsMapper waypointsMapper;

    @Test
    @Sql({
            "classpath:/sql/InsertExampleUser.sql",
            "classpath:/sql/InsertExamplePlan.sql",
            "classpath:/sql/InsertExampleWaypointList.sql"
    })
    public void testInsert() {
        final var waypoint = ExampleWaypoint.gen();
        waypoint.setId(-1);
        assert this.waypointsMapper.insert(waypoint) == 1;
        assert waypoint.getId() != -1;
    }

    @Test
    @Sql({
            "classpath:/sql/InsertExampleUser.sql",
            "classpath:/sql/InsertExamplePlan.sql",
            "classpath:/sql/InsertExampleWaypointList.sql",
            "classpath:/sql/InsertExampleWaypoint.sql"
    })
    public void testSelectByIdAndEmail() {
        final var id = ExampleWaypoint.gen().getId();
        final var email = ExampleUser.gen().getEmail();
        assert this.waypointsMapper.selectByWaypointListIdAndEmail(id, email) != null;
    }
}
