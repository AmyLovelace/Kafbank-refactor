package com.ms.bf.client.adapter.controller.model.clientValidator;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ClientValidator {
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

    public static boolean validateName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }

        if (!name.matches("[a-zA-Z ]+")) {
            return false;
        }

        return true;
    }

    public static boolean validateMembership(String membership) {
        if (membership == null || membership.isEmpty()) {
            return false;
        }

        if (!membership.matches("^(Debit|Credit|debit|credit|DEBIT|CREDIT)$")) {
            return false;
        }

        return true;
    }




}
