package com.immovable.investmentplatform.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.immovable.investmentplatform.dto.WalletDto;
import com.immovable.investmentplatform.dto.WalletTransactionDto;
import com.immovable.investmentplatform.entities.Wallet;
import com.immovable.investmentplatform.entities.WalletTransaction;
import com.immovable.investmentplatform.services.WalletService;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Wallet saveWallet(@RequestBody WalletDto wallet){
        return walletService.save(wallet);
    }

    @PostMapping("/transaction")
    @ResponseStatus(HttpStatus.CREATED)
    public WalletTransaction saveTransaction(@RequestBody WalletTransactionDto walletTransactionDto){
        return walletService.saveTransaction(walletTransactionDto);
    }

    @GetMapping("/getInfo/{customerId}")
    public Wallet getWalletInfo(@PathVariable("customerId") String customerId){
        return walletService.getWalletInfo(customerId);
    }

    @GetMapping("/walletBalance/{customerId}")
    public Map<String,BigDecimal> getWalletBalance(@PathVariable("customerId") String customerId){
        return walletService.getBalance(customerId);
    }

    @GetMapping("/verifyAmount")
    public boolean verify(@RequestBody WalletTransactionDto walletTransactionDto){
        return walletService.isValidTransaction(walletTransactionDto);
    }
}
