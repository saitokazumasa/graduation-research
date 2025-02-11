package com.tabisketch.mapper;

import com.tabisketch.bean.entity.NewEmailVerificationToken;
import org.apache.ibatis.annotations.*;

import java.util.UUID;

@Mapper
public interface INewEmailVerificationTokensMapper {
    @Insert("INSERT INTO new_email_verification_tokens (new_email, user_id) VALUES (#{newEmail}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "uuid")
    int insert(final NewEmailVerificationToken newEmailVerificationToken);

    @Select("SELECT * FROM new_email_verification_tokens WHERE uuid = #{uuid}")
    NewEmailVerificationToken selectByUUID(final UUID uuid);

    @Delete("DELETE FROM new_email_verification_tokens WHERE uuid = #{uuid}")
    int delete(final UUID uuid);
}
