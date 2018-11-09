package com.loona.hachathon.room;

import com.loona.hachathon.space.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    Room findRoomByUuid(String id);
}
