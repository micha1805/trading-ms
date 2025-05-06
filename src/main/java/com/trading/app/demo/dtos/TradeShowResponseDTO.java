package com.trading.app.demo.dtos;

import com.trading.app.demo.model.Trade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeShowResponseDTO {
    private Trade trade;
}
