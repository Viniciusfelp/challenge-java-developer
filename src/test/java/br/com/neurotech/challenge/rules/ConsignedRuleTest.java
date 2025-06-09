package br.com.neurotech.challenge.rules;

import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.entity.NeurotechClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ConsignedRuleTest {

    private ConsignedRule rule;

    @BeforeEach
    void setUp() {
        rule = new ConsignedRule();
    }

    @Test
    @DisplayName("Should return CONSIGNED when age is greater than 65")
    void isEligibleCase1() {

        NeurotechClient client = new NeurotechClient("1", "Vinícius", 66, 5000.0);

        Optional<CreditType> result = rule.determineCreditType(client);

        assertTrue(result.isPresent());
        assertEquals(CreditType.CONSIGNED, result.get());
    }

    @Test
    @DisplayName("Should return empty when age is exactly 65")
    void notEligibleCase1() {

        NeurotechClient client = new NeurotechClient("1", "Vinícius", 65, 5000.0);

        Optional<CreditType> result = rule.determineCreditType(client);

        assertTrue(result.isEmpty());
    }
}