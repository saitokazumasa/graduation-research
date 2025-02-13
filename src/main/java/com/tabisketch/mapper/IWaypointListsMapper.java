package com.tabisketch.mapper;

import com.tabisketch.bean.entity.WaypointList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IWaypointListsMapper {
    @Insert("INSERT INTO waypoint_lists (travel_day, plan_id) VALUES (#{travelDay}, #{planId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(final WaypointList waypointList);

    @Select("SELECT w.* FROM waypoint_lists w " +
            "   INNER JOIN plans p ON w.plan_id = p.id " +
            "   INNER JOIN users u ON p.user_id = u.id " +
            "   WHERE w.id = #{id} AND u.email = #{email}")
    WaypointList selectByIdAndEmail(final int id, final String email);
}
