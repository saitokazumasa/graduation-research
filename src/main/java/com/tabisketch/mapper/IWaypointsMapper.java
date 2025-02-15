package com.tabisketch.mapper;

import com.tabisketch.bean.entity.Waypoint;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IWaypointsMapper {
    @Insert("""
            INSERT INTO waypoints (label, place_id, visit_order, preferred_arrival_datetime, arrival_datetime,
                                   stay_time, transportation, duration, budget, waypoint_list_id)
            VALUES (#{label}, #{placeId}, #{visitOrder}, #{preferredArrivalDatetime}, #{arrivalDatetime},
                    #{stayTime}, #{transporation}, #{duration}, #{budget}, #{waypointListId})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(final Waypoint waypoint);

    @Select("""
            SELECT * FROM waypoints w
                INNER JOIN waypoint_lists wl ON w.waypoint_list_id = wl.id
                INNER JOIN plans p ON wl.plan_id = p.id
                INNER JOIN users u ON p.user_id = u.id
            WHERE w.waypoint_list_id = #{waypointListId} AND u.email = #{email}
            """)
    List<Waypoint> selectByWaypointListIdAndEmail(final int waypointListId, final String email);
}
