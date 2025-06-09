package br.com.neurotech.challenge.rules;

import br.com.neurotech.challenge.entity.NeurotechClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class VariableInterestRuleTest {

    private VariableInterestRule rule;

    @BeforeEach
    void setUp() {
        rule = new VariableInterestRule();
    }

    @Test
    @DisplayName("Should return VARIABLE_INTEREST when age and income are eligible")
    void isEligibleCase1() {
        NeurotechClient client = new NeurotechClient("1", "Airton", 30, 10000.0);
        assertTrue(rule.determineCreditType(client).isPresent());
    }

    @Test
    @DisplayName("Should return VARIABLE_INTEREST on exact lower boundaries")
    void isEligibleCase2() {
        NeurotechClient client = new NeurotechClient("1", "Cristiane", 21, 5000.0);
        assertTrue(rule.determineCreditType(client).isPresent());
    }

    @Test
    @DisplayName("Should return VARIABLE_INTEREST on exact upper boundaries")
    void isEligibleCase3() {
        NeurotechClient client = new NeurotechClient("1", "José", 65, 15000.0);
        assertTrue(rule.determineCreditType(client).isPresent());
    }

    @Test
    @DisplayName("Should return empty when age is below range")
    void notEligibleCase1() {
        NeurotechClient client = new NeurotechClient("1", "Vinícius", 20, 10000.0);
        assertTrue(rule.determineCreditType(client).isEmpty());
    }

    @Test
    @DisplayName("Should return empty when age is above range")
    void notEligibleCase2() {
        NeurotechClient client = new NeurotechClient("1", "Zuleide", 66, 10000.0);
        assertTrue(rule.determineCreditType(client).isEmpty());
    }

    @Test
    @DisplayName("Should return empty when income is below range")
    void notEligibleCase3() {
        NeurotechClient client = new NeurotechClient("1", "Luiza", 30, 4999.99);
        assertTrue(rule.determineCreditType(client).isEmpty());
    }

    @Test
    @DisplayName("Should return empty when income is above range")
    void notEligibleCase4() {
        NeurotechClient client = new NeurotechClient("1", "Vitoria", 30, 15000.01);
        assertTrue(rule.determineCreditType(client).isEmpty());
    }
}