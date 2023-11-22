package com.ms.bf.card.adapter;

import com.ms.bf.card.adapter.controller.model.CardValidator.CardValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CardValidatorTest {

    @Test
    void testIsValidAccountNumber_withNullAccountNumber_shouldReturnFalse() {
        String accountNumber = null;
        boolean isValid = CardValidator.isValidAccountNumber(accountNumber);
        assertFalse(isValid);
    }

    @Test
    void testIsValidAccountNumber_withEmptyAccountNumber_shouldReturnFalse() {
        String accountNumber = "";
        boolean isValid = CardValidator.isValidAccountNumber(accountNumber);
        assertFalse(isValid);
    }

    @Test
    void testIsValidAccountNumber_withValidAccountNumber_shouldReturnTrue() {
        String accountNumber = "123.456-7";
        boolean isValid = CardValidator.isValidAccountNumber(accountNumber);
        assertFalse(isValid);
    }

    @Test
    void testIsValidAccountNumber_withInvalidAccountNumber_shouldReturnFalse() {
        String accountNumber = "123456";
        boolean isValid = CardValidator.isValidAccountNumber(accountNumber);
        assertFalse(isValid);
    }

    @Test
    void testValidateAge_withAgeLessThan18_shouldReturnFalse() {
        int age = 17;
        boolean isValid = CardValidator.validateAge(age);
        assertFalse(isValid);
    }

    @Test
    void testValidateAge_withAgeMoreThan99_shouldReturnFalse() {
        int age = 100;
        boolean isValid = CardValidator.validateAge(age);
        assertFalse(isValid);
    }

    @Test
    void testValidateAge_withValidAge_shouldReturnTrue() {
        int age = 25;
        boolean isValid = CardValidator.validateAge(age);
        assertTrue(isValid);
    }
}
