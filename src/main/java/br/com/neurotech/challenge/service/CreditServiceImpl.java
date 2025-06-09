package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.DTOs.ClientIncomeResponseDTO;
import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.repository.NeurotechClientRepository;
import br.com.neurotech.challenge.rules.CreditModalityRule;
import br.com.neurotech.challenge.rules.VehicleEligibilityRule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private static final int    MIN_AGE    = 23;
    private static final int    MAX_AGE    = 49;
    private static final double MIN_INCOME = 5000.00;
    private static final double MAX_INCOME = 15000.00;

    private final ClientService clientService;
    private final NeurotechClientRepository clientRepository;
    private final List<VehicleEligibilityRule> vehicleEligibilityRules;
    private final List<CreditModalityRule> creditModalityRules;

    @Override
    public boolean checkCredit(String clientId, VehicleModel model) {
        var client = clientService.getById(clientId);
        return checkCredit(client, model);
    }

    public boolean checkCredit(NeurotechClient client, VehicleModel model) {

        return vehicleEligibilityRules.stream()
                .filter(rule -> rule.appliesTo(model))
                .findFirst()
                .orElseThrow(() ->  new IllegalArgumentException("No eligibility rule found for model: " + model))
                .isEligible(client);
    }

    @Override
    public List<CreditType> determineCreditType(NeurotechClient client) {
        return creditModalityRules.stream()
                .map(rule -> rule.determineCreditType(client))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ClientIncomeResponseDTO> findEligibleClientsForHatch(Pageable page) {
        var clients = clientRepository.findEligibleClients(MIN_AGE, MAX_AGE, MIN_INCOME, MAX_INCOME, page);

        List<ClientIncomeResponseDTO> filteredClients = clients.getContent()
                .stream()
                .filter(client -> determineCreditType(client).contains(CreditType.FIXED_INTEREST))
                .filter(client -> checkCredit(client, VehicleModel.HATCH))
                .map(ClientIncomeResponseDTO::new)
                .collect(Collectors.toList());

        return new PageImpl<>(filteredClients, page, filteredClients.size());
    }

}
