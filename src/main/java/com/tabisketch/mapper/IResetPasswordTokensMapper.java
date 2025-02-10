package com.tabisketch.mapper;

import com.tabisketch.bean.entity.ResetPasswordToken;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface IResetPasswordTokensMapper {
    @Insert("INSERT INTO reset_password_tokens (user_id) VALUES (#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "uuid")
    int insert(final ResetPasswordToken resetPasswordToken);
}
