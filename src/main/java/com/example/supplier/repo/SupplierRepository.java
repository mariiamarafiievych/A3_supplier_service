package com.example.supplier.repo;


import com.example.supplier.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
    @Query("SELECT s FROM Supplier s WHERE s.firstName = :firstName and s.lastName = :lastName")
    Supplier findSupplierByName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
