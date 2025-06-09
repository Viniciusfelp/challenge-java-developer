package br.com.neurotech.challenge.rules;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;

public interface VehicleEligibilityRule {
    boolean appliesTo(VehicleModel model);
    boolean isEligible(NeurotechClient client);
}
