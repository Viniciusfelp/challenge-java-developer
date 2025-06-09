package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.repository.NeurotechClientRepository;
import br.com.neurotech.challenge.rules.CreditModalityRule;
import br.com.neurotech.challenge.rules.VehicleEligibilityRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditServiceTest {

    @Mock
    private ClientService clientService;
    @Mock
    private NeurotechClientRepository clientRepository;
    @Mock
    private VehicleEligibilityRule vehicleRule;
    @Mock
    private CreditModalityRule creditRule;

    private CreditServiceImpl creditService;

    @BeforeEach
    void setUp() {
        creditService = new CreditServiceImpl(clientService, clientRepository, List.of(vehicleRule), List.of(creditRule));
    }

    @Test
    @DisplayName("checkCredit by ID should return true when the vehicle eligibility rule passes")
    void checkCredit() {

        String clientId = "1";
        VehicleModel model = VehicleModel.HATCH;
        NeurotechClient client = new NeurotechClient(clientId, "Vinícius", 24, 10000.0);

        when(clientService.getById(clientId)).thenReturn(client);
        when(vehicleRule.appliesTo(model)).thenReturn(true);
        when(vehicleRule.isEligible(client)).thenReturn(true);

        boolean isEligible = creditService.checkCredit(clientId, model);

        assertTrue(isEligible);
        verify(clientService, times(1)).getById(clientId);
        verify(vehicleRule, times(1)).isEligible(client);
    }

    @Test
    @DisplayName("determineCreditType should return credit types from applicable rules")
    void determineCreditType() {

        NeurotechClient client = new NeurotechClient("1", "Vinícius", 24, 10000.0);
        when(creditRule.determineCreditType(client)).thenReturn(Optional.of(CreditType.FIXED_INTEREST));

        List<CreditType> creditTypes = creditService.determineCreditType(client);

        assertNotNull(creditTypes);
        assertEquals(1, creditTypes.size());
        assertEquals(CreditType.FIXED_INTEREST, creditTypes.getFirst());
    }

}