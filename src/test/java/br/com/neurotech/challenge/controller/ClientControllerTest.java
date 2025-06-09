package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.DTOs.ClientRequestDTO;
import br.com.neurotech.challenge.DTOs.NeurotechClientResponseDTO;
import br.com.neurotech.challenge.exception.ClientNotFoundException;
import br.com.neurotech.challenge.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    @Test
    @DisplayName("Should save a new client and return status 201 Created")
    void saveClient() throws Exception {

        String clientId = "1";
        ClientRequestDTO requestDTO = new ClientRequestDTO(clientId, "Vinícius Barbosa", 24, 5000.0);

        when(clientService.save(any(ClientRequestDTO.class))).thenReturn(clientId);

        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/client/" + clientId));
    }

    @Test
    @DisplayName("Should return status 400 Bad Request when saving a client with invalid data (Name is Blank)")
    void saveClientWithInvalidData() throws Exception {

        ClientRequestDTO requestDTO = new ClientRequestDTO("2", "", 30, 7000.0);

        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return status 400 Bad Request when saving a client with age below minimum")
    void saveClientUnderEighteen() throws Exception {
        ClientRequestDTO requestDTO = new ClientRequestDTO("3", "Vinícius Barbosa", 17, 4000.0);

        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should get a client by ID and return status 200 OK")
    void getClient() throws Exception {
        String clientId = "1";
        NeurotechClientResponseDTO responseDTO = new NeurotechClientResponseDTO(
                "Vinícius Barbosa",
                25,
                5000.0
        );

        when(clientService.get(clientId)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/client/{id}", clientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(responseDTO.name()))
                .andExpect(jsonPath("$.age").value(responseDTO.age()))
                .andExpect(jsonPath("$.income").value(responseDTO.income()));
    }

    @Test
    @DisplayName("Should return status 404 Not Found when getting a client with a non-existing ID")
    void getClientNonExistingId() throws Exception {
        String clientId = "999";
        when(clientService.get(clientId)).thenThrow(new ClientNotFoundException("Client not found"));
        mockMvc.perform(get("/api/client/{id}", clientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}