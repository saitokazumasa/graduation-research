package com.tabisketch.mapper;

import com.tabisketch.bean.entity.ExamplePlan;
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
    private IWaypointListsMapper waypointListsMapper;

    @Test
    @Sql({"classpath:/sql/InsertExampleUser.sql", "classpath:/sql/InsertExamplePlan.sql"})
    public void testInsert() {
        final var waypointList = ExampleWaypointList.gen();
        waypointList.setId(-1);
        assert this.waypointListsMapper.insert(waypointList) == 1;
        assert waypointList.getId() != -1;
    }

    @Test
    @Sql({
            "classpath:/sql/InsertExampleUser.sql",
            "classpath:/sql/InsertExamplePlan.sql",
            "classpath:/sql/InsertExampleWaypointList.sql"
    })
    public void testSelectByIdAndEmail() {
        final var waypointList = ExampleWaypointList.gen();
        final var email = ExampleUser.gen().getEmail();
        assert this.waypointListsMapper.selectByIdAndEmail(waypointList.getId(), email) != null;
    }

    @Test
    @Sql({
            "classpath:/sql/InsertExampleUser.sql",
            "classpath:/sql/InsertExamplePlan.sql",
            "classpath:/sql/InsertExampleWaypointList.sql"
    })
    public void testUpdate() {
        final var waypointList = ExampleWaypointList.gen();
        final var email = ExampleUser.gen().getEmail();
        assert this.waypointListsMapper.update(waypointList, email) == 1;
    }

    @Test
    @Sql({
            "classpath:/sql/InsertExampleUser.sql",
            "classpath:/sql/InsertExamplePlan.sql",
            "classpath:/sql/InsertExampleWaypointList.sql"
    })
    public void testDeleteByIdAndEmail() {
        final var id = ExampleWaypointList.gen().getId();
        final var email = ExampleUser.gen().getEmail();
        assert this.waypointListsMapper.deleteByIdAndEmail(id, email) == 1;
    }

    @Test
    @Sql({
            "classpath:/sql/InsertExampleUser.sql",
            "classpath:/sql/InsertExamplePlan.sql",
            "classpath:/sql/InsertExampleWaypointList.sql"
    })
    public void testDeleteByPlanUUIDAndEmail() {
        final var uuid = ExamplePlan.gen().getUuid();
        final var email = ExampleUser.gen().getEmail();
        assert this.waypointListsMapper.deleteByPlanUUIDAndEmail(uuid, email) >= 1;
    }
}
