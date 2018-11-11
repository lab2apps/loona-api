package com.loona.hachathon.search;

import com.loona.hachathon.room.RoomDto;
import com.loona.hachathon.util.CsvAttributeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomSearchRepository {

    @Autowired
    private NamedParameterJdbcOperations jdbcOps;

    private static final RoomSearchResultMapper roomSearchResultMapper = new RoomSearchResultMapper();
    private static final CsvAttributeConverter csvAttributeConverter = new CsvAttributeConverter();

    public List<RoomDto> search(RoomFilterParams roomFilterParams) {
        MapSqlParameterSource parameterSource = getSqlParametersFromFilter(roomFilterParams);

        // Additional page parameters
        parameterSource.addValue("offset", roomFilterParams.getPage() * roomFilterParams.getPageSize());
        parameterSource.addValue("limit", roomFilterParams.getPageSize());

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("SELECT * FROM room ")
                .append("WHERE room_type = :roomType ")
                .append("AND rent_type = :rentType ")
                .append("AND price >= :minPrice AND price <= :maxPrice ")
                .append("AND footage >= :minFootage AND footage <= :maxFootage ");

        List<String> options = roomFilterParams.getOptions();
        if (!CollectionUtils.isEmpty(options)) {
            sqlStringBuilder.append("AND ARRAY[ :options ]::TEXT[] <@ regexp_split_to_array(options, ',') ");
        }

        sqlStringBuilder.append("OFFSET :offset LIMIT :limit");

        return jdbcOps.query(sqlStringBuilder.toString(), parameterSource, roomSearchResultMapper);
    }

    public long getTotalCount(RoomFilterParams roomFilterParams) {
        MapSqlParameterSource parameterSource = getSqlParametersFromFilter(roomFilterParams);

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("SELECT count(1) FROM room ")
                .append("WHERE room_type = :roomType ")
                .append("AND rent_type = :rentType ")
                .append("AND price >= :minPrice AND price <= :maxPrice ")
                .append("AND footage >= :minFootage AND footage <= :maxFootage ");

        List<String> options = roomFilterParams.getOptions();
        if (!CollectionUtils.isEmpty(options)) {
            sqlStringBuilder.append("AND ARRAY[ :options ]::TEXT[] <@ regexp_split_to_array(options, ',')");
        }

        Long result = jdbcOps.queryForObject(sqlStringBuilder.toString(), parameterSource, Long.class);

        if (result == null) {
            return 0;
        } else return result;
    }

    private MapSqlParameterSource getSqlParametersFromFilter(RoomFilterParams roomFilterParams) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        // Required
        String roomType = roomFilterParams.getRoomType();
        parameterSource.addValue("roomType", roomType);

        // Optional
        Integer minPrice = roomFilterParams.getMinPrice();
        Integer maxPrice = roomFilterParams.getMaxPrice();
        parameterSource.addValue("minPrice", minPrice);
        parameterSource.addValue("maxPrice", maxPrice);

        String rentType = roomFilterParams.getRentType();
        parameterSource.addValue("rentType", rentType);

        Integer minFootage = roomFilterParams.getMinFootage();
        Integer maxFootage = roomFilterParams.getMaxFootage();
        parameterSource.addValue("minFootage", minFootage);
        parameterSource.addValue("maxFootage", maxFootage);

        List<String> options = roomFilterParams.getOptions();
        if (!CollectionUtils.isEmpty(options)) {
            parameterSource.addValue("options", options);
        }

        return parameterSource;
    }

    private static class RoomSearchResultMapper implements RowMapper<RoomDto> {

        @Override
        public RoomDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            RoomDto room = new RoomDto();

            room.setUuid(rs.getString(1));
            room.setBookingType(rs.getString(2));
            room.setDescription(rs.getString(3));
            room.setFloor(rs.getString(4));
            room.setFootage(String.valueOf(rs.getInt(5)));
            room.setImageUrls(rs.getString(6));
            room.setName(rs.getString(7));
            room.setOptions(new ArrayList<>(csvAttributeConverter.convertToEntityAttribute(rs.getString(8))));
            room.setPrice(rs.getInt(9));
            room.setRentType(rs.getString(10));
            room.setRoomType(rs.getString(11));
            room.setSpaceId(rs.getString(12));

            return room;
        }

    }
}
