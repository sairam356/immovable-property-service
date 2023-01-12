package com.immovable.propertyservice.controller;

import com.immovable.propertyservice.dto.WalletTransactionDto;
import com.immovable.propertyservice.entities.Wallet;
import com.immovable.propertyservice.entities.WalletTransaction;
import com.immovable.propertyservice.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Wallet saveWallet(@RequestBody Wallet wallet){
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
}
