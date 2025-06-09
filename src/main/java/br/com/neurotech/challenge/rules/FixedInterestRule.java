package br.com.neurotech.challenge.rules;

import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.entity.NeurotechClient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FixedInterestRule implements CreditModalityRule {

    @Override
    public Optional<CreditType> determineCreditType(NeurotechClient client) {
        if(client.getAge() >=18 && client.getAge() <=25) {
            return Optional.of(CreditType.FIXED_INTEREST);
        }
        return Optional.empty();
    }
}
