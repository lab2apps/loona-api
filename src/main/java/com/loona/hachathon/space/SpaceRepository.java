package com.loona.hachathon.space;

import com.loona.hachathon.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space, String> {

    Space findSpaceByUuid(String uuid);
    Space findSpaceByUuidAndVkUser(String uuid, User vkUser);
    List<Space> findByVkUser(User vkUser);
    List<Space> findByNameContaining(String name);
}
