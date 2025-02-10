package com.tabisketch.mapper;

import com.tabisketch.bean.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface IUsersMapper {
    @Insert("INSERT INTO users (email, password) VALUES (#{email}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(final User user);

    @Select("SELECT * FROM users WHERE id = #{id}")
    User selectById(final int id);

    @Select("SELECT * FROM users WHERE email = #{email}")
    User selectByEmail(final String email);

    @Update("UPDATE users SET password = #{value} WHERE id = #{id}")
    int updatePassword(final int id, final String value);

    @Update("UPDATE users SET email_verified = #{value} WHERE id = #{id}")
    int updateEmailVerified(final int id, final boolean value);
}
