package com.tabisketch.mapper;

import com.tabisketch.bean.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IUsersMapper {
    @Insert("INSERT INTO users (email, password) VALUES (#{email}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(final User user);

    @Select("SELECT * FROM users WHERE id = #{id}")
    User selectById(final int id);

    @Select("SELECT * FROM users WHERE email = #{email}")
    User selectByEmail(final String email);
}
