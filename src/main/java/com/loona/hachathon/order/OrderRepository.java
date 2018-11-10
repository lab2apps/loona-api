package com.loona.hachathon.order;

import com.loona.hachathon.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    Order findOrderByUuid(String uuid);
    List<Order> findAllByVkUser(User vkUser);
}
