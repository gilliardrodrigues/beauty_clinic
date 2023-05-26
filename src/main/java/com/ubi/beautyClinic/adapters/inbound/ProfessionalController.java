package com.ubi.beautyClinic.adapters.inbound;

import com.ubi.beautyClinic.adapters.inbound.mappers.GenericMapper;
import com.ubi.beautyClinic.adapters.inbound.request.JwtRequest;
import com.ubi.beautyClinic.adapters.inbound.request.ProfessionalRequest;
import com.ubi.beautyClinic.adapters.inbound.response.JwtResponse;
import com.ubi.beautyClinic.adapters.inbound.response.ProfessionalResponse;
import com.ubi.beautyClinic.application.core.domain.Professional;
import com.ubi.beautyClinic.application.core.domain.ServiceEnum;
import com.ubi.beautyClinic.application.core.exceptions.BusinessLogicException;
import com.ubi.beautyClinic.application.ports.in.ProfessionalUseCaseInboundPort;
import com.ubi.beautyClinic.config.JwtTokenUtil;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/professionals")
@AllArgsConstructor
@OpenAPIDefinition(info = @Info(title = "Beauty Clinic API", version = "1.0"))
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class ProfessionalController {

    private final ProfessionalUseCaseInboundPort inboundPort;
    private final GenericMapper mapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Tag(name = "Profissionais")
    @Operation(summary = "Autenticação de profissional")
    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = inboundPort.loadProfessionalByEmail(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails, "PROFESSIONAL");

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @Tag(name = "Profissionais")
    @PostMapping("/register")
    @Operation(summary = "Cadastrar um novo profissional")
    public ResponseEntity<ProfessionalResponse> registerProfessional(@RequestBody @Valid ProfessionalRequest requestDTO) {

        var professional = mapper.mapTo(requestDTO, Professional.class);
        var savedProfessional = inboundPort.registerProfessional(professional);
        var responseDTO = mapper.mapTo(savedProfessional, ProfessionalResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Tag(name = "Profissionais")
    @GetMapping("/filter/by-service/{service}")
    @Operation(summary = "Listar profissionais por serviço")
    public ResponseEntity<List<ProfessionalResponse>> listProfessionalsByService(@Parameter(description = "Serviço desejado") @PathVariable ServiceEnum service) {

        var professionals = inboundPort.findProfessionalsByService(service);
        return professionals.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(mapper.mapToList(professionals, new TypeToken<List<ProfessionalResponse>>() {}.getType()));
    }

    @Tag(name = "Profissionais")
    @GetMapping
    @Operation(summary = "Obter todos os profissionais")
    public ResponseEntity<List<ProfessionalResponse>> listAll() {

        var professionals = inboundPort.getAllProfessionals();
        return ResponseEntity.ok(mapper.mapToList(professionals, new TypeToken<List<ProfessionalResponse>>() {}.getType()));
    }

    @Tag(name = "Profissionais")
    @GetMapping("/{id}")
    @Operation(summary = "Obter profissional pelo ID")
    public ResponseEntity<ProfessionalResponse> findProfessionalById(@Parameter(description = "ID do profissional") @PathVariable Long id) {

        var professional = inboundPort.findProfessionalById(id);
        return ResponseEntity.ok(mapper.mapTo(professional, ProfessionalResponse.class));
    }

    @Tag(name = "Profissionais")
    @GetMapping("/search/by-name/{fullName}")
    @Operation(summary = "Obter profissional pelos primeiros caracteres do nome")
    public ResponseEntity<List<ProfessionalResponse>> findProfessionalByFullNameStartingWith(@Parameter(description = "Nome do profissional (parcial ou completo)") @PathVariable String fullName) {

        var professionals = inboundPort.findProfessionalByFullNameStartingWith(fullName);
        return professionals.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(mapper.mapToList(professionals, new TypeToken<List<ProfessionalResponse>>() {}.getType()));
    }

    @Tag(name = "Profissionais")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar os dados de um profissional")
    public ResponseEntity<ProfessionalResponse> updateProfessionalData (@Valid @PathVariable Long id, @RequestBody ProfessionalRequest professionalRequest) throws BusinessLogicException {

        var professional = mapper.mapTo(professionalRequest, Professional.class);
        professional.setId(id);
        return inboundPort.professionalExists(id)
                ? ResponseEntity.ok(mapper.mapTo(inboundPort.updateProfessionalData(professional), ProfessionalResponse.class))
                : ResponseEntity.notFound().build();
    }

    @Tag(name = "Profissionais")
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir os dados de um profissional")
    public ResponseEntity<ProfessionalResponse> deleteProfessional(@Parameter(description = "ID do profissional") @PathVariable Long id) {

        if (!inboundPort.professionalExists(id))
            return ResponseEntity.notFound().build();

        inboundPort.deleteProfessionalById(id);
        return ResponseEntity.noContent().build();
    }
}
