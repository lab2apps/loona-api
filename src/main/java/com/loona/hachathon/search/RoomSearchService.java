package com.loona.hachathon.search;

import com.loona.hachathon.room.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RoomSearchService {

    @Autowired
    private RoomSearchRepository roomSearchRepository;

    public RoomSearchResultDto search(RoomFilterParams roomFilterParams) {
        List<Room> foundRooms = roomSearchRepository.search(roomFilterParams);

        if (foundRooms.isEmpty()) return new RoomSearchResultDto(0, 0, Collections.emptyList());

        long totalCount = roomSearchRepository.getTotalCount(roomFilterParams);

        return new RoomSearchResultDto(totalCount, foundRooms.size(), foundRooms);
    }

}
