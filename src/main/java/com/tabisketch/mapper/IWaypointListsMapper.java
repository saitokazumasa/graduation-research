package com.tabisketch.mapper;

import com.tabisketch.bean.entity.WaypointList;
import org.apache.ibatis.annotations.*;

import java.util.UUID;

@Mapper
public interface IWaypointListsMapper {
    @Insert("INSERT INTO waypoint_lists (travel_day, plan_id) VALUES (#{travelDay}, #{planId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(final WaypointList waypointList);

    @Select("SELECT w.* FROM waypoint_lists w " +
            "INNER JOIN plans p ON w.plan_id = p.id " +
            "INNER JOIN users u ON p.user_id = u.id " +
            "WHERE w.id = #{id} AND u.email = #{email}")
    WaypointList selectByIdAndEmail(final int id, final String email);

    @Select("""
            SELECT w.* FROM waypoint_lists w
                INNER JOIN plans p ON w.plan_id = p.id
                INNER JOIN users u ON p.user_id = u.id
            WHERE p.uuid = #{planUUID} AND w.travel_day = #{travelDay} AND u.email = #{email}
            """)
    WaypointList selectByPlanUUIDAndTravelDayAndEmail(final UUID planUUID, final int travelDay, final String email);

    @Update("UPDATE waypoint_lists w SET" +
            "   main_transporation = #{waypointList.mainTransporation} " +
            "FROM plans p " +
            "INNER JOIN users u ON p.user_id = u.id " +
            "WHERE w.id = #{waypointList.id} AND u.email = #{email} AND w.plan_id = p.id ")
    int update(final WaypointList waypointList, final String email);

    @Delete("DELETE FROM waypoint_lists w " +
            "USING plans p, users u " +
            "WHERE w.id = #{id} AND u.email = #{email} AND p.user_id = u.id AND w.plan_id = p.id")
    int deleteByIdAndEmail(final int id, final String email);

    @Delete("DELETE FROM waypoint_lists w " +
            "USING plans p, users u " +
            "WHERE p.uuid = #{planUUID} AND u.email = #{email} AND p.user_id = u.id AND w.plan_id = p.id")
    int deleteByPlanUUIDAndEmail(final UUID planUUID, final String email);
}
