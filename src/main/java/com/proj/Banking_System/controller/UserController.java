package com.proj.Banking_System.controller;

import com.proj.Banking_System.DTO.*;
import com.proj.Banking_System.Services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/user")
@Tag(name="User Account Management API's")
public class UserController {


    @Autowired
    UserService userService;

    @Operation(
            summary = "Create New User Account",
            description = "Creating a new user and assigning an account ID"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status 201 Created"
    )
    @PostMapping
    public BankResponse createAccount(@RequestBody UserRequest userRequest){
        return  userService.createAccount(userRequest);
    }
    @PostMapping("/login")
    public BankResponse login(@RequestBody LoginDto loginDto){
        return userService.login(loginDto);
    }
    @Operation(
            summary = "Balance Enquiry",
            description = "Check account balance by providing account number"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 SUCCESS"
    )
    @GetMapping("/balanceEnquiry")
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest request){
        return userService.balanceEnquiry(request);
    }
    @GetMapping("/nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest request){
        return userService.nameEnquiry(request);
    }
    @PostMapping("/creditACcountBalance")
    public BankResponse creditBalance(@RequestBody CrediDebitRequest request){
        return userService.creditAccount(request);
    }
    @PostMapping("/debitACcountBalance")
    public BankResponse debitBalance(@RequestBody CrediDebitRequest request){
        return userService.debitAccount(request);
    }

    @PostMapping("/transferMoney")
    public BankResponse transferMoney(@RequestBody TransferRequest request){
       return userService.transfer(request);
    }
}
