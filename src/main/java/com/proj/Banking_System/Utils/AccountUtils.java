package com.proj.Banking_System.Utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE="001";
    public static final String ACCOUNT_EXISTS_MSG="Account already exists";
    public static final String ACCOUNT_CREATION_SUCCESS="002";
    public static final String ACCOUNT_CREATION_Message="Account has been successfully created!";
    public static final String ACCOUNT_NOT_EXIST_CODE="003";
    public static final String ACCOUNT_NOT_EXIST_MESSAGE="User with this account number does not exist";
    public static final String ACCOUNT_FOUND_CODE="004";
    public static final String ACCOUNT_FOUND_MSG="Account has been successfully fetched!";
    public static final String ACCOUNT_CREDIT_Message="Account has been successfully credited!";
    public static final String ACCOUNT_CREDIT_Code="005";
    public static final String ACCOUNT_DEBIT_Message="Account has been successfully credited!";
    public static final String ACCOUNT_DEBIT_Code="006";

    public static String generateAccountNumber(){
        Year currentYear = Year.now();
        int min=1000000;
        int max=9999999;

        int randNumber= (int) Math.floor(Math.random()*(max-min+1)+min);

        String an=currentYear.toString()+Integer.toString(randNumber);
        return an;
    }
}
