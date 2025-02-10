package com.tabisketch.mapper;

import com.tabisketch.bean.entity.WaypointList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface IWaypointListsMapper {
    @Insert("INSERT INTO waypoint_lists (travel_day, plan_id) VALUES (#{travelDay}, #{planId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(final WaypointList waypointList);
}
