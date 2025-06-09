package br.com.neurotech.challenge.rules;

import br.com.neurotech.challenge.entity.NeurotechClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FixedInterestRuleTest {

    private FixedInterestRule rule;

    @BeforeEach
    void setUp() {
        rule = new FixedInterestRule();
    }

    @Test
    @DisplayName("Should return FIXED_INTEREST when age is 18")
    void isEligibleCase1() {
        NeurotechClient client = new NeurotechClient("1", "Alan", 18, 5000.0);
        assertTrue(rule.determineCreditType(client).isPresent());
    }

    @Test
    @DisplayName("Should return FIXED_INTEREST when age is 25")
    void isEligibleCase2() {
        NeurotechClient client = new NeurotechClient("1", "Karol", 25, 5000.0);
        assertTrue(rule.determineCreditType(client).isPresent());
    }

    @Test
    @DisplayName("Should return FIXED_INTEREST when age is 22")
    void isEligibleCase3() {
        NeurotechClient client = new NeurotechClient("1", "Vinícius", 22, 5000.0);
        assertTrue(rule.determineCreditType(client).isPresent());
    }

    @Test
    @DisplayName("Should return empty when age is 17")
    void notEligibleCase1() {
        NeurotechClient client = new NeurotechClient("1", "Carlos", 17, 5000.0);
        assertTrue(rule.determineCreditType(client).isEmpty());
    }

    @Test
    @DisplayName("Should return empty when age is 26")
    void notEligibleCase2() {
        NeurotechClient client = new NeurotechClient("1", "Beatriz", 26, 5000.0);
        assertTrue(rule.determineCreditType(client).isEmpty());
    }
}