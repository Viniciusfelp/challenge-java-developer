package br.com.neurotech.challenge.rules;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SuvEligibilityRuleTest {

    private SuvEligibilityRule rule;

    @BeforeEach
    void setUp() {
        rule = new SuvEligibilityRule();
    }

    @Test
    @DisplayName("Should apply to SUV model")
    void applyToSuvModel() {
        assertTrue(rule.appliesTo(VehicleModel.SUV));
    }

    @Test
    @DisplayName("Should not apply to HATCH model")
    void notApplyToHatchModel() {
        assertFalse(rule.appliesTo(VehicleModel.HATCH));
    }

    @Test
    @DisplayName("Should be eligible when income is greater than 8000 and age is greater than 20")
    void isEligibleCase1() {
        NeurotechClient client = new NeurotechClient("1", "Vinícius", 21, 8001.0);
        assertTrue(rule.isEligible(client));
    }

    @Test
    @DisplayName("Should not be eligible when income is 8000")
    void isEligibleCase2() {
        NeurotechClient client = new NeurotechClient("1", "Vinícius", 21, 8000.0);
        assertFalse(rule.isEligible(client));
    }

    @Test
    @DisplayName("Should not be eligible when income is less than 8000")
    void notEligibleCase1() {
        NeurotechClient client = new NeurotechClient("1", "Vinícius", 21, 7999.99);
        assertFalse(rule.isEligible(client));
    }

    @Test
    @DisplayName("Should not be eligible when age is 20")
    void notEligibleCase2() {
        NeurotechClient client = new NeurotechClient("1", "Vinícius", 20, 9000.0);
        assertFalse(rule.isEligible(client));
    }

    @Test
    @DisplayName("Should not be eligible when age is less than 20")
    void notEligibleCase3() {
        NeurotechClient client = new NeurotechClient("1", "Vinícius", 19, 9000.0);
        assertFalse(rule.isEligible(client));
    }

    @Test
    @DisplayName("Should not be eligible when both income and age are below threshold")
    void notEligibleCase4() {
        NeurotechClient client = new NeurotechClient("1", "Vinícius", 19, 7999.99);
        assertFalse(rule.isEligible(client));
    }
}