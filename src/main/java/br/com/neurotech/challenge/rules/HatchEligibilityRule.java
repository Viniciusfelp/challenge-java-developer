package br.com.neurotech.challenge.rules;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import org.springframework.stereotype.Component;

@Component
public class HatchEligibilityRule implements VehicleEligibilityRule {
    @Override
    public boolean appliesTo(VehicleModel model) {
        return model == VehicleModel.HATCH;
    }

    @Override
    public boolean isEligible(NeurotechClient client) {
        return client.getIncome() >= 5000 && client.getIncome() <= 15000;
    }
}