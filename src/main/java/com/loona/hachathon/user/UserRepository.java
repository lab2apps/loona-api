package com.loona.hachathon.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findUserById(String id);
    User findUserBySignedId(String signedId);
}
