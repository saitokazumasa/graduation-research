package com.tabisketch.mapper;

import com.tabisketch.bean.entity.Day;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface IDaysMapper {
    @Insert("INSERT INTO days (day, plan_id, walk_threshold, prefer_transportation_list_binary) " +
            "VALUES (#{day}, #{planId}, #{walkThreshold}, #{preferTransportationListBinary})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(final Day day);

    @Select("SELECT * FROM days WHERE plan_id = #{planId}")
    List<Day> selectByPlanId(final int planId);

    @Delete("DELETE FROM days WHERE id = #{id}")
    int deleteById(final int id);

    @Delete("DELETE FROM days " +
            "   USING plans " +
            "   WHERE plan_id = plans.id " +
            "   AND plans.uuid = #{uuid}")
    int deleteByPlanUUID(final UUID uuid);
}
