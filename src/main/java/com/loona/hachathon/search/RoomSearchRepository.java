package com.loona.hachathon.search;

import com.loona.hachathon.room.Room;
import com.loona.hachathon.util.CsvAttributeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class RoomSearchRepository {

    @Autowired
    private NamedParameterJdbcOperations jdbcOps;

    private static final RoomSearchResultMapper roomSearchResultMapper = new RoomSearchResultMapper();
    private static final CsvAttributeConverter csvAttributeConverter = new CsvAttributeConverter();

    public List<Room> search(RoomFilterParams roomFilterParams) {
        MapSqlParameterSource parameterSource = getSqlParametersFromFilter(roomFilterParams);

        // Additional page parameters
        parameterSource.addValue("offset", roomFilterParams.getPage() * roomFilterParams.getPageSize());
        parameterSource.addValue("limit", roomFilterParams.getPageSize());

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("SELECT * FROM room ")
                .append("WHERE start_work_time >= :startWorkTime AND end_work_time <= :endWorkTime ");

        if (!roomFilterParams.getRoomType().isEmpty()) {
            sqlStringBuilder.append("AND room_type = :roomType ");
        }

        if (!roomFilterParams.getRentType().isEmpty()) {
            sqlStringBuilder.append("AND rent_type = :rentType ");
        }

        if (!roomFilterParams.getName().isEmpty()) {
            sqlStringBuilder.append("AND name = :name ");
        }

        sqlStringBuilder.append("AND price >= :minPrice AND price <= :maxPrice ")
                .append("OFFSET :offset LIMIT :limit");

        return jdbcOps.query(sqlStringBuilder.toString(), parameterSource, roomSearchResultMapper);
    }

    public long getTotalCount(RoomFilterParams roomFilterParams) {
        MapSqlParameterSource parameterSource = getSqlParametersFromFilter(roomFilterParams);

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("SELECT count(*) FROM room ")
                .append("WHERE start_work_time >= :startWorkTime AND end_work_time <= :endWorkTime ");

        if (!roomFilterParams.getRoomType().isEmpty()) {
            sqlStringBuilder.append("AND room_type = :roomType ");
        }

        if (!roomFilterParams.getRentType().isEmpty()) {
            sqlStringBuilder.append("AND rent_type = :rentType ");
        }

        if (!roomFilterParams.getName().isEmpty()) {
            sqlStringBuilder.append("AND name = :name ");
        }

        sqlStringBuilder.append("AND price >= :minPrice AND price <= :maxPrice ");

        Long result = jdbcOps.queryForObject(sqlStringBuilder.toString(), parameterSource, Long.class);

        if (result == null) {
            return 0;
        } else return result;
    }

    private MapSqlParameterSource getSqlParametersFromFilter(RoomFilterParams roomFilterParams) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        // Required
        LocalDateTime startWorkTime = roomFilterParams.getStartWorkTime();
        LocalDateTime endWorkTime = roomFilterParams.getEndWorkTime();

        parameterSource.addValue("startWorkTime", startWorkTime);
        parameterSource.addValue("endWorkTime", endWorkTime);

        // Optional
        String roomType = roomFilterParams.getRoomType();
        if (!roomType.isEmpty()) {
            parameterSource.addValue("roomType", roomType);
        }

        String rentType = roomFilterParams.getRentType();
        if (!rentType.isEmpty()) {
            parameterSource.addValue("rentType", rentType);
        }

        String name = roomFilterParams.getName();
        if (!name.isEmpty()) {
            parameterSource.addValue("name", name);
        }

        Integer minPrice = roomFilterParams.getMinPrice();
        Integer maxPrice = roomFilterParams.getMaxPrice();

        parameterSource.addValue("minPrice", minPrice);
        parameterSource.addValue("maxPrice", maxPrice);

        return parameterSource;
    }

    private static class RoomSearchResultMapper implements RowMapper<Room> {

        @Override
        public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
            Room room = new Room();

            room.setUuid(rs.getString(1));
            room.setDescription(rs.getString(2));
            room.setEndWorkTime(rs.getTimestamp(3).toLocalDateTime());
            room.setImageUrls(csvAttributeConverter.convertToEntityAttribute(rs.getString(4)));
            room.setName(rs.getString(5));
            room.setPrice(rs.getInt(6));
            room.setRentType(rs.getString(7));
            room.setRoomType(rs.getString(8));
            room.setStartWorkTime(rs.getTimestamp(9).toLocalDateTime());

            return room;
        }

    }
}
