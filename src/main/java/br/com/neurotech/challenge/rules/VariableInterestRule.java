package br.com.neurotech.challenge.rules;

import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.entity.NeurotechClient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VariableInterestRule implements CreditModalityRule {

    @Override
    public Optional<CreditType> determineCreditType(NeurotechClient client) {
        boolean isAgeEligible = client.getAge() >=21 && client.getAge() <= 65;
        boolean isIncomeEligible = client.getIncome() >= 5000.00 && client.getIncome() <= 15000.00;

        if (isAgeEligible && isIncomeEligible) {
            return Optional.of(CreditType.VARIABLE_INTEREST);
        }
        return Optional.empty();
    }
}
