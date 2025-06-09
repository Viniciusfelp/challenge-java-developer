package br.com.neurotech.challenge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeurotechClient {

	@Id
	private String id;
	private String name;
	private Integer age;
	private Double income;

}