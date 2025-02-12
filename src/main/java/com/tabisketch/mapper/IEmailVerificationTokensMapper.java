package com.tabisketch.mapper;

import com.tabisketch.bean.entity.EmailVerificationToken;
import org.apache.ibatis.annotations.*;

import java.util.UUID;

@Mapper
public interface IEmailVerificationTokensMapper {
    @Insert("INSERT INTO email_verification_tokens (user_id) VALUES (#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "uuid")
    int insert(final EmailVerificationToken emailVerificationToken);

    @Select("SELECT * FROM email_verification_tokens WHERE uuid = #{uuid}")
    EmailVerificationToken selectByUUID(final UUID uuid);

    @Delete("DELETE FROM email_verification_tokens WHERE uuid = #{uuid}")
    int delete(final UUID uuid);
}
