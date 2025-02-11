package com.tabisketch.mapper;

import com.tabisketch.bean.entity.Plan;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface IPlansMapper {
    @Insert("INSERT INTO plans (user_id) VALUES (#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id,uuid")
    int insert(final Plan plan);

    @Select("SELECT * FROM plans WHERE uuid = #{uuid}")
    Plan selectByUUID(final UUID uuid);

    @Select("SELECT * FROM plans WHERE user_id = #{userId}")
    List<Plan> selectByUserId(final int userId);

    @Update("UPDATE plans SET " +
            "   title = #{title}, " +
            "   editable = #{editable}, " +
            "   publicly_viewable = #{publiclyViewable} " +
            "WHERE id = #{id}")
    int update(final Plan plan);

    @Delete("DELETE FROM plans WHERE uuid = #{uuid}")
    int delete(final UUID uuid);
}
