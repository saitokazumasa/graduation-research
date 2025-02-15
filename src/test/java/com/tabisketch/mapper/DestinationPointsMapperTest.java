package com.tabisketch.mapper;

import com.tabisketch.bean.entity.ExampleDestinationPoint;
import com.tabisketch.bean.entity.ExampleUser;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DestinationPointsMapperTest {
    @Autowired
    private IDestinationPointsMapper destinationPointsMapper;

    @Test
    @Sql({
            "classpath:/sql/InsertExampleUser.sql",
            "classpath:/sql/InsertExamplePlan.sql",
            "classpath:/sql/InsertExampleWaypointList.sql"
    })
    public void testInsert() {
        final var destinationPoint = ExampleDestinationPoint.gen();
        destinationPoint.setId(-1);
        assert this.destinationPointsMapper.insert(destinationPoint) == 1;
        assert destinationPoint.getId() != -1;
    }

    @Test
    @Sql({
            "classpath:/sql/InsertExampleUser.sql",
            "classpath:/sql/InsertExamplePlan.sql",
            "classpath:/sql/InsertExampleWaypointList.sql",
            "classpath:/sql/InsertExampleDestinationPoint.sql"
    })
    public void testSelectByIdAndEmail() {
        final var id = ExampleDestinationPoint.gen().getId();
        final var email = ExampleUser.gen().getEmail();
        assert this.destinationPointsMapper.selectByIdAndEmail(id, email) != null;
    }
}
