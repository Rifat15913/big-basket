package com.bigbasket.app.repository;

import com.bigbasket.app.model.Cart;
import com.bigbasket.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
}
