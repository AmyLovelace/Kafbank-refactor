package com.ms.bf.card.adapter.controller.model.CardValidator;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CardValidator {
    private static final String ACCOUNT_NUMBER_REGEX =
            "^[0-9]{1,2}(\\.[0-9]{3})*-[0-9Kk]$";

    private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile(ACCOUNT_NUMBER_REGEX);


    public static boolean isValidAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            return false;
        }

        Matcher matcher = ACCOUNT_NUMBER_PATTERN.matcher(accountNumber);

        if (!matcher.matches()) {
            return false;
        }
        return  true;
    }

    public static boolean validateAge(int age) {
        return age >= 18 && age <= 99;

    }


}
