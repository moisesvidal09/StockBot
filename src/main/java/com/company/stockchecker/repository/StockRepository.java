package com.company.stockchecker.repository;

import com.company.stockchecker.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.Set;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("SELECT DISTINCT s.code FROM Stock s")
    Optional<Set<String>> findAllDistinctCode();

}
