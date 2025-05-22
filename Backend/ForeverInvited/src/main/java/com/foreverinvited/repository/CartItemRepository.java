package com.foreverinvited.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foreverinvited.model.CartItem;
import com.foreverinvited.model.User;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

    List<CartItem> findByUser(User user);
    void deleteByUser(User user);
}
