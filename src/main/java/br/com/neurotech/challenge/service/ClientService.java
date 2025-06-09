package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.DTOs.ClientRequestDTO;
import br.com.neurotech.challenge.DTOs.NeurotechClientResponseDTO;
import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.entity.NeurotechClient;

@Service
public interface ClientService {
	
	/**
	 * Salva um novo cliente
	 * 
	 * @return ID do cliente recém-salvo
	 */
	String save(ClientRequestDTO client);
	
	/**
	 * Recupera um cliente baseado no seu ID
	 */
	NeurotechClientResponseDTO get(String id);
	NeurotechClient getById(String id);

}
