package br.com.neurotech.challenge.repository;

import br.com.neurotech.challenge.entity.NeurotechClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class NeurotechClientRepositoryTest {

    @Autowired
    private NeurotechClientRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        repository.saveAll(List.of(
            new NeurotechClient("1", "Ana Carolina", 23, 7500.5),
            new NeurotechClient("10", "Julia Andrade", 19, 3000.0),
            new NeurotechClient("11", "Lucas Pinheiro", 50, 25000.0),
            new NeurotechClient("12", "Maria Oliveira", 68, 6000.0),
            new NeurotechClient("123456789000", "Ana Santos", 28, 9500.5),
            new NeurotechClient("123456789009", "Ana Santos", 25, 9500.5),
            new NeurotechClient("2", "Bruno Martins", 24, 12000.0),
            new NeurotechClient("3", "Carla Dias", 25, 15000.0),
            new NeurotechClient("4", "Daniel Farias", 23, 5000.0),
            new NeurotechClient("5", "Eduarda Souza", 22, 8000.0),
            new NeurotechClient("6", "Felipe Barros", 26, 9500.0),
            new NeurotechClient("7", "Gabriela Lima", 40, 11000.0),
            new NeurotechClient("8", "Heitor Mendes", 24, 4999.99),
            new NeurotechClient("9", "Isabela Rocha", 25, 15000.01)
        ));
    }

    @Test
    @DisplayName("Should find client by ID")
    void findClientById() {
        Optional<NeurotechClient> client = repository.findById("2");

        assertTrue(client.isPresent());
        assertEquals("Bruno Martins", client.get().getName());
        assertEquals(24, client.get().getAge());
        assertEquals(12000.0, client.get().getIncome());
    }

    @Test
    @DisplayName("Should return empty when client not found")
    void clientNotFound() {
        Optional<NeurotechClient> client = repository.findById("999");

        assertFalse(client.isPresent());
    }

    @Test
    @DisplayName("Should find eligible clients within age and income range")
    void findEligibleClients() {
        Page<NeurotechClient> eligibleClients = repository.findEligibleClients(
            23, 49, 5000.0, 15000.0, PageRequest.of(0, 10)
        );

        assertEquals(8, eligibleClients.getTotalElements());

        List<String> clientIds = eligibleClients.getContent().stream()
            .map(NeurotechClient::getId)
            .toList();

        assertTrue(clientIds.contains("2"));
        assertTrue(clientIds.contains("6"));
    }

    @Test
    @DisplayName("Should handle pagination correctly")
    void paginationCorrectly() {

        repository.saveAll(List.of(
                new NeurotechClient("20", "Eu", 23, 8000.0),
                new NeurotechClient("21", "Tu", 24, 9000.0),
                new NeurotechClient("22", "Ele", 25, 10000.0)
        ));

        Page<NeurotechClient> firstPage = repository.findEligibleClients(
                22, 49, 5000.0, 15000.0, PageRequest.of(0, 2)
        );

        assertEquals(12, firstPage.getTotalElements());
        assertEquals(2, firstPage.getContent().size());
        assertEquals(6, firstPage.getTotalPages());
        Page<NeurotechClient> secondPage = repository.findEligibleClients(
                22, 49, 5000.0, 15000.0, PageRequest.of(1, 2)
        );

        assertEquals(2, secondPage.getContent().size());
        assertNotEquals(
                firstPage.getContent().getFirst().getId(),
                secondPage.getContent().getFirst().getId()
        );
    }
}
