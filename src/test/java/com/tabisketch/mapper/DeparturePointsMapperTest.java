package com.tabisketch.mapper;

import com.tabisketch.bean.entity.ExampleDeparturePoint;
import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.bean.entity.ExampleWaypointList;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DeparturePointsMapperTest {
    @Autowired
    private IDeparturePointsMapper departurePointsMapper;

    @Test
    @Sql({
            "classpath:/sql/InsertExampleUser.sql",
            "classpath:/sql/InsertExamplePlan.sql",
            "classpath:/sql/InsertExampleWaypointList.sql"
    })
    public void testInsert() {
        final var departurePoint = ExampleDeparturePoint.gen();
        departurePoint.setId(-1);
        assert this.departurePointsMapper.insert(departurePoint) == 1;
        assert departurePoint.getId() != -1;
    }

    @Test
    @Sql({
            "classpath:/sql/InsertExampleUser.sql",
            "classpath:/sql/InsertExamplePlan.sql",
            "classpath:/sql/InsertExampleWaypointList.sql",
            "classpath:/sql/InsertExampleDeparturePoint.sql"
    })
    public void testSelectByIdAndEmail() {
        final var id = ExampleDeparturePoint.gen().getId();
        final var email = ExampleUser.gen().getEmail();
        assert this.departurePointsMapper.selectByIdAndEmail(id, email) != null;
    }

    @Test
    @Sql({
            "classpath:/sql/InsertExampleUser.sql",
            "classpath:/sql/InsertExamplePlan.sql",
            "classpath:/sql/InsertExampleWaypointList.sql",
            "classpath:/sql/InsertExampleDeparturePoint.sql"
    })
    public void testSelectByWaypointListIdAndEmail() {
        final var waypointListId = ExampleWaypointList.gen().getId();
        final var email = ExampleUser.gen().getEmail();
        assert this.departurePointsMapper.selectByWaypointListIdAndEmail(waypointListId, email) != null;
    }
}
