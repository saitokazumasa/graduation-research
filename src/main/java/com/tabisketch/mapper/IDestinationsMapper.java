package com.tabisketch.mapper;

import com.tabisketch.bean.entity.Destination;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IDestinationsMapper {
    @Insert("INSERT INTO destinations (" +
            "   place_id, " +
            "   visit_order, " +
            "   specified_start_time, " +
            "   duration_minutes, " +
            "   budget, " +
            "   destination_list_id" +
            ") VALUES (" +
            "   #{placeId}, " +
            "   #{visitOrder}, " +
            "   #{specifiedStartTime}, " +
            "   #{durationMinutes}, " +
            "   #{budget}, " +
            "   #{destinationListId}" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(final Destination record);

    @Select("SELECT * FROM destinations WHERE id = #{id}")
    Destination selectById(final int id);

    @Select("SELECT * FROM destinations WHERE destination_list_id = #{destinationListId}")
    List<Destination> selectByDestinationListId(final int destinationListId);
}
