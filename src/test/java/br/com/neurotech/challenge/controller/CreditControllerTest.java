package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.DTOs.ClientIncomeResponseDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.exception.ClientNotFoundException;
import br.com.neurotech.challenge.service.CreditService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CreditController.class)
class CreditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditService creditService;

    @Test
    @DisplayName("Should return true when client is eligible for credit")
    void clientIsEligibleForCredit() throws Exception {
        when(creditService.checkCredit(eq("1"), eq(VehicleModel.HATCH))).thenReturn(true);

        mockMvc.perform(get("/api/credit/1/check/HATCH"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    @DisplayName("Should return false when client is not eligible for credit")
    void clientIsNotEligibleForCredit() throws Exception {
        when(creditService.checkCredit(eq("1"), eq(VehicleModel.SUV))).thenReturn(false);

        mockMvc.perform(get("/api/credit/1/check/SUV"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    @DisplayName("Should return 404 when client not found")
    void clientNotFound() throws Exception {
        when(creditService.checkCredit(eq("999"), any(VehicleModel.class)))
                .thenThrow(new ClientNotFoundException("Client not found with ID: 999"));

        mockMvc.perform(get("/api/credit/999/check/HATCH"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return eligible clients for Hatch with fixed interest")
    void eligibleClients() throws Exception {
        List<ClientIncomeResponseDTO> clients = List.of(
                new ClientIncomeResponseDTO(new NeurotechClient("1", "Vinícius Barbosa", 24, 6000.0)),
                new ClientIncomeResponseDTO(new NeurotechClient("2", "Barbosa Vinícius", 24, 7000.0))
        );

        PageImpl<ClientIncomeResponseDTO> page = new PageImpl<>(clients, PageRequest.of(0, 10), clients.size());

        when(creditService.findEligibleClientsForHatch(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/credit/eligible-clients/hatch-fixed-interest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].nome", is("Vinícius Barbosa")))
                .andExpect(jsonPath("$.content[0].income", is(6000.0)))
                .andExpect(jsonPath("$.content[1].nome", is("Barbosa Vinícius")))
                .andExpect(jsonPath("$.content[1].income", is(7000.0)));
    }

    @Test
    @DisplayName("Should return empty page when no eligible clients found")
    void NoEligibleClientsFound() throws Exception {
        PageImpl<ClientIncomeResponseDTO> emptyPage = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);

        when(creditService.findEligibleClientsForHatch(any(Pageable.class))).thenReturn(emptyPage);

        mockMvc.perform(get("/api/credit/eligible-clients/hatch-fixed-interest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.totalElements", is(0)));
    }
}
