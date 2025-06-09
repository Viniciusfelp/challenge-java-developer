package br.com.neurotech.challenge.DTOs;

import br.com.neurotech.challenge.entity.NeurotechClient;

public record ClientIncomeResponseDTO(String nome, Double income) {

    public  ClientIncomeResponseDTO(NeurotechClient client) {
        this(client.getName(),  client.getIncome());
    }
}
