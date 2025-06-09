package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.DTOs.ClientRequestDTO;
import br.com.neurotech.challenge.DTOs.NeurotechClientResponseDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.exception.ClientAlreadyExistsException;
import br.com.neurotech.challenge.exception.ClientNotFoundException;
import br.com.neurotech.challenge.repository.NeurotechClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements  ClientService {

    private final NeurotechClientRepository neurotechClientRepository;

    @Override
    @Transactional
    public String save(ClientRequestDTO client) {
        if(!neurotechClientRepository.existsById(client.id())){
            return neurotechClientRepository.save(client.toEntity()).getId();
        }
        throw new ClientAlreadyExistsException("Client already exists");
    }

    @Override
    @Transactional(readOnly = true)
    public NeurotechClientResponseDTO get(String id) {
        var client = neurotechClientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with ID: " + id));
        return new NeurotechClientResponseDTO(client.getName(), client.getAge(), client.getIncome());
    }

    @Transactional(readOnly = true)
    public NeurotechClient getById(String id) {
        return neurotechClientRepository.findById(id).
                orElseThrow(() -> new ClientNotFoundException("Client not found with ID: " + id));

    }
}