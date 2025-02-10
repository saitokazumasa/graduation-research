package com.tabisketch.mapper;

import com.tabisketch.bean.entity.EmailVerificationToken;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.UUID;

@Mapper
public interface IEmailVerificationTokenMapper {
    @Select("SELECT * FROM email_verification_tokens WHERE uuid = #{uuid}")
    EmailVerificationToken selectByUUID(final UUID uuid);

    @Insert("INSERT INTO email_verification_tokens (user_id) VALUES (#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "uuid")
    int insert(final EmailVerificationToken emailVerificationToken);
}
