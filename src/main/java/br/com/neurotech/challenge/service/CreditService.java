package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.DTOs.ClientIncomeResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;

import java.util.List;

@Service
public interface CreditService {

	/**
	 * Efetua a checagem se o cliente está apto a receber crédito
	 * para um determinado modelo de veículo
	 */
	boolean checkCredit(String clientId, VehicleModel model);

	List<CreditType> determineCreditType(NeurotechClient client);

	Page<ClientIncomeResponseDTO> findEligibleClientsForHatch(Pageable page);
}
