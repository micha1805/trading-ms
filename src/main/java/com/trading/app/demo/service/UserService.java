package com.trading.app.demo.service;

import com.trading.app.demo.dtos.CurrentBalanceResponseDTO;
import com.trading.app.demo.model.Trade;
import com.trading.app.demo.model.User;
import com.trading.app.demo.model.Wire;
import com.trading.app.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public List<User> getUsers(){return userRepository.findAll();};

    public User getUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "User with Id=" + userId + " does not exist"
                ));
    }

    public void update(User user) {
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Integer getCurrentBalance(User user){
        int currentBalance = 0;

        int depositsTotal = user.getWires().stream()
                .filter(w -> w.getAmount() > 0)
                .mapToInt(Wire::getAmount)
                .sum();
        int withdrawalTotal = user.getWires().stream()
                .filter(w -> w.getAmount() <=0 )
                .mapToInt(Wire::getAmount)
                .sum();
        int cash = depositsTotal - withdrawalTotal;
        int closedProfitLoss = getClosedPnl(user);
        // I could add openPNL but for the sake of simplicity I wont.
        // if I wanted I would have to grab current symbol price for each trade (via openProfitLoss method)
        //
        // int openProfitLoss = getOpenPnl(user);
        // currentBalance = cash + closedProfitLoss + openProfitLoss;

        currentBalance = cash + closedProfitLoss;
        return currentBalance;
    }

    public User getUserFromHeader(String authHeader){
        Optional<User> currentUser = userRepository
                .findByEmail(jwtService.extractUsername(authHeader.substring(7)));

        if(currentUser.isPresent()){
            return currentUser.get();
        }else{
            throw new IllegalArgumentException("User not found");
        }
    }

    public User getUserFromHeaderWithTradesAndWires(String authHeader) {
        String email = jwtService.extractUsername(authHeader.substring(7));
        return userRepository.findByEmailWithTradesAndWires(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Transactional(readOnly = true)
    public Integer getClosedPnl(User user){
        return user.getTrades()
                .stream().filter(t -> !t.isOpen())
                .mapToInt(Trade::getClosedPNL)
                .sum();
    }

    @Transactional(readOnly = true)
    public Integer getOpenPnl(User user){
        return user.getTrades()
                .stream().filter(t -> t.isOpen())
                .mapToInt(Trade::getOpenPNL)
                .sum();
    }
}
