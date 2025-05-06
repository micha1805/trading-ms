package com.trading.app.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trading.app.demo.service.TradeService;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trades")
public class Trade {

    //FIELDS
    @Id
    @SequenceGenerator(
            name = "trade_sequence_generator",
            sequenceName = "trade_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "trade_sequence_generator"
    )
    private long id;
    private String symbol;
    private Integer quantity;
    private Integer openPriceInCent;
    private Integer closePriceInCent;
    private LocalDateTime openDateTime;
    private LocalDateTime closeDateTime;
    private boolean open;

    @JsonIgnore
    public int getClosedPNL() throws IllegalArgumentException{
        if(open) throw new IllegalArgumentException("trade still open, cannot calculate closed PnL");
        return closePriceInCent - openPriceInCent;
    }

    @JsonIgnore
    public int getOpenPNL() throws IllegalArgumentException{
        if(!open) throw new IllegalArgumentException("trade is closed, cannot calculate open PnL");
        return TradeService.getStockPriceNow(this.getSymbol()) - openPriceInCent;
    }

    // RELATIONSHIPS
    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude // prevent infinite loops
    @JsonIgnore
    private User user;

}
