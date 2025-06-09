package br.com.neurotech.challenge.rules;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HatchEligibilityRuleTest {

    private HatchEligibilityRule rule;

    @BeforeEach
    void setUp() {
        rule = new HatchEligibilityRule();
    }

    @Test
    @DisplayName("Should apply to HATCH model")
    void applyToHatchModel() {
        assertTrue(rule.appliesTo(VehicleModel.HATCH));
    }

    @Test
    @DisplayName("Should not apply to SUV model")
    void notApplyToSuvModel() {
        assertFalse(rule.appliesTo(VehicleModel.SUV));
    }

    @Test
    @DisplayName("Should be eligible when income is 5000")
    void isEligibleCase1() {
        NeurotechClient client = new NeurotechClient("1", "Vinícius", 24, 5000.0);
        assertTrue(rule.isEligible(client));
    }

    @Test
    @DisplayName("Should be eligible when income is 15000")
    void isEligibleCase2() {
        NeurotechClient client = new NeurotechClient("1", "Vinícius", 24, 15000.0);
        assertTrue(rule.isEligible(client));
    }

    @Test
    @DisplayName("Should be eligible when income is between 5000 and 15000")
    void isEligibleCase3() {
        NeurotechClient client = new NeurotechClient("1", "Vinícius", 24, 10000.0);
        assertTrue(rule.isEligible(client));
    }

    @Test
    @DisplayName("Should not be eligible when income is less than 5000")
    void notEligibleCase1() {
        NeurotechClient client = new NeurotechClient("1", "Vinícius", 24, 4999.99);
        assertFalse(rule.isEligible(client));
    }

    @Test
    @DisplayName("Should not be eligible when income is greater than 15000")
    void notEligibleCase2() {
        NeurotechClient client = new NeurotechClient("1", "Vinícius", 24, 15000.01);
        assertFalse(rule.isEligible(client));
    }
}