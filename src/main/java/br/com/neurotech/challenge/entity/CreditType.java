package br.com.neurotech.challenge.entity;

import lombok.Getter;

@Getter
public enum CreditType {
    FIXED_INTEREST("Fixed Interest", 0.05),
    VARIABLE_INTEREST("Variable Interest", null),
    CONSIGNED("Consigned", null);

    private final String description;
    private final Double annualInterestRate;

    CreditType(String description, Double annualInterestRate) {
        this.description = description;
        this.annualInterestRate = annualInterestRate;
    }

}