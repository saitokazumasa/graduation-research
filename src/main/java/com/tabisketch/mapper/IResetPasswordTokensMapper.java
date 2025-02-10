package com.tabisketch.mapper;

import com.tabisketch.bean.entity.ResetPasswordToken;
import org.apache.ibatis.annotations.*;

import java.util.UUID;

@Mapper
public interface IResetPasswordTokensMapper {
    @Insert("INSERT INTO reset_password_tokens (user_id) VALUES (#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "uuid")
    int insert(final ResetPasswordToken resetPasswordToken);

    @Select("SELECT * FROM reset_password_tokens WHERE uuid = #{uuid}")
    ResetPasswordToken selectByUuid(final UUID uuid);

    @Delete("DELETE FROM reset_password_tokens WHERE uuid = #{uuid}")
    int delete(final UUID uuid);
}
