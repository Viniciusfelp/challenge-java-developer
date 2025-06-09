package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.DTOs.ClientRequestDTO;
import br.com.neurotech.challenge.DTOs.NeurotechClientResponseDTO;
import br.com.neurotech.challenge.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/client")
@Tag(name = "Client", description = "Client management")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @Operation(summary = "Register a new client", description = "Creates a new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Client already exists")
    })
    public ResponseEntity<Void> saveClient(@Valid @RequestBody ClientRequestDTO clientDto, UriComponentsBuilder builder) {
        String clientId = clientService.save(clientDto);
        var uri = builder.path("/api/client/{id}").buildAndExpand(clientId).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    @Operation(summary = "Find a client by ID", description = "Returns data for a specific client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client found",
                    content = @Content(schema = @Schema(implementation = ClientRequestDTO.class,
                            example = "{\"name\": \"Vinícius\",\"age\": 24,\"income\": 10000.0}"))
            ),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<NeurotechClientResponseDTO> getClient(@Parameter(description = "Client ID") @PathVariable String id) {
        var client = clientService.get(id);
        return ResponseEntity.ok(client);
    }
}
