package com.example.shopingmall.repository;

import com.example.shopingmall.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository {
    Item save(Item item);
    Optional<Item> findByName(String name);

    Item findById(Long Id);
    List<Item> findAll();
    void delete(Long deleteId);
    void clearStore();
}
