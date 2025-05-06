package com.trading.app.demo.controller;

import com.trading.app.demo.dtos.*;
import com.trading.app.demo.model.Trade;
import com.trading.app.demo.model.User;
import com.trading.app.demo.repository.TradeRepository;
import com.trading.app.demo.repository.UserRepository;
import com.trading.app.demo.service.TradeService;
import com.trading.app.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/trade")
public class TradeController {

    private final UserService userService;
    private final TradeService tradeService;
    private final TradeRepository tradeRepository;
    private final UserRepository userRepository;
    // insert user Id in request
    @GetMapping(path = "/index")
    public ResponseEntity<TradeIndexResponseDTO> tradeIndex(@RequestHeader("Authorization") String authHeader){

        User user = userService.getUserFromHeader(authHeader);
        Set<Trade> trades = user.getTrades();

        TradeIndexResponseDTO response = TradeIndexResponseDTO.builder()
                .trades(new ArrayList<>(trades))
                .build();

        return ResponseEntity.ok(response);

    }

    // check that user is authorised to check that trade
    @GetMapping(path = "/{tradeId}")
    public ResponseEntity<TradeShowResponseDTO> getTrade(@RequestHeader("Authorization") String authHeader, @PathVariable String tradeId){
        User user = userService.getUserFromHeader(authHeader);
        Long tradeIdLong = Long.parseLong(tradeId);
        Trade trade = tradeRepository.findById(tradeIdLong)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Trade of id " + tradeIdLong + "not found"
                ));
        // validate tradeId if currentUser == trade.user
        if(trade.getUser().getId().equals(user.getId())){
            return ResponseEntity.ok(TradeShowResponseDTO.builder().trade(trade).build());
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping(path = "/index/open")
    public ResponseEntity<OpenTradesResponseDTO> getAllOpenTrades(@RequestHeader("Authorization") String authHeader){
        User user = userService.getUserFromHeader(authHeader);

        List<Trade> trades = tradeRepository.getOpenTrades(user.getId());

        return ResponseEntity.ok(OpenTradesResponseDTO.builder().trades(trades).build());
    }

    @GetMapping(path = "/index/closed")
    public ResponseEntity<OpenTradesResponseDTO> getAllClosedTrades(@RequestHeader("Authorization") String authHeader){
        User user = userService.getUserFromHeader(authHeader);

        List<Trade> trades = tradeRepository.getClosedTrades(user.getId());

        return ResponseEntity.ok(OpenTradesResponseDTO.builder().trades(trades).build());
    }

    @PostMapping(path = "/openTrade")
    public ResponseEntity<String> createTrade(@RequestBody TradePostDTO newTrade, @RequestHeader("Authorization") String authHeader){
        User user = userService.getUserFromHeader(authHeader);
        Integer balance = userService.getCurrentBalance(user);

        // get stock current price
        Integer stockPrice = TradeService.getStockPriceNow(newTrade.getSymbol());

        // check balance in the validation

        Integer newTradeValue = newTrade.getQuantity();

        if(balance < stockPrice* newTrade.getQuantity()){
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();
        }else{
            Trade trade = Trade.builder()
                    .quantity(newTrade.getQuantity())
                    .symbol(newTrade.getSymbol())
                    .open(true)
                    .openDateTime(LocalDateTime.now())
                    .openPriceInCent(stockPrice *100)
                    .user(user)
                    .build();
            tradeRepository.save(trade);
            return ResponseEntity.ok("Trade created successfully");
        }
    }


    @PatchMapping(path = "/closeTrade/{tradeId}")
    public ResponseEntity<String> closeTrade(@PathVariable String tradeId, @RequestHeader("Authorization") String authHeader){
        // validate trade belongs to user
        User user = userService.getUserFromHeader(authHeader);

        Trade trade = tradeRepository.findById(Long.parseLong(tradeId))
                .orElseThrow(() -> new IllegalArgumentException("Trade not found"));
        trade.setCloseDateTime(LocalDateTime.now());
        trade.setOpen(false);
        // following times 100 because price in cent
        trade.setClosePriceInCent(TradeService.getStockPriceNow(trade.getSymbol())*100);
        tradeRepository.save(trade);

        return ResponseEntity.ok("Trade closed successfully");
    }


    @GetMapping(path = "/closedPNL")
    @Transactional(readOnly = true)
    public ResponseEntity<ClosedPNLResponseDTO> closedPNL(@RequestHeader("Authorization") String authHeader){
        User user = userService.getUserFromHeaderWithTradesAndWires(authHeader);
        Integer closedPNL = userService.getClosedPnl(user);

        return ResponseEntity.ok(ClosedPNLResponseDTO.builder().closedPnlInCent(closedPNL*100).build());
    }

    @GetMapping(path = "/openPNL")
    @Transactional(readOnly = true)
    public ResponseEntity<OpenPNLResponseDTO> openPNL(@RequestHeader("Authorization") String authHeader){
        User user = userService.getUserFromHeaderWithTradesAndWires(authHeader);
        Integer openPNL = userService.getOpenPnl(user);

        return ResponseEntity.ok(OpenPNLResponseDTO.builder().openPnlInCent(openPNL*100).build());
    }


}
