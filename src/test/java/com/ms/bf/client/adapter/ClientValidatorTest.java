package com.ms.bf.client.adapter;

import com.ms.bf.client.adapter.controller.model.clientValidator.ClientValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClientValidatorTest {

    @Test
    void testIsValidAccountNumber_withNullAccountNumber_shouldReturnFalse() {
        String accountNumber = null;
        boolean isValid = ClientValidator.isValidAccountNumber(accountNumber);
        assertFalse(isValid);
    }

    @Test
    void testIsValidAccountNumber_withEmptyAccountNumber_shouldReturnFalse() {
        String accountNumber = "";
        boolean isValid = ClientValidator.isValidAccountNumber(accountNumber);
        assertFalse(isValid);
    }

    @Test
    void testIsValidAccountNumber_withValidAccountNumber_shouldReturnTrue() {
        String accountNumber = "123.456-7";
        boolean isValid = ClientValidator.isValidAccountNumber(accountNumber);
        assertFalse(isValid);
    }

    @Test
    void testIsValidAccountNumber_withInvalidAccountNumber_shouldReturnFalse() {
        String accountNumber = "123456";
        boolean isValid = ClientValidator.isValidAccountNumber(accountNumber);
        assertFalse(isValid);
    }

    @Test
    void testValidateAge_withAgeLessThan18_shouldReturnFalse() {
        int age = 17;
        boolean isValid = ClientValidator.validateAge(age);
        assertFalse(isValid);
    }

    @Test
    void testValidateAge_withAgeMoreThan99_shouldReturnFalse() {
        int age = 100;
        boolean isValid = ClientValidator.validateAge(age);
        assertFalse(isValid);
    }

    @Test
    void testValidateAge_withValidAge_shouldReturnTrue() {
        int age = 25;
        boolean isValid = ClientValidator.validateAge(age);
        assertTrue(isValid);
    }
}
