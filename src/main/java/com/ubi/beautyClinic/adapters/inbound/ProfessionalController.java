package com.ubi.beautyClinic.adapters.inbound;

import com.ubi.beautyClinic.adapters.inbound.mappers.GenericMapper;
import com.ubi.beautyClinic.adapters.inbound.request.ProfessionalRequest;
import com.ubi.beautyClinic.adapters.inbound.response.ProfessionalResponse;
import com.ubi.beautyClinic.application.core.domain.Professional;
import com.ubi.beautyClinic.application.core.exceptions.BusinessLogicException;
import com.ubi.beautyClinic.application.ports.in.ProfessionalUseCaseInboundPort;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/professionals")
@AllArgsConstructor
public class ProfessionalController {

    private final ProfessionalUseCaseInboundPort inboundPort;
    private final GenericMapper mapper;

    @GetMapping
    @Operation(summary = "Obter todos os profissionais")
    public ResponseEntity<List<ProfessionalResponse>> listAll() {

        var professionals = inboundPort.getAllProfessionals();
        return ResponseEntity.ok(mapper.mapToList(professionals, new TypeToken<List<ProfessionalResponse>>() {}.getType()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter profissional pelo ID")
    public ResponseEntity<ProfessionalResponse> findProfessionalById(@PathVariable Long id) {

        var professional = inboundPort.findProfessionalById(id);
        return ResponseEntity.ok(mapper.mapTo(professional, ProfessionalResponse.class));
    }

    @GetMapping("/search/{fullName}")
    @Operation(summary = "Obter profissional pelos primeiros caracteres do nome")
    public ResponseEntity<List<ProfessionalResponse>> findProfessionalByFullNameStartingWith(@PathVariable(required = false) String fullName) {

        var professionals = inboundPort.findProfessionalByFullNameStartingWith(fullName);
        return professionals.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(mapper.mapToList(professionals, new TypeToken<List<ProfessionalResponse>>() {}.getType()));
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo profissional")
    public ResponseEntity<ProfessionalResponse> registerProfessional(@RequestBody @Valid ProfessionalRequest requestDTO) {

        var professional = mapper.mapTo(requestDTO, Professional.class);
        var savedProfessional = inboundPort.registerProfessional(professional);
        var responseDTO = mapper.mapTo(savedProfessional, ProfessionalResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar os dados de um profissional")
    public ResponseEntity<ProfessionalResponse> updateProfessionalData (@Valid @PathVariable Long id, @RequestBody ProfessionalRequest professionalRequest) throws BusinessLogicException {

        var professional = mapper.mapTo(professionalRequest, Professional.class);
        professional.setId(id);
        return inboundPort.professionalExists(id)
                ? ResponseEntity.ok(mapper.mapTo(inboundPort.updateProfessionalData(professional), ProfessionalResponse.class))
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir os dados de um profissional")
    public ResponseEntity<ProfessionalResponse> deleteProfessional(@PathVariable Long id) {

        if (!inboundPort.professionalExists(id))
            return ResponseEntity.notFound().build();

        inboundPort.deleteProfessionalById(id);
        return ResponseEntity.noContent().build();
    }
}
