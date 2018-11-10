package com.loona.hachathon.room;

import com.loona.hachathon.space.Space;
import com.loona.hachathon.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    Room findRoomByUuid(String id);
    Room findRoomByUuidAndVkUser(String id, User vkUser);
    List<Room> findByVkUser(User vkUser);
}
