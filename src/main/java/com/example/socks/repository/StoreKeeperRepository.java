package com.example.socks.repository;

import com.example.socks.model.StoreKeeper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreKeeperRepository extends JpaRepository<StoreKeeper, Integer> {
    StoreKeeper findByUserName(String username);
}
