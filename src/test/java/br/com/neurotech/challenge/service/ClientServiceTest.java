package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.DTOs.ClientRequestDTO;
import br.com.neurotech.challenge.DTOs.NeurotechClientResponseDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.exception.ClientAlreadyExistsException;
import br.com.neurotech.challenge.exception.ClientNotFoundException;
import br.com.neurotech.challenge.repository.NeurotechClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private NeurotechClientRepository neurotechClientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private ClientRequestDTO clientRequestDTO;
    private NeurotechClient neurotechClient;

    @BeforeEach
    void setUp() {
        clientRequestDTO = new ClientRequestDTO("1", "Vinícius Barbosa", 30, 7500.0);
        neurotechClient = new NeurotechClient("1", "Vinícius Barbosa", 30, 7500.0);
    }

    @Test
    @DisplayName("Should save the client successfully when it does not exist in the database")
    void save() {

        when(neurotechClientRepository.existsById(clientRequestDTO.id())).thenReturn(false);
        when(neurotechClientRepository.save(any(NeurotechClient.class))).thenReturn(neurotechClient);

        String savedId = clientService.save(clientRequestDTO);

        assertNotNull(savedId);
        assertEquals("1", savedId);
        verify(neurotechClientRepository, times(1)).existsById("1");
        verify(neurotechClientRepository, times(1)).save(any(NeurotechClient.class));
    }

    @Test
    @DisplayName("Should throw ClientAlreadyExistsException when trying to save client with ID that already exists")
    void saveClientAlreadyExists() {
        when(neurotechClientRepository.existsById(clientRequestDTO.id())).thenReturn(true);

        ClientAlreadyExistsException exception = assertThrows(ClientAlreadyExistsException.class, () -> {
            clientService.save(clientRequestDTO);
        });

        assertEquals("Client already exists", exception.getMessage());
        verify(neurotechClientRepository, never()).save(any(NeurotechClient.class));
    }

    @Test
    @DisplayName("Should return NeurotechClientResponseDTO when client is found")
    void getClient() {
        when(neurotechClientRepository.findById("1")).thenReturn(Optional.of(neurotechClient));

        NeurotechClientResponseDTO result = clientService.get("1");

        assertNotNull(result);
        assertEquals("Vinícius Barbosa", result.name());
        assertEquals(30, result.age());
        assertEquals(7500.0, result.income());
        verify(neurotechClientRepository, times(1)).findById("1");
    }

    @Test
    @DisplayName("Should throw ClientNotFoundException when client is not found")
    void getClientNotFound() {

        when(neurotechClientRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> {
            clientService.get("999");
        });
    }
}