package com.proj.Banking_System.Services;

import com.proj.Banking_System.DTO.*;
import com.proj.Banking_System.Entity.User;
import com.proj.Banking_System.Repository.UserRepository;
import com.proj.Banking_System.Utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MSG)
                    .accountInfo(null).build();

        }
        User newUser =  User.builder().firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName()).otherName(userRequest.getOtherName())
                .gender(userRequest.getGender()).address(userRequest.getAddress())
                .stateofOrigin(userRequest.getStateofOrigin()).
                accountNumber(AccountUtils.generateAccountNumber())
                .Acount_balance(BigDecimal.ZERO).email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber()).status("ACTIVE")
                .build();

        User savedUser = userRepository.save(newUser);

        EmailDetails emailDetails=EmailDetails.builder().
                recipient(savedUser.getEmail()).subject("Account Creation").messageBody("Congratulations! your account has been successfully created.\n Your Account Details:\n" +
                        " Account Name :"+savedUser.getFirstName()+" "+savedUser.getOtherName()+" "+savedUser.getLastName()+"\n Account Number:"+savedUser.getAccountNumber()).build();
        emailService.sendEmailAlert(emailDetails);

        return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_Message).accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAcount_balance()).accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName()+" "+savedUser.getOtherName()+" "+savedUser.getLastName())
                        .build()).build();
    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest request) {
        if (userRepository.existsByAccountNumber(request.getAccountNumber())) {
            User foundUser=userRepository.findUserByAccountNumber(request.getAccountNumber());
            return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_FOUND_CODE).responseMessage(AccountUtils.ACCOUNT_EXISTS_CODE).accountInfo(AccountInfo.builder()
                    .accountBalance(foundUser.getAcount_balance()).accountNumber(request.getAccountNumber()).accountName(foundUser.getFirstName()+" "+foundUser.getOtherName()+" "+foundUser.getLastName()).build()).build();
        }
        return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE).responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                .accountInfo(null).build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest request) {
        if (userRepository.existsByAccountNumber(request.getAccountNumber())) {
            User foundUser=userRepository.findUserByAccountNumber(request.getAccountNumber());
            return foundUser.getFirstName()+" "+foundUser.getOtherName()+" "+foundUser.getLastName();
        }
        return BankResponse.builder().responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                .build().toString();
    }

    @Override
    public BankResponse creditAccount(CrediDebitRequest request) {
        User userToCredit=userRepository.findUserByAccountNumber(request.getAccountNumber());
        if (userToCredit!=null){
            userToCredit.setAcount_balance(userToCredit.getAcount_balance().add(request.getAmount()));
            userRepository.save(userToCredit);
            return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_CREDIT_Code).responseMessage(AccountUtils.ACCOUNT_CREDIT_Message).accountInfo(AccountInfo.builder()
                    .accountName(userToCredit.getFirstName()+" "+userToCredit.getOtherName()+" "+userToCredit.getLastName()).accountNumber(userToCredit.getAccountNumber()).accountBalance(userToCredit.getAcount_balance()).build()).build();
        }
        return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE).responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                .accountInfo(null).build();
    }

    @Override
    public BankResponse debitAccount(CrediDebitRequest request) {
        User userToDebit=userRepository.findUserByAccountNumber(request.getAccountNumber());
        if (userToDebit!=null){
            if(userToDebit.getAcount_balance().subtract(request.getAmount()).compareTo(BigDecimal.ZERO)>=0){
                userToDebit.setAcount_balance(userToDebit.getAcount_balance().subtract(request.getAmount()));
                userRepository.save(userToDebit);
                return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_DEBIT_Code).responseMessage(AccountUtils.ACCOUNT_DEBIT_Message).accountInfo(AccountInfo.builder()
                        .accountName(userToDebit.getFirstName()+" "+userToDebit.getOtherName()+" "+userToDebit.getLastName()).accountNumber(userToDebit.getAccountNumber()).accountBalance(userToDebit.getAcount_balance()).build()).build();
            }else {
                return BankResponse.builder().responseCode("007").responseMessage("Insufficient Account Balance")
                        .accountInfo(AccountInfo.builder()
                                .accountName(userToDebit.getFirstName()+" "+userToDebit.getOtherName()+" "+userToDebit.getLastName()).accountNumber(userToDebit.getAccountNumber()).accountBalance(userToDebit.getAcount_balance()).build()).build();
            }
        }
        return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE).responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                .accountInfo(null).build();
    }

    @Override
    public BankResponse transfer(TransferRequest request) {
        boolean isDestinationAccount=userRepository.existsByAccountNumber(request.getDestinationAccountNumber());
        if (!isDestinationAccount){
            return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE).responseMessage("Destination Account Not Exist")
                    .accountInfo(null).build();
        }
        User sourceUser=userRepository.findUserByAccountNumber(request.getSourceAccountNumber());
        User destinationUser=userRepository.findUserByAccountNumber(request.getDestinationAccountNumber());
        if (!(request.getAmount().compareTo(sourceUser.getAcount_balance())>0)){
          sourceUser.setAcount_balance(sourceUser.getAcount_balance().subtract(request.getAmount()));
          userRepository.save(sourceUser);
          destinationUser.setAcount_balance(destinationUser.getAcount_balance().add(request.getAmount()));
          userRepository.save(destinationUser);
          EmailDetails debitAlert=EmailDetails.builder().recipient(sourceUser.getEmail()).subject("Account Debited").messageBody("Rs."+request.getAmount()+" has been debited from your Account :"+sourceUser.getAccountNumber()+
                  "\nYour current balance is :"+sourceUser.getAcount_balance()+"\nTransfered to: "+destinationUser.getFirstName()+" "+destinationUser.getOtherName()+" "+destinationUser.getLastName()+"\n"+"Account Number :"+destinationUser.getAccountNumber()).build();
          EmailDetails creditAlert=EmailDetails.builder().subject("Account Credited").recipient(destinationUser.getEmail()).messageBody("Your Account has been credited for Rs."+request.getAmount()+"\n" +"Account Number :"+destinationUser.getAccountNumber()+"\n"+"Current Balance :"+destinationUser.getAcount_balance()).build();
          emailService.sendEmailAlert(debitAlert);
          emailService.sendEmailAlert(creditAlert);
          return BankResponse.builder().responseMessage("Transfer Successful|| Your current Balance is "+sourceUser.getAcount_balance()).build();
        }
        return BankResponse.builder().responseCode("007").responseMessage("Insufficient Account Balance "+"Required Defficiet Amount :"+(request.getAmount().subtract(sourceUser.getAcount_balance())))
                .accountInfo(AccountInfo.builder()
                        .accountName(sourceUser.getFirstName()+" "+sourceUser.getOtherName()+" "+sourceUser.getLastName()).accountNumber(sourceUser.getAccountNumber()).accountBalance(sourceUser.getAcount_balance()).build()).build();

    }


}
