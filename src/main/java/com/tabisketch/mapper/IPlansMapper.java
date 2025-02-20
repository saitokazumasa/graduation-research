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

    @Select("SELECT p.* FROM plans p " +
            "INNER JOIN users u ON p.user_id = u.id " +
            "WHERE p.uuid = #{uuid} AND u.email = #{email}")
    Plan selectByUUIDAndEmail(final UUID uuid, final String email);

    @Select("SELECT * FROM plans WHERE user_id = #{userId}")
    List<Plan> selectByUserId(final int userId);

    @Update("UPDATE plans SET " +
            "   title = #{title}, " +
            "   thumbnail_path = #{thumbnailPath}, " +
            "   editable = #{editable}, " +
            "   publicly_viewable = #{publiclyViewable} " +
            "WHERE id = #{id} AND user_id = #{userId}")
    int update(final Plan plan);

    @Delete("DELETE FROM plans p " +
            "USING users u " +
            "WHERE p.uuid = #{uuid} AND u.email = #{email} AND p.user_id = u.id")
    int deleteByUUIDAndEmail(final UUID uuid, final String email);
}
