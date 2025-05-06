package com.trading.app.demo.repository;

import com.trading.app.demo.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    @Query(
            value = "SELECT * FROM trades WHERE user_id=?1 AND open=true;",
            nativeQuery = true
    )
    List<Trade> getOpenTrades(Long userId);

    @Query(
            value = "SELECT * FROM trades WHERE user_id=?1 AND open=false;",
            nativeQuery = true
    )
    List<Trade> getClosedTrades(Long userId);
}
