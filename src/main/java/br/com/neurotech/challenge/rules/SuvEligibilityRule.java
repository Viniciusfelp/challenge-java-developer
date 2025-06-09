package br.com.neurotech.challenge.rules;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import org.springframework.stereotype.Component;

@Component
public class SuvEligibilityRule implements VehicleEligibilityRule{

    @Override
    public boolean appliesTo(VehicleModel model) {
        return VehicleModel.SUV.equals(model);
    }

    @Override
    public boolean isEligible(NeurotechClient client) {
        return client.getIncome() > 8000 && client.getAge() > 20;
    }
}
