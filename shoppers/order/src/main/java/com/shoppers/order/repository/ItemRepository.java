package com.shoppers.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoppers.order.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
