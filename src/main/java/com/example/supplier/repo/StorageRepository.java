package com.example.supplier.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.supplier.entities.Item;
import com.example.supplier.entities.Storage;

import java.util.UUID;

public interface StorageRepository extends JpaRepository<Storage, UUID> {
    @Query("SELECT s FROM Storage s WHERE s.item = :item")
    Storage findByItem(@Param("item") Item item);
}
