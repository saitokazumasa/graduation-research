package com.tabisketch.mapper;

import com.tabisketch.bean.entity.DeparturePoint;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IDeparturePointsMapper {
    @Insert("""
            INSERT INTO departure_points (label, place_id, departure_datetime, transportation, duration, waypoint_list_id)
            VALUES (#{label}, #{placeId}, #{departureDatetime}, #{transporation}, #{duration}, #{waypointListId})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(final DeparturePoint departurePoint);

    @Select("""
            SELECT d.* FROM departure_points d
                INNER JOIN waypoint_lists w ON d.waypoint_list_id = w.id
                INNER JOIN plans p ON w.plan_id = p.id
                INNER JOIN users u ON p.user_id = u.id
            WHERE d.id = #{id} AND u.email = #{email}
            """)
    DeparturePoint selectByIdAndEmail(final int id, final String email);

    @Select("""
            SELECT d.* FROM departure_points d
                INNER JOIN waypoint_lists w ON d.waypoint_list_id = w.id
                INNER JOIN plans p ON w.plan_id = p.id
                INNER JOIN users u ON p.user_id = u.id
            WHERE d.waypoint_list_id = #{waypointListId} AND u.email = #{email}
            """)
    DeparturePoint selectByWaypointListIdAndEmail(final int waypointListId, final String email);
}
