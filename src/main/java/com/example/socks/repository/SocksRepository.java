package com.example.socks.repository;

import com.example.socks.model.Socks;
import com.example.socks.model.SocksId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocksRepository extends JpaRepository<Socks, SocksId> {

    List<Socks> findBySocksIdColorAndSocksIdCottonPartAfter(String color, byte cottonPart);
    List<Socks> findBySocksIdColorAndSocksIdCottonPartBefore(String color, byte cottonPart);
    List<Socks> findBySocksIdColorAndSocksIdCottonPart(String color, byte cottonPart);
}
