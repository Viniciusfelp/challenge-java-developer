package br.com.neurotech.challenge.DTOs;

import br.com.neurotech.challenge.entity.NeurotechClient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;

public record ClientRequestDTO(@NotBlank
                                 String id,
                               @NotBlank(message = "Name cannot be blank")
                                 String name,
                               @Min(value = 18, message = "Age must be at least 18")
                                 Integer age,
                               @PositiveOrZero(message = "Income must be positive or zero")
                                 Double income) implements Serializable {

    public NeurotechClient toEntity() {
        return new NeurotechClient(id, name, age, income);
    }
}
