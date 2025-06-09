package br.com.neurotech.challenge.rules;

import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.entity.NeurotechClient;

import java.util.Optional;

public interface CreditModalityRule {

    Optional<CreditType> determineCreditType(NeurotechClient client);
}
