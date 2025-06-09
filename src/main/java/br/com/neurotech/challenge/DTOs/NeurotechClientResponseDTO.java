package br.com.neurotech.challenge.DTOs;

import br.com.neurotech.challenge.entity.NeurotechClient;

public record NeurotechClientResponseDTO (String name, Integer age, Double income){
    public NeurotechClientResponseDTO(NeurotechClient client) {
        this(client.getName(), client.getAge(), client.getIncome());
    }
}
