package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.DTOs.ClientIncomeResponseDTO;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.service.CreditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/credit")
@Tag(name = "Credit", description = "Credit analysis")
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;

    @Operation(summary = "Check credit eligibility",
            description = "Checks if a client is eligible for credit based on their income and vehicle model")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Credit eligibility check successful",
                    content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @GetMapping("/{id}/check/{vehicleModel}")
    public ResponseEntity<Boolean> checkCredit(
            @Parameter(description = "Client ID") @PathVariable String id, 
            @Parameter(description = "Vehicle model") @PathVariable VehicleModel vehicleModel) {
        boolean isEligible = creditService.checkCredit(id, vehicleModel);
        return ResponseEntity.ok(isEligible);
    }

    @Operation(summary = "Find eligible clients for model Hatch with fixed interest",
            description = "Returns a paginated list of clients profiled for financing a HATCH vehicle under a fixed-interest modality.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eligible clients retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClientIncomeResponseDTO.class))))
    })
    @GetMapping("/eligible-clients/hatch-fixed-interest")
    public ResponseEntity<Page<ClientIncomeResponseDTO>> findEligibleClientsForHatchWithFixedInterest(@PageableDefault Pageable page) {
        Page<ClientIncomeResponseDTO> eligibleClients = creditService.findEligibleClientsForHatch(page);
        return ResponseEntity.ok(eligibleClients);
    }
}
